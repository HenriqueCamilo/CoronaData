package dev.henriquemapa.coronadata

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.text.SimpleDateFormat
import java.util.*
import dev.henriquemapa.coronadata.model.getPaises

class MainActivity : AppCompatActivity() {

    companion object{
        const val BASE_URL = "https://covidapi.info/"
    }

    private lateinit var paisEditText: EditText
    private lateinit var paisValueTextView: TextView
    private lateinit var confirmadosValueTextView: TextView
    private lateinit var mortosValueTextView: TextView
    private lateinit var curadosValuetextView: TextView
    private lateinit var dataValuetextView: TextView



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.paisEditText = findViewById(R.id.paisEditText)
        this.paisValueTextView = findViewById(R.id.paisValueTextView)
        this.confirmadosValueTextView = findViewById(R.id.confirmadosValueTextView)
        this.mortosValueTextView = findViewById(R.id.mortosValueTextView)
        this.curadosValuetextView = findViewById(R.id.curadosValueTextView)
        this.dataValuetextView = findViewById(R.id.dataValueTextView)
        var result: Map<Map>

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener{ getCoronaData(result) }


    }

    private fun getCoronaData(result: Any) {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CovidInterface::class.java)

        val call = service.getCoronaCasesByCountry(result.toString())

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

                    var calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_WEEK, -1)

                    var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val todayString = formatter.format(calendar.time)

                    paisValueTextView.text = paisEditText.text
                    confirmadosValueTextView.text = responseCovidData.result.get(todayString)?.confirmed.toString()
                    mortosValueTextView.text = responseCovidData.result.get(todayString)?.deaths.toString()
                    curadosValuetextView.text = responseCovidData.result.get(todayString)?.recovered.toString()
                    val formatter2 = SimpleDateFormat("dd-MM-yyyy")
                    val todayStringFormated = formatter2.format(calendar.time)
                    dataValuetextView.text = todayStringFormated.toString()
//
//////                    val current = LocalDateTime.now()
//////
//////
////                    curadosValuetextView.text = responseCovidData.result.toString()
//
//
////                    var calendar = Calendar.getInstance()
////                    calendar.add(Calendar.DAY_OF_WEEK, -1 )
//                    dataValuetextView.text = todayString
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
