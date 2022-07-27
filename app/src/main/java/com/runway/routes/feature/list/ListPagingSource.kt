package com.runway.routes.feature.list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.runway.routes.data.LocationDataSource
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.domain.entity.RunwayEntity
import kotlin.math.min


class ListPagingSource(
    private val filter: String,
    private val locationSource: LocationDataSource,
    private val localDataSource: RunwayLocalDataSource,
    private val initialSize: Int
) : PagingSource<Int, RunwayEntity>() {

    override fun getRefreshKey(state: PagingState<Int, RunwayEntity>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RunwayEntity> {
        return try {

            val currentPage = params.key ?: 0
            val loadSize = params.loadSize

            val offset = calcOffset(currentPage, loadSize)
            val limit = loadSize + 1

            val (latitude, longitude) = locationSource.getLastLocation()

            val runways = getRunways(latitude, longitude, offset, limit)
            val hasMore = runways.size > loadSize

            LoadResult.Page(
                data = runways.subList(0, min(runways.size, loadSize)),
                prevKey = if (currentPage != 0) currentPage - 1 else null,
                nextKey = if (hasMore) currentPage + 1 else null
            )
        } catch (throwable: Throwable) {
            Log.e("ListSource", "load() throwable - $throwable")
            return LoadResult.Error(throwable)
        }
    }

    private fun calcOffset(currentPage: Int, loadSize: Int) = if (currentPage == 0) {
        0
    } else {
        loadSize * (currentPage - 1) + initialSize
    }

    private suspend fun getRunways(
        latitude: Double,
        longitude: Double,
        offset: Int,
        limit: Int
    ) = if (filter.isEmpty()) {
        localDataSource.getByDistance(latitude, longitude, offset, limit)
    } else {
        localDataSource.getByDistanceWithQuery(latitude, longitude, offset, limit, filter)
    }
}