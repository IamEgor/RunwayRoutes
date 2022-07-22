package com.runway.routes.feature.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.runway.routes.feature.main.MainComponent
import com.runway.routes.feature.splash.SplashComponentImpl


class RootComponent constructor(componentContext: ComponentContext) :
    Root,
    ComponentContext by componentContext {

    private val router: Router<Config, Root.Child> = router(
        initialStack = { listOf(Config.Splash) },
        childFactory = ::child,
    )
    override val routerState: Value<RouterState<*, Root.Child>> = router.state

    override fun navigateToMain() = router.replaceCurrent(Config.Main)

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.Splash -> Root.Child.Splash(
            SplashComponentImpl(componentContext)
        )
        is Config.Main -> Root.Child.Main(
            MainComponent(componentContext)
        )
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Splash : Config

        @Parcelize
        object Main : Config
    }
}
