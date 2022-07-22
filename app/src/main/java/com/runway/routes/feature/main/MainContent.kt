package com.runway.routes.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.RouterState
import com.runway.routes.R
import com.runway.routes.feature.list.ListContent
import com.runway.routes.feature.map.MapContent

@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier
) {

    val routerState by component.routerState.subscribeAsState()
    val activeComponent = routerState.activeChild.instance

    Column(modifier = modifier) {
        Pager(
            modifier = Modifier.weight(weight = 1F),
            routerState = routerState
        )
        BottomNavigation(
            activeComponent = activeComponent,
            component = component
        )
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun Pager(
    modifier: Modifier,
    routerState: RouterState<*, Main.Child>
) {
    Children(
        routerState = routerState,
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is Main.Child.ListChild -> ListContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )
            is Main.Child.MapChild -> MapContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BottomNavigation(
    activeComponent: Main.Child,
    component: MainComponent
) {
    BottomNavigation(modifier = Modifier.fillMaxWidth()) {
        listOf(
            BottomNavigationItemEntity(
                stringResource(R.string.list),
                Icons.Default.List,
                { activeComponent is Main.Child.ListChild },
                component::onListTabClicked
            ),
            BottomNavigationItemEntity(
                stringResource(R.string.map),
                Icons.Default.Map,
                { activeComponent is Main.Child.MapChild },
                component::onMapTabClicked
            )
        ).forEach { entity -> BottomNavigationItem(entity) }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(entity: BottomNavigationItemEntity) {
    BottomNavigationItem(
        selected = entity.selected(),
        onClick = entity.onClick,
        icon = {
            Icon(
                imageVector = entity.icon,
                contentDescription = entity.text,
            )
        },
        label = { Text(text = entity.text) },
    )
}

private class BottomNavigationItemEntity(
    val text: String,
    val icon: ImageVector,
    val selected: () -> Boolean,
    val onClick: () -> Unit,
)