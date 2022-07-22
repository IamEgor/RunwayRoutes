package com.runway.routes.di

import com.runway.routes.Database
import com.runway.routes.data.LocationDataSource
import com.runway.routes.data.RunwayLocalDataSource
import com.runway.routes.data.RunwayRemoteDataSource
import com.runway.routes.data.ktorHttpClient
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "runway.db"

val appModule = module {

    factory {
        val driver: SqlDriver = AndroidSqliteDriver(
            Database.Schema,
            androidContext(),
            DATABASE_NAME
        )
        Database(driver)
    }

    single { LocationDataSource(get()) }
    single { RunwayLocalDataSource(get()) }
    single { RunwayRemoteDataSource(ktorHttpClient) }
}
