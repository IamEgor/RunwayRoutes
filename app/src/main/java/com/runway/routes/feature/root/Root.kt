package com.runway.routes.feature.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.runway.routes.feature.main.MainComponent
import com.runway.routes.feature.splash.SplashComponent

interface Root {

    val routerState: Value<RouterState<*, Child>>

    fun navigateToMain()

    sealed class Child {

        class Splash(val component: SplashComponent) : Child()

        class Main(val component: MainComponent) : Child()
    }
}
