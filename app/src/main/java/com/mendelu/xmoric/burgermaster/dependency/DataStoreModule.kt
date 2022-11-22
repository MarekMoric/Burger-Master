package com.mendelu.xmoric.burgermaster.dependency

import android.content.Context
import com.mendelu.xmoric.burgermaster.datastore.DataStoreRepositoryImpl
import com.mendelu.xmoric.burgermaster.datastore.IDataStoreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideDataStoreRepository(androidContext()) }
}

fun provideDataStoreRepository(context: Context): IDataStoreRepository
        = DataStoreRepositoryImpl(context)