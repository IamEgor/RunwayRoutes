package com.runway.routes.feature.splash

sealed class SplashState {

    object Initial : SplashState()

    sealed class Process(val inProgress: Boolean) : SplashState()
    object LoadingFromNetwork : Process(true)
    object SavingToDatabase : Process(true)
    object Done : Process(false)

    class Error(val error: Throwable) : SplashState()
    object NavigateToMainNow : SplashState()
}
