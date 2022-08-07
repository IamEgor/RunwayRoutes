package com.runway.routes.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("fuel")
data class Fuel(
    val list: List<FuelItem>
)

@Serializable
@SerialName("item")
data class FuelItem(
    @XmlElement(true) val id: String,
    @XmlElement(true) val type_id: String,
    @XmlElement(true) val exists_id: String
)