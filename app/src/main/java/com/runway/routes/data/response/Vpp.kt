package com.runway.routes.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("vpp")
data class Vpp(
    val list: List<VppItem>
)

@Serializable
@SerialName("item")
data class VppItem(
    @XmlElement(true) val id: String,
    @XmlElement(true) val name: String?,
    @XmlElement(true) val length: String?,
    @XmlElement(true) val width: String?,
    @XmlElement(true) val porog1_lon: String?,
    @XmlElement(true) val porog1_lat: String?,
    @XmlElement(true) val porog2_lon: String?,
    @XmlElement(true) val porog2_lat: String?,
    @XmlElement(true) val kurs: String?,
    @XmlElement(true) val kurs_ist: String?,
    @XmlElement(true) val pokr_id: String?,
    @XmlElement(true) val lights_id: String?,
    @XmlElement(true) val korobochka: String?,
    @XmlElement(true) val pokr: String?,
    @XmlElement(true) val pokr_code: String?,
    @XmlElement(true) val lights: String?
)