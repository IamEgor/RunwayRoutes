package com.runway.routes.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.runway.routes.feature.details.DetailsContent
import com.runway.routes.feature.main.MainContent
import com.runway.routes.feature.splash.SplashContent

@Composable
fun RootContent(root: Root, modifier: Modifier = Modifier) {

    val routerState by root.routerState.subscribeAsState()

    when (val instance = routerState.activeChild.instance) {
        is Root.Child.Splash -> SplashContent(
            component = instance.component,
            modifier = modifier,
            navigateToMain = root::navigateToMain
        )
        is Root.Child.Main -> MainContent(
            component = instance.component,
            modifier = modifier,
            navigateDetails = root::navigateDetails
        )
        is Root.Child.Details -> DetailsContent(
            component = instance.component,
            modifier = modifier,
            popToMain = root::popToMain
        )
    }
}