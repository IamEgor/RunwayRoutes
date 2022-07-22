package com.runway.routes.feature.splash

import android.Manifest
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.runway.routes.R
import com.runway.routes.RunwayApp
import com.runway.routes.ui.theme.Typography
import com.runway.routes.utils.openAppSystemSettings

@Composable
fun SplashContent(
    component: SplashComponent,
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit
) {

    val state = component.state.collectAsState()

    when (val value = state.value) {
        is SplashState.Initial -> Unit
        is SplashState.Process -> SplashLoadingDataView(
            inProgress = value.inProgress,
            loadingText = getProgressText(value),
            navigateToMain = navigateToMain
        )
        is SplashState.Error -> ErrorView(value.error)
        is SplashState.NavigateToMainNow -> navigateToMain()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun SplashLoadingDataView(
    inProgress: Boolean,
    loadingText: String,
    navigateToMain: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var visible by remember { mutableStateOf(false) }
        val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        val doneText = stringResource(R.string.done).uppercase()

        val permissionGranted = permissionState.status is PermissionStatus.Granted
        visible = !inProgress

        val (baseline, progressRow, permissionRow, doneButton) = createRefs()

        HorizontalBaseline(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(baseline) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            })

        ProgressRow(
            inProgress = inProgress,
            loadingText = loadingText,
            doneText = doneText,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .size(48.dp)
                .constrainAs(progressRow) { bottom.linkTo(baseline.top) }
        )

        PermissionRow(
            visible = visible,
            showRationale = permissionState.status.shouldShowRationale,
            permissionGranted = permissionGranted,
            doneText = doneText,
            launchPermissionRequest = { permissionState.launchPermissionRequest() },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .constrainAs(permissionRow) { top.linkTo(baseline.bottom) }
        )

        DoneButton(
            permissionGranted && !inProgress,
            doneText,
            navigateToMain,
            Modifier
                .width(160.dp)
                .constrainAs(doneButton) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }
        )
    }
}

@Composable
private fun HorizontalBaseline(modifier: Modifier) = Row(modifier = modifier) { }

@Composable
private fun ProgressRow(
    inProgress: Boolean,
    loadingText: String,
    doneText: String,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.weight(5f),
            style = Typography.titleLarge,
            text = loadingText
        )
        if (inProgress) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .weight(2f, false)
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(2f, false),
                text = doneText,
                color = MaterialTheme.colors.primary,
                style = Typography.titleMedium
            )
        }
    }
}


@Composable
private fun PermissionRow(
    visible: Boolean,
    showRationale: Boolean,
    permissionGranted: Boolean,
    doneText: String,
    launchPermissionRequest: () -> Unit,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(600)) +
                expandVertically(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(600)) +
                shrinkVertically(animationSpec = tween(1000)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(5f),
                style = Typography.titleLarge,
                text = "Grant location permission"
            )
            if (!permissionGranted) {
                Button(
                    modifier = Modifier.weight(2f),
                    onClick = {
                        if (showRationale) {
                            launchPermissionRequest()
                        } else {
                            openAppSystemSettings(RunwayApp.instance)
                        }
                    }) {
                    Text("Grant")
                }
            } else {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(2f, false),
                    text = doneText,
                    color = MaterialTheme.colors.primary,
                    style = Typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun DoneButton(
    enabled: Boolean,
    doneText: String,
    navigateToMain: () -> Unit,
    modifier: Modifier
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = navigateToMain
    ) {
        Text(doneText)
    }
}

@Composable
private fun ErrorView(error: Throwable) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.error,
            style = Typography.titleLarge,
            text = stringResource(
                R.string.error_while_loading_runways,
                error.message ?: error.toString()
            )
        )
    }
}

@Composable
private fun getProgressText(value: SplashState.Process) = when (value) {
    SplashState.LoadingFromNetwork -> stringResource(R.string.loading_from_network)
    SplashState.SavingToDatabase -> stringResource(R.string.saving_to_database)
    SplashState.Done -> stringResource(R.string.saving_to_database)
}