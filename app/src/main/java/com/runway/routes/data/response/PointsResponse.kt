package com.runway.routes.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement


@Serializable
@SerialName("points")
data class PointsResponse(
    val point: List<Point>
)

@Serializable
@SerialName("point")
data class Point(
    @XmlElement(true) val id: Long,
    @XmlElement(true) val lat: Double,
    @XmlElement(true) val lon: Double,
    @XmlElement(true) val type: String,//"vert", "airport"
    @XmlElement(true) val last_update: String,
    @XmlElement(true) val active: Long,
    @XmlElement(true) val verified: String,
    @XmlElement(true) val international: String,
    @XmlElement(true) val name: String,
    @XmlElement(true) val name_ru: String,
    @XmlElement(true) val city: String,
    @XmlElement(true) val belongs: String,
    @XmlElement(true) val index_ru: String,
    @XmlElement(true) val index: String,
    @SerialName("class")
    @XmlElement(true) val clazz: String,
    @XmlElement(true) val height: String?,
    @XmlElement(true) val delta_m: String?,
    @XmlElement(true) val worktime: String,
    @XmlElement(true) val email: String,
    @XmlElement(true) val website: String,
    @XmlElement(true) val infrastructure: String,
    @XmlElement(true) val comments: String,
    @XmlElement(true) val country_id: String?,
    @XmlElement(true) val img_plan: String,
    @XmlElement(true) val img_aerial: String,
    @XmlElement(true) val region: String,
    @XmlElement(true) val country_name: String,
    @XmlElement(true) val utc_offset: String,
    @XmlElement(true) val civil_twilight_start: String,
    @XmlElement(true) val civil_twilight_end: String,
    val vpp: Vpp?,
    val freq: Freq?,
    val contact: Contact?,
    val fuel: Fuel?
)