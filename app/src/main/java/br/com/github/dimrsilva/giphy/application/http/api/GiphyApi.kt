package br.com.github.dimrsilva.giphy.application.http.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/trending")
    fun trending(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Call<GiphyResultPayload>
}