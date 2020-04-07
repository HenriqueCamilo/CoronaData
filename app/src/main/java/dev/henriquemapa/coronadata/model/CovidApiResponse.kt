package dev.henriquemapa.coronadata.model


import com.google.gson.annotations.SerializedName

data class CovidApiResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("result")
    val result: Map<String, Date>
)