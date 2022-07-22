package com.runway.routes.feature.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashComponent {
    val state: StateFlow<SplashState>
}