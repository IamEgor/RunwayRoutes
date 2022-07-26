package com.runway.routes.feature.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.runway.routes.domain.entity.RunwayEntity

class MapClusterItem(private val entity: RunwayEntity) : ClusterItem {

    private val position: LatLng = LatLng(entity.lat, entity.lon)

    override fun getPosition(): LatLng = position

    override fun getTitle(): String = entity.nameRu

    override fun getSnippet(): String = entity.nameEn
}