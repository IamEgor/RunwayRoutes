package com.runway.routes.feature.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.runway.routes.feature.list.ListComponent
import com.runway.routes.feature.map.MapComponent

interface Main {

    val routerState: Value<RouterState<*, Child>>

    fun onListTabClicked()

    fun onMapTabClicked()

    sealed class Child {

        class ListChild(val component: ListComponent) : Child()

        class MapChild(val component: MapComponent) : Child()
    }
}
