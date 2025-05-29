package com.example.aridagrisolutions

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aridagrisolutions.databinding.ActivityViewWeatherBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class View_weather : AppCompatActivity() {
    private var _binding: ActivityViewWeatherBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Weather"
        val lat=intent.getStringExtra("lat")
        val long=intent.getStringExtra("long")
        Toast.makeText(this,"Latitude: $lat Longitude: $long", Toast.LENGTH_LONG).show()
        getJsonData(lat,long)
    }
    private fun getJsonData(lat: String?, long: String?) {
        val myAPIKEY="bacc02c4772e2787d60ed20985cfb81b"
        val queue = Volley.newRequestQueue(this)
        val url ="https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${myAPIKEY}"
        val jsonRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    setValues(response)
                },
                { Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show() })
        queue.add(jsonRequest)
    }



    @SuppressLint("SetTextI18n")
    private fun setValues(response: JSONObject?) {
        //city.text= response?.getString("name")
        //var lat = response?.getJSONObject("coord")?.getString("lat")
        //var long=response?.getJSONObject("coord")?.getString("lon")
        //coordinates.text="${lat} , ${long}"


        val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date())
        binding.updatedAt.text=updatedAtText
        binding.status.text=response?.getJSONArray("weather")?.getJSONObject(0)?.getString("description")
        binding.weatherMain.text=response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")
        var tempr=response?.getJSONObject("main")?.getString("temp")
        tempr=((((tempr)?.toFloat()?.minus(273.15)))?.toInt()).toString()
        binding.temp.text="${tempr}°C"
        var mintemp=response?.getJSONObject("main")?.getString("temp_min")
        mintemp=((((mintemp)?.toFloat()?.minus(273.15)))?.toInt()).toString()
        binding.tempMin.text="Min: $mintemp °C"
        var maxtemp=response?.getJSONObject("main")?.getString("temp_max")
        maxtemp=((ceil((maxtemp)?.toFloat()?.minus(273.15)!!)).toInt()).toString()
        binding.tempMax.text="Max: $maxtemp °C"
        binding.pressure.text=response?.getJSONObject("main")?.getString("pressure")
        binding.humidity.text=response?.getJSONObject("main")?.getString("humidity")+"%"
        binding.wind.text=response?.getJSONObject("wind")?.getString("speed")
        val sunrise=response?.getJSONObject("sys")?.getLong("sunrise")
        if (sunrise != null) {
            binding.sunrise.text=SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
        }
        val sunset=response?.getJSONObject("sys")?.getLong("sunset")
        if (sunset != null) {
            binding.sunset.text=SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
        }
    }
}


