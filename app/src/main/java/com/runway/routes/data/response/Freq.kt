package com.runway.routes.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("freq")
data class Freq(
    val list: List<FreqItem>
)

@Serializable
@SerialName("item")
data class FreqItem(
    @XmlElement(true) val id: String,
    @XmlElement(true) val freq: String?,
    @XmlElement(true) val callsign: String?,
    @XmlElement(true) val type: String?
)