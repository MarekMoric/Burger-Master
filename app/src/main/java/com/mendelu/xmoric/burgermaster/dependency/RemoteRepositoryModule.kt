package com.mendelu.xmoric.burgermaster.dependency

import com.mendelu.xmoric.burgermaster.communication.MapAPI
import com.mendelu.xmoric.burgermaster.communication.MapRemoteRepositaryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {
    single { provideMapRemoteRepository(get()) }
}

fun provideMapRemoteRepository(mapAPI: MapAPI): MapRemoteRepositaryImpl
        = MapRemoteRepositaryImpl(mapAPI)

