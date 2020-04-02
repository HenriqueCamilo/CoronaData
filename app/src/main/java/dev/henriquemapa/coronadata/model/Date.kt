package dev.henriquemapa.coronadata.model


import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("recovered")
    val recovered: Int
)