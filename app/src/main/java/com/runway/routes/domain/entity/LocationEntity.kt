package com.runway.routes.domain.entity

data class LocationEntity(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        val DEFAULT = LocationEntity(0.0, 0.0)
    }
}