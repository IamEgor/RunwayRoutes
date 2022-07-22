package com.runway.routes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.runway.routes.feature.root.RootComponent
import com.runway.routes.feature.root.RootContent
import com.runway.routes.ui.theme.RunwayRoutesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = RootComponent(componentContext = defaultComponentContext())
        setContent {
            RunwayRoutesTheme {
                RootContent(root = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}