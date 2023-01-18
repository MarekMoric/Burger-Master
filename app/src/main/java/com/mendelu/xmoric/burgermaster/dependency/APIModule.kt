package com.mendelu.xmoric.burgermaster.dependency

import com.mendelu.xmoric.burgermaster.communication.MapAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val  apiModule = module {
    single { provideMapApi(get()) }
}

fun provideMapApi(retrofit: Retrofit): MapAPI
        = retrofit.create(MapAPI::class.java)
