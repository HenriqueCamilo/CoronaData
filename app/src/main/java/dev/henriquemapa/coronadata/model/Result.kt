package dev.henriquemapa.coronadata.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName(value = "2020-03-31", alternate = ["2020-04-01", "2020-04-02"])
    val Date: Date
)