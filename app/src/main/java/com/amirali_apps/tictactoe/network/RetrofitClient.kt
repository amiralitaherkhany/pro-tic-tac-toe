package com.amirali_apps.tictactoe.network

//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit: Retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://gist.githubusercontent.com/amiralitaherkhany/")
        .build()

object RetrofitClient {
    val retrofitService: CheckUpdateRequests = retrofit.create(CheckUpdateRequests::class.java)
}


