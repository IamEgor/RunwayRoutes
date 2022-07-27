package com.runway.routes.feature.details

import com.arkivanov.decompose.value.Value
import com.runway.routes.domain.entity.RunwayEntity

interface DetailsComponent {
    val model: Value<RunwayEntity>
}