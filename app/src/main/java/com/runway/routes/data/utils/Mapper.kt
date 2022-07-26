package com.runway.routes.data.utils

import com.google.android.gms.maps.model.LatLng
import com.runway.routes.GetByDistance
import com.runway.routes.Runway
import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.domain.entity.RunwayEntity
import kotlin.math.acos

const val EARTH_RADIUS = 6_371.0

fun Runway.toEntity() = RunwayEntity(
    this._id,
    this.runwayId,
    this.lat,
    this.lon,
    this.nameEn,
    this.nameRu,
    this.active,
    this.type,
    this.indexEn,
    this.indexRu,
    this.belongs
)

fun GetByDistance.toEntity() = RunwayEntity(
    this._id,
    this.runwayId,
    this.lat,
    this.lon,
    this.nameEn,
    this.nameRu,
    this.active,
    this.type,
    this.indexEn,
    this.indexRu,
    this.belongs,
    acos(this.distance!!.toFloat()) * EARTH_RADIUS
)

fun LocationEntity.toLatLng() = LatLng(latitude, longitude)