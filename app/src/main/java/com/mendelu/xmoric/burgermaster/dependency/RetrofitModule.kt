package com.mendelu.xmoric.burgermaster.dependency

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mendelu.xmoric.burgermaster.model.Coordinates

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

val retrofitModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    val dispatcher = Dispatcher()
    httpClient.dispatcher(dispatcher)
    return httpClient.addInterceptor(httpLoggingInterceptor).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    val gson = GsonBuilder()
        .setLenient()
        .apply { registerTypeAdapter(Coordinates::class.java, DataDeserializer()) }
        .create()

    return Retrofit.Builder().baseUrl("https://637652b681a568fc25fbd797.mockapi.io/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

class DataDeserializer : JsonDeserializer<Coordinates> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Coordinates {
        val jsonArray = json?.asJsonArray
        Log.d("TAG", "$jsonArray")

        if(jsonArray != null){
            val data: MutableList<LatLng> = mutableListOf()
            for (limits in jsonArray){
                try {
                    data.add(
                        LatLng(
                            limits.asJsonArray.get(1).asDouble,
                            limits.asJsonArray.get(0).asDouble)
                    )

                }catch (exception: java.lang.Exception){
                    exception.printStackTrace()
                }
            }
            Log.d("TAG", "${data.size}")
            val dataObject = Coordinates()
            dataObject.allCoordinates = data
            return dataObject
        }
        return Coordinates()
    }

}
