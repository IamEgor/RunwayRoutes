package com.runway.routes.domain.entity

import android.os.Parcelable
import com.runway.routes.domain.PREVIEW_HEIGHT
import com.runway.routes.domain.PREVIEW_WIDTH
import com.runway.routes.domain.STATIC_MAP_HOST
import com.runway.routes.domain.ZOOM
import kotlinx.parcelize.Parcelize

@Parcelize
data class RunwayEntity(
    val _id: Long,
    val runwayId: Long,
    val lat: Double,
    val lon: Double,
    val nameEn: String,
    val nameRu: String,
    val active: Boolean,
    val type: RunwayType,
    val indexEn: String,
    val indexRu: String,
    val belongs: Owner?,
    val country: String,
    val region: String,
    val city: String,
    val distanceKm: Double? = null
) : Parcelable {

    companion object {
        const val ACTIVE = 1L
        private fun runwayType(type: String) = if (type == RunwayType.VERT.value)
            RunwayType.VERT else RunwayType.AIRPORT
    }

    constructor(
        _id: Long,
        runwayId: Long,
        lat: Double,
        lon: Double,
        nameEn: String,
        nameRu: String,
        active: Long,
        type: String,
        indexEn: String,
        indexRu: String,
        belongs: String,
        country: String,
        region: String,
        city: String,
        distanceKm: Double? = null
    ) : this(
        _id = _id,
        runwayId = runwayId,
        lat = lat,
        lon = lon,
        nameEn = nameEn,
        nameRu = nameRu,
        active = active == ACTIVE,
        type = runwayType(type),
        indexEn = indexEn,
        indexRu = indexRu,
        belongs = Owner.fromString(belongs),
        country = country,
        region = region,
        city = city,
        distanceKm = distanceKm
    )
}

fun RunwayEntity.regionText() = if (region.isEmpty() && city.isEmpty()) {
    null
} else if (region.isEmpty()) {
    city
} else if (city.isEmpty()) {
    region
} else {
    "$region, $city"
}

fun RunwayEntity.getPreviewMapUri(): String {
    return STATIC_MAP_HOST +
            "?ll=$lon,$lat" +
            "&z=$ZOOM" +
            "&l=map" +
            "&size=$PREVIEW_WIDTH,$PREVIEW_HEIGHT" +
            "&pt=$lon,$lat,comma"
}