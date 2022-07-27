package com.runway.routes

import com.runway.routes.data.model.PointsResponse
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.domain.entity.RunwayType
import com.runway.routes.utils.findRoute
import junit.framework.Assert.assertEquals
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.Test
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class RouteUtilsTest {

    companion object {
        const val PLANE_DISTANCE = 300.0
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun test() {
        val xml = XML
        val text = File("src/test/res/aopa-points-export.xml").readText()
        val pointsResponse = xml.decodeFromString<PointsResponse>(text)
        val points = pointsResponse.point
            .map { point ->
                RunwayEntity(
                    point.id,
                    point.id,
                    point.lat,
                    point.lon,
                    point.name,
                    point.name_ru,
                    point.active,
                    point.type,
                    point.index,
                    point.index_ru,
                    point.belongs,
                    point.country_name,
                    point.region,
                    point.city
                )
            }
            .filter { it.active }
            .filter { it.belongs != null }
            .filter { it.type == RunwayType.AIRPORT }

        println("points.size - ${points.size}")

        val firstPoint = points.find { it.indexRu == "ЗЦЕ5" }!!
        val lastPoint = points.find { it.indexRu == "УННМ" }!!

        val route: List<RunwayEntity>
        val measureTime = measureTime {
            route = findRoute(PLANE_DISTANCE, firstPoint, lastPoint, points)
        }

        println("measureTime - $measureTime")

        assertEquals(route.size, 15)
    }
}