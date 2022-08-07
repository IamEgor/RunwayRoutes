package com.runway.routes.feature.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.runway.routes.feature.list.ListComponent
import com.runway.routes.feature.map.MapComponent
import com.runway.routes.feature.route.RouteComponent

interface Main {

    val routerState: Value<RouterState<*, Child>>

    fun onListTabClicked()

    fun onRouteTabClicked()

    fun onMapTabClicked()

    sealed class Child {

        class ListChild(val component: ListComponent) : Child()

        class RouteChild(val component: RouteComponent) : Child()

        class MapChild(val component: MapComponent) : Child()
    }
}
