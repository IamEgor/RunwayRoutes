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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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
        private const val MIN_QUERY = 3
    }

    private val locationSource: LocationDataSource by inject()
    private val localDataSource: RunwayLocalDataSource by inject()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "exception - $exception, message - ${exception.message}")
    }
    private val componentScope = createComponentScope(coroutineExceptionHandler)
    private val filterFlow = MutableStateFlow("")
    private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_SIZE)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override val runways = filterFlow
        .map { query -> normalizeQuery(query.trim()) }
        .distinctUntilChanged()
        .debounce(500L)
        .flatMapLatest { filter ->
            Log.w(TAG, "flatMapLatest() filter = $filter")
            val pagingSource = pagingSource(filter)
            Pager(pagingConfig) { pagingSource }.flow
        }.cachedIn(componentScope)


    override fun updateFilter(query: String) = filterFlow.update { query }

    private fun normalizeQuery(query: String) = if (query.length < MIN_QUERY) "" else query

    private fun pagingSource(filter: String) = ListPagingSource(
        filter,
        locationSource,
        localDataSource,
        INITIAL_SIZE
    )
}