package com.example.newyorkburritos.api

import com.example.newyorkburritos.models.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    fun searchRestaurants(
//        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm:String,
//        @Query("limit") limit:Int,
//        @Query("offset") offset:Int,
        @Query("location") location:String) : Call<YelpSearchResult>

    @GET("businesses/search")
    suspend fun searchRestaurantsCoroutine(
//        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm:String,
//        @Query("limit") limit:Int,
//        @Query("offset") offset:Int,
        @Query("location") location:String) : YelpSearchResult
}


