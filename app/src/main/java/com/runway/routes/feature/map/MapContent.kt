package com.runway.routes.feature.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import com.runway.routes.R
import com.runway.routes.RunwayApp
import com.runway.routes.data.utils.toLatLng
import com.runway.routes.domain.ZOOM
import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.utils.openAppSystemSettings


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapContent(
    component: MapComponent,
    modifier: Modifier
) {

    val permissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    when (val status = permissionState.status) {
        PermissionStatus.Granted -> {
            PermissionGrantedView(modifier, component)
        }
        is PermissionStatus.Denied -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val showRationale = status.shouldShowRationale
                val permissionText = getPermissionText(showRationale)
                Text(text = permissionText.first)
                Button(onClick = {
                    if (showRationale) {
                        permissionState.launchPermissionRequest()
                    } else {
                        openAppSystemSettings(RunwayApp.instance)
                    }
                }, content = {
                    Text(permissionText.second)
                })
            }
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun PermissionGrantedView(
    modifier: Modifier,
    component: MapComponent
) {

    val list by component.runways.collectAsState(emptyList())
    val location by component.currentLocation.collectAsState(LocationEntity.DEFAULT)

    val uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }
    val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location.toLatLng(), ZOOM.toFloat())
    }

    LaunchedEffect(location) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(location.toLatLng()))
    }
    GoogleMap(
        modifier = modifier,
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
    ) {
        val context = LocalContext.current
        var clusterManager by remember { mutableStateOf<ClusterManager<MapClusterItem>?>(null) }
        MapEffect(list) { map ->
            if (clusterManager == null) {
                clusterManager = ClusterManager<MapClusterItem>(context, map)
            }
            clusterManager?.addItems(list.map { MapClusterItem(it) })
        }
        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                clusterManager?.onCameraIdle()
            }
        }
    }
}

@Composable
private fun getPermissionText(shouldShowRationale: Boolean) = if (shouldShowRationale) {
    stringResource(R.string.location_permission_request_rationale) to
            stringResource(R.string.request_permission)
} else {
    stringResource(R.string.location_permission_request_no_rationale) to
            stringResource(R.string.open_app_settings)
}