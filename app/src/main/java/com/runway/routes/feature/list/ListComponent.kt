package com.runway.routes.feature.list

import androidx.paging.PagingData
import com.runway.routes.domain.entity.RunwayEntity
import kotlinx.coroutines.flow.Flow

interface ListComponent {
    val runways: Flow<PagingData<RunwayEntity>>
}