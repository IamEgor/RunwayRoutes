package com.runway.routes.data.utils

import io.ktor.client.features.json.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.serialization.XML

class XMLSerializer(private val xmlSerializer: XML = XML()) : JsonSerializer {

    @OptIn(InternalSerializationApi::class)
    override fun read(type: TypeInfo, body: Input): Any {
        val text = body.readText()
        return xmlSerializer.decodeFromString(type.type.serializer(), text)
    }

    override fun write(data: Any, contentType: ContentType): OutgoingContent {
        val serializer = xmlSerializer.serializersModule.serializer<Any>()
        val string = xmlSerializer.encodeToString(serializer, data)
        return TextContent(string, contentType)
    }
}