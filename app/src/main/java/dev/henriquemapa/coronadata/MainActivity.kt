package dev.henriquemapa.coronadata

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import dev.henriquemapa.coronadata.model.CovidApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    companion object{
        const val BASE_URL = "https://covidapi.info/"
    }

    private lateinit var paisEditText: EditText
    private lateinit var paisValueTextView: TextView
    private lateinit var confirmadosValueTextView: TextView
    private lateinit var mortosValueTextView: TextView
    private lateinit var curadosValuetextView: TextView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.paisEditText = findViewById(R.id.paisEditText)
        this.paisValueTextView = findViewById(R.id.paisValueTextView)
        this.confirmadosValueTextView = findViewById(R.id.confirmadosValueTextView)
        this.mortosValueTextView = findViewById(R.id.mortosValueTextView)
        this.curadosValuetextView = findViewById(R.id.curadosValueTextView)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(View.OnClickListener { getCoronaData() })


    }

    private fun getCoronaData() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CovidInterface::class.java)

        val call = service.getCoronaCasesByCountry(paisEditText.text.toString())

        call.enqueue(object : Callback<CovidApiResponse>{
            override fun onFailure(call: Call<CovidApiResponse>?, t: Throwable?) {
                Toast.makeText(baseContext, t?.message, Toast.LENGTH_LONG).show()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<CovidApiResponse>?,
                response: Response<CovidApiResponse>?
            ) {
                if(response?.code() == 200){
                    val responseCovidData = response.body()!!

                    paisValueTextView.text = paisEditText.text
                    confirmadosValueTextView.text = responseCovidData.result.Date.confirmed.toString()
                    mortosValueTextView.text = responseCovidData.result.Date.deaths.toString()
//                    val current = LocalDateTime.now()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                    curadosValuetextView.text = current.format(formatter).toString()
                    curadosValuetextView.text = responseCovidData.result.Date.recovered.toString()
                }
            }

        })

    }


////                if(response?.code() == 200){
////                    val responseWeather = response.body()!!
////
////                    cityValueTextView.text = responseWeather.name
////                    weatherValueTextView.text = "${responseWeather.weather[0].main} (${responseWeather.weather[0].description})"
////                    temperatureValueTextView.text = "${responseWeather.main.temp} C"
////                    feelsLikeValueTextView.text = "${responseWeather.main.feelsLike} C"
////                }
////            }
////
////        })
}
