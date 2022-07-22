package com.runway.routes.feature.splash

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.data.RunwayRemoteDataSource
import com.runway.routes.feature.createComponentScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext,
    SplashComponent,
    KoinComponent {

    companion object {
        private const val TAG = "SplashComponentImpl"
    }

    private val remoteDataSource: RunwayRemoteDataSource by inject()
    private val localDataSource: RunwayLocalDataSource by inject()

    private val _state = MutableStateFlow<SplashState>(SplashState.Initial)
    override val state: StateFlow<SplashState> = _state

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception - $exception, message - ${exception.message}")
        _state.update { SplashState.Error(exception) }
    }
    private val componentScope = createComponentScope(coroutineExceptionHandler)

    init {
        lifecycle.doOnStart {
            componentScope.launch {
                val hasRecords = localDataSource.hasRecords()
                if (!hasRecords) {
                    _state.update { SplashState.LoadingFromNetwork }
                    val points = remoteDataSource.load()
                    _state.update { SplashState.SavingToDatabase }
                    localDataSource.saveRunways(points)
                    _state.update { SplashState.Done }
                } else {
                    _state.update { SplashState.NavigateToMainNow }
                }
            }
        }
    }
}