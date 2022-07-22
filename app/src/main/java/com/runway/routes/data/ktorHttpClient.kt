package com.runway.routes.data

import android.util.Log
import com.runway.routes.data.utils.XMLSerializer
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*


internal val ktorHttpClient = HttpClient(Android) {
    Json {
        serializer = XMLSerializer()
        accept(ContentType.Text.Xml)
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor =>", message)
            }

        }
        level = LogLevel.ALL
    }
    install(ResponseObserver) {
        onResponse { response ->
            Log.v("HTTP status:", "${response.status.value}")
        }
    }
    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}