package com.depi.bookdiscovery.network

import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {

    private val gson = GsonBuilder().serializeNulls().create()
    private const val BASE_URL = "https://www.googleapis.com"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}