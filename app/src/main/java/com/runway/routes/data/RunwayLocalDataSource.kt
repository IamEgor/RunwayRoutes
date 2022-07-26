package com.runway.routes.data

import com.runway.routes.Database
import com.runway.routes.data.model.PointsResponse
import com.runway.routes.data.utils.toEntity
import kotlinx.coroutines.coroutineScope
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class RunwayLocalDataSource(database: Database) {

    companion object {
        private const val TAG = "RunwayLocalDataSource"
        private const val RAD = 180 / PI
    }

    private val tableQueries = database.runwayTableQueries

    suspend fun hasRecords() = coroutineScope {
        tableQueries.hasRecords().executeAsOne()
    }

    suspend fun saveRunways(pointsResponse: PointsResponse) = coroutineScope {
        tableQueries.transaction {
            pointsResponse.point.forEach { point ->
                tableQueries.insert(
                    point.id,
                    point.lat,
                    point.lon,
                    sin(point.lat / RAD),
                    cos(point.lat / RAD),
                    sin(point.lon / RAD),
                    cos(point.lon / RAD),
                    point.name,
                    point.name_ru,
                    point.active,
                    point.type,
                    point.belongs,
                    point.index,
                    point.index_ru
                )
            }
        }
    }

    suspend fun getByDistance(lat: Double, lon: Double, skip: Int, count: Int) = coroutineScope {
        val latRad = lat / RAD
        val lonRad = lon / RAD
        tableQueries.getByDistance(
            inputLatSin = sin(latRad),
            inputLatCos = cos(latRad),
            inputLonSin = sin(lonRad),
            inputLonCos = cos(lonRad),
            skip = skip.toLong(),
            count = count.toLong()
        )
            .executeAsList()
            .map { it.toEntity() }
    }

    suspend fun getAllRunways() = coroutineScope {
        tableQueries.getAllRecords().executeAsList().map { it.toEntity() }
    }
}