package com.runway.routes.feature.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.feature.details.DetailsComponentImpl
import com.runway.routes.feature.main.MainComponent
import com.runway.routes.feature.splash.SplashComponentImpl


class RootComponent constructor(componentContext: ComponentContext) :
    Root,
    ComponentContext by componentContext {

    private val router: Router<Config, Root.Child> = router(
        initialStack = { listOf(Config.Splash) },
        childFactory = ::child,
    )

    init {
        backPressedHandler.register {
            val configuration = router.activeChild.configuration
            if (configuration is Config.Details) {
                router.pop()
                true
            } else {
                false
            }
        }
    }

    override val routerState: Value<RouterState<*, Root.Child>> = router.state

    override fun navigateToMain() = router.replaceCurrent(Config.Main)

    override fun navigateDetails(runway: RunwayEntity) = router.push(Config.Details(runway))

    override fun popToMain() = router.pop()

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.Splash -> Root.Child.Splash(
            SplashComponentImpl(componentContext)
        )
        is Config.Main -> Root.Child.Main(
            MainComponent(componentContext)
        )
        is Config.Details -> Root.Child.Details(
            DetailsComponentImpl(config.runway, componentContext)
        )
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Splash : Config

        @Parcelize
        object Main : Config

        @Parcelize
        class Details(val runway: RunwayEntity) : Config
    }
}
