package com.runway.routes.utils

import com.runway.routes.domain.EARTH_RADIUS
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.domain.exception.NoPathException


@Throws(NoPathException::class)
fun findRoute(
    aircraftRangeKm: Double,
    firstPoint: RunwayEntity,
    lastPoint: RunwayEntity,
    points: List<RunwayEntity>
): List<RunwayEntity> {

    //points sorted toward the last
    val sortedPoints = points
        .map { point -> distanceKm(point, lastPoint) to point }
        .sortedBy { it.first }

    val routePoints = mutableListOf(firstPoint)
    val excludePoints = mutableListOf<RunwayEntity>()
    var currentPoint: RunwayEntity = firstPoint

    while (currentPoint != lastPoint) {

        val nextPoint = findNextPoint(aircraftRangeKm, currentPoint, excludePoints, sortedPoints)

        currentPoint = if (nextPoint == null) {
            excludePoints.add(routePoints.removeLast())
            routePoints.lastOrNull() ?: throw NoPathException()
        } else {
            nextPoint.also { routePoints.add(it) }
        }
    }

    return routePoints
}

private fun findNextPoint(
    aircraftRangeKm: Double,
    prevPoint: RunwayEntity,
    excludePoints: List<RunwayEntity>,
    sortedPoints: List<Pair<Double, RunwayEntity>>
): RunwayEntity? {
    sortedPoints.forEach { (_, point) ->
        if (excludePoints.contains(point)) return@forEach
        val distanceFromPrev = distanceKm(prevPoint, point)
        if (distanceFromPrev < aircraftRangeKm) {
            return point
        }
    }
    return null
}

private fun distanceKm(from: RunwayEntity, to: RunwayEntity): Double {
    return distanceKm(from.lat, from.lon, to.lat, to.lon)
}

private fun distanceKm(
    fromLatitude: Double,
    fromLongitude: Double,
    toLatitude: Double,
    toLongitude: Double
): Double {
    return SphericalUtil.distanceRadians(
        Math.toRadians(fromLatitude), Math.toRadians(fromLongitude),
        Math.toRadians(toLatitude), Math.toRadians(toLongitude)
    ) * EARTH_RADIUS / 1000
}
