package com.amirali_apps.tictactoe.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val retrofit: Retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://gist.githubusercontent.com/amiralitaherkhany/")
        .build()

interface CheckUpdateRequests {
    @GET("64b54adbb11b526a8af4c7fc6f395af6/raw/")
    suspend fun checkForUpdate(): UpdateModel
}

object RetrofitClient {
    val retrofitService: CheckUpdateRequests = retrofit.create(CheckUpdateRequests::class.java)
}


