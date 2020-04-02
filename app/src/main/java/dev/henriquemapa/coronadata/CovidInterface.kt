package dev.henriquemapa.coronadata

import dev.henriquemapa.coronadata.model.CovidApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface CovidInterface {


    @GET("api/v1/country/{country}/latest")
    fun getCoronaCasesByCountry(@Path("country") country:String): Call<CovidApiResponse>



}