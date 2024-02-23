package com.example.data.operations

import com.example.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersRetrofit {
    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: String,
        @Query("results") results: String,
        @Query("seed") seed: String
    ): Response<UsersResponse>

}