package com.teimurgasanov.isitcold.models

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("name") var name: String,
        @SerializedName("country") var country: String
)