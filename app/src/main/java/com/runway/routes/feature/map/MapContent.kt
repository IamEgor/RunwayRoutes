package com.runway.routes.feature.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.runway.routes.R
import com.runway.routes.RunwayApp
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
            PermissionGrantedView(modifier)
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

@Composable
private fun PermissionGrantedView(modifier: Modifier) {

    val singapore = LatLng(1.35, 103.87)
    val batam = LatLng(1.07, 104.1)
    val moro = LatLng(0.76092, 103.6959431)

    var uiSettings by remember { mutableStateOf(MapUiSettings(rotationGesturesEnabled = false)) }
    val properties by remember { mutableStateOf(MapProperties()) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Box(modifier) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings
        ) {
            Marker(
                position = singapore,
                title = "Marker in Sydney"
            )
            Polyline(
                points = listOf(singapore, batam, moro),
                geodesic = true,
            )
        }
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
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