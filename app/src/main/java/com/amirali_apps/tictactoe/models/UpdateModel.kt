package com.amirali_apps.tictactoe.models

import com.google.gson.annotations.SerializedName

data class UpdateModel(
    @SerializedName("latest_version")
    var latestVersion: String,
    @SerializedName("download_url")
    var downloadUrl: String
)

