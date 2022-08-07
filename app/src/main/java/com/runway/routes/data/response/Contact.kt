package com.runway.routes.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("contact")
data class Contact(
    val list: List<ContactItem>
)

@Serializable
@SerialName("item")
data class ContactItem(
    @XmlElement(true) val id: String,
    @XmlElement(true) val type_id: String?,
    @XmlElement(true) val fio: String?,
    @XmlElement(true) val value: String?,
    @XmlElement(true) val email: String?,
    @XmlElement(true) val type: String?
)