package com.runway.routes.data.utils

import com.google.android.gms.maps.model.LatLng
import com.runway.routes.GetByDistance
import com.runway.routes.GetByDistanceWithQuery
import com.runway.routes.Runway
import com.runway.routes.domain.EARTH_RADIUS
import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.domain.entity.RunwayEntity
import kotlin.math.acos


fun Runway.toEntity() = RunwayEntity(
    _id = _id,
    runwayId = runwayId,
    lat = lat,
    lon = lon,
    nameEn = nameEn,
    nameRu = nameRu,
    active = active,
    type = type,
    indexEn = indexEn,
    indexRu = indexRu,
    belongs = belongs,
    country = country,
    region = region,
    city = city
)

fun GetByDistance.toEntity() = RunwayEntity(
    _id = _id,
    runwayId = runwayId,
    lat = lat,
    lon = lon,
    nameEn = nameEn,
    nameRu = nameRu,
    active = active,
    type = type,
    indexEn = indexEn,
    indexRu = indexRu,
    belongs = belongs,
    country = country,
    region = region,
    city = city,
    distanceKm = acos(this.distance!!.toFloat()) * EARTH_RADIUS
)

fun GetByDistanceWithQuery.toEntity() = RunwayEntity(
    _id = _id,
    runwayId = runwayId,
    lat = lat,
    lon = lon,
    nameEn = nameEn,
    nameRu = nameRu,
    active = active,
    type = type,
    indexEn = indexEn,
    indexRu = indexRu,
    belongs = belongs,
    country = country,
    region = region,
    city = city,
    distanceKm = acos(this.distance!!.toFloat()) * EARTH_RADIUS
)

fun LocationEntity.toLatLng() = LatLng(latitude, longitude)