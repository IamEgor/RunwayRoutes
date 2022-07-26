package com.runway.routes.feature.map

import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.domain.entity.RunwayEntity
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val runways: Flow<List<RunwayEntity>>

    val currentLocation: Flow<LocationEntity>
}