package com.example.newyorkburritos.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val BASE_URL = "https://api.yelp.com/v3/"
object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor()).build()

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: YelpService by lazy {
        retrofit.create(YelpService ::class.java)
    }
}