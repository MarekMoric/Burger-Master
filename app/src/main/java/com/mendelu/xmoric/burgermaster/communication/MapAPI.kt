package com.mendelu.xmoric.burgermaster.communication

import com.mendelu.xmoric.burgermaster.model.Coordinates
import com.mendelu.xmoric.burgermaster.model.Store
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MapAPI {
    @Headers("Content-Type: application/json")
    @GET("stores")
    suspend fun getStores(): Response<List<Store>>

    @Headers("Content-Type: application/json")
    @GET("brno")
    suspend fun getBrnoBoundaries(): Response<List<Coordinates>>
//    suspend fun getBrnoBoundaries(): Response<Coordinates>
}