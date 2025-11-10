package com.depi.bookdiscovery.network

import com.depi.bookdiscovery.dto.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") searchTerms: String,
        @Query("maxResults") maxResults: Int,
        @Query("startIndex") startIndex: Int
    ): Response<BooksResponse>

}