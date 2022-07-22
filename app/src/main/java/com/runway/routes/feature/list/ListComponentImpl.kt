package com.runway.routes.feature.list

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.runway.routes.data.LocationDataSource
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.feature.createComponentScope
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListComponentImpl(
    componentContext: ComponentContext
) : ComponentContext by componentContext,
    ListComponent,
    KoinComponent {

    companion object {
        private const val TAG = "ListComponent"
        private const val PAGE_SIZE = 15
        private const val INITIAL_SIZE = 45
    }

    private val locationSource: LocationDataSource by inject()
    private val localDataSource: RunwayLocalDataSource by inject()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception - $exception, message - ${exception.message}")
    }
    private val componentScope = createComponentScope(coroutineExceptionHandler)
    private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_SIZE)
    private val pagingSource = ListPagingSource(locationSource, localDataSource, INITIAL_SIZE)

    override val runways = Pager(pagingConfig) { pagingSource }
        .flow.cachedIn(componentScope)
}