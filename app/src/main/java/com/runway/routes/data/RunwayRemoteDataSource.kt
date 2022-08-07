package com.runway.routes.data

import com.runway.routes.BuildConfig
import com.runway.routes.data.response.PointsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlin.time.ExperimentalTime


class RunwayRemoteDataSource(private val client: HttpClient) {

    companion object {
        private val HOST = "https://maps.aopa.ru"
    }

    @Deprecated("from network")
    @OptIn(ExperimentalTime::class)
    suspend fun load(): PointsResponse {
//        val response: PointsResponse
//        val measureTime = measureTime {
//            val text = RunwayApp.instance
//                .assets
//                .open("aopa-points-export.xml")
//                .bufferedReader().use { it.readText() }
//
//            response = XML.decodeFromString(text)
//        }
//        Log.w(
//            "RunwayRemoteDataSource",
//            "load() measureTime - $measureTime, size - ${response.point.size}, response - $response"
//        )
//        return response
        val clientBuilder: HttpRequestBuilder.() -> Unit = {
            url {
                path("export/exportFormRequest")
                parameters.apply {
                    append("exportType", "standart")
                    append("exportAll[]", "airport")
                    append("exportAll[]", "vert")
                    append("exportFormat", "xml")
                    append("api_key", BuildConfig.API_KEY)
                }
            }
        }
        return client.get(HOST, clientBuilder)
    }
}