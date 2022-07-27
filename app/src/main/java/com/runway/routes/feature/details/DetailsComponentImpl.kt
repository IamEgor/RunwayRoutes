package com.runway.routes.feature.details

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.feature.createComponentScope
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//TODO https://api.sunrise-sunset.org/json?lat=53.476284&lng=25.993132&date=today
class DetailsComponentImpl(
    val runway: RunwayEntity,
    componentContext: ComponentContext
) : ComponentContext by componentContext,
    DetailsComponent,
    KoinComponent {

    companion object {
        private const val TAG = "DetailsComponent"
    }

    private val localDataSource: RunwayLocalDataSource by inject()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception - $exception, message - ${exception.message}")
    }
    private val componentScope = createComponentScope(coroutineExceptionHandler)

    override val model: Value<RunwayEntity> = MutableValue(runway)
}