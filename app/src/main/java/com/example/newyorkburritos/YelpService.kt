package com.example.newyorkburritos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

public interface YelpService {
    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm:String,
        @Query("limit") limit:Int,
        @Query("offset") offset:Int,
        @Query("location") location:String) : Call<YelpSearchResult>
}


