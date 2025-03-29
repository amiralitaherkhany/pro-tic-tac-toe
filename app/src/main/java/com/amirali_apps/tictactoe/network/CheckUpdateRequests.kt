package com.amirali_apps.tictactoe.network

import com.amirali_apps.tictactoe.models.UpdateModel
import retrofit2.http.GET

interface CheckUpdateRequests {
    @GET("64b54adbb11b526a8af4c7fc6f395af6/raw/")
    suspend fun checkForUpdate(): UpdateModel
}
