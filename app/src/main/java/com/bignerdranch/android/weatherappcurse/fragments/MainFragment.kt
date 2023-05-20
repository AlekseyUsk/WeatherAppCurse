package com.bignerdranch.android.weatherappcurse.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bignerdranch.android.weatherappcurse.API_KEY
import com.bignerdranch.android.weatherappcurse.adapter.ViewPagerAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentMainBinding
import com.bignerdranch.android.weatherappcurse.isPermissionGranted
import com.bignerdranch.android.weatherappcurse.model.WeatherModel
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject

class MainFragment : Fragment() {

    private val fragmentList =
        listOf<Fragment>(HoursFragment.newInstance(), DaysFragment.newInstance())
    private var tableList = listOf("Hours", "Days")

    lateinit var binding: FragmentMainBinding
    lateinit var pLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        requestWeatherData("London")
    }

    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tableList[position]
        }.attach()
    }

    // регистрация pLauncher : ActivityResultLauncher<String
    // калбек проверка в реальном времени дал ли разрешение пользователь и вернет либо true или false
    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is ${it}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            //Todo если нет разрешения от пользоватьеля то запускаю следующий код
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            //а если нет разрешения то код можно дописать
        }
    }

    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, { result -> parseResultWeatherData(result)
            },
            { error ->
                Log.d("MyLog", "ОШИБКА ${error}")
            }
        )
        queue.add(stringRequest)
    }

    private fun parseResultWeatherData(result: String) {
        val mainObject = JSONObject(result)
        //создал модельку с данными в которую я передам данные с сервера JsonObject
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            "", "",
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            ""
        )
        Log.d("MyLog", "City ${item.city}")
        Log.d("MyLog", "Time ${item.time}")
        Log.d("MyLog", "Condition ${item.condition}")
        Log.d("MyLog", "Temp ${item.currentTemp}")
        Log.d("MyLog", "Url ${item.imageUrl}")
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}