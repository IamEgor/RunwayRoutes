package com.runway.routes.domain.entity

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
    val distanceKm: Double? = null
) {

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
        distanceKm = distanceKm
    )
}

enum class RunwayType(val value: String) {
    VERT("vert"),
    AIRPORT("airport"),
}

enum class Owner(val value: String) {

    CA("ГА"),
    GA("АОН"),
    EA("ЭА"),
    MD("МО"),
    MUS("МВД"),
    FSB("ФСБ"),
    DOSAAF("ДОСААФ"),
    MES("МЧС");

    companion object {
        fun fromString(value: String): Owner? {
            values().forEach { owner ->
                if (value == owner.value) return owner
            }
            return null
        }
    }
}