package com.runway.routes.feature.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.runway.routes.feature.list.ListComponentImpl
import com.runway.routes.feature.map.MapComponentImpl
import com.runway.routes.feature.route.RouteComponentImpl


class MainComponent constructor(componentContext: ComponentContext) :
    Main,
    ComponentContext by componentContext {

    private val router: Router<Config, Main.Child> = router(
        initialStack = { listOf(Config.List) },
        childFactory = ::child,
    )
    override val routerState: Value<RouterState<*, Main.Child>> = router.state

    override fun onListTabClicked() = router.bringToFront(Config.List)

    override fun onRouteTabClicked() = router.bringToFront(Config.Route)

    override fun onMapTabClicked() = router.bringToFront(Config.Map)

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.List -> Main.Child.ListChild(
            ListComponentImpl(componentContext)
        )
        is Config.Route -> Main.Child.RouteChild(
            RouteComponentImpl(componentContext)
        )
        is Config.Map -> Main.Child.MapChild(
            MapComponentImpl(componentContext)
        )
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        object List : Config

        @Parcelize
        object Route : Config

        @Parcelize
        object Map : Config
    }
}
