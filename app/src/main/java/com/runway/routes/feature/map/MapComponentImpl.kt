package com.runway.routes.feature.map

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.runway.routes.data.LocationDataSource
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.feature.createComponentScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MapComponentImpl(
    componentContext: ComponentContext
) : ComponentContext by componentContext,
    MapComponent,
    KoinComponent {

    companion object {
        private const val TAG = "MapComponentImpl"
    }

    private val locationSource: LocationDataSource by inject()
    private val localDataSource: RunwayLocalDataSource by inject()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception - $exception, message - ${exception.message}")
    }
    private val componentScope = createComponentScope(coroutineExceptionHandler)

    private val _runways = MutableStateFlow<List<RunwayEntity>>(emptyList())
    override val runways: Flow<List<RunwayEntity>> = _runways
    private val _currentLocation = MutableStateFlow(LocationEntity.DEFAULT)
    override val currentLocation: Flow<LocationEntity> = _currentLocation

    init {
        lifecycle.doOnCreate {
            componentScope.launch {
                launch {
                    val runways = localDataSource.getAllRunways()
                    _runways.update { runways }
                }
                launch {
                    val location = locationSource.getLastLocation()
                    _currentLocation.update { prev ->
                        prev.copy(latitude = location.latitude, longitude = location.longitude)
                    }
                }
            }
        }
    }
}