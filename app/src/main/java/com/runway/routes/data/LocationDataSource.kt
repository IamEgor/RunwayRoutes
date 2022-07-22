package com.runway.routes.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.runway.routes.domain.entity.LocationEntity
import com.runway.routes.utils.awaitResult
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class LocationDataSource(context: Context) {

    private val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
    private val provider = LocationServices.getFusedLocationProviderClient(context)
    private val mutex = Mutex()

    private lateinit var currentLocation: LocationEntity

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): LocationEntity = mutex.withLock {

        if (::currentLocation.isInitialized) return currentLocation

        val location = provider.getCurrentLocation(priority, null)
            .awaitResult() ?: throw IllegalStateException()

        currentLocation = LocationEntity(location.latitude, location.longitude)

        return@withLock currentLocation
    }
}