package com.bignerdranch.android.weatherappcurse.fragments

import android.Manifest
import android.content.Intent
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bignerdranch.android.weatherappcurse.API_KEY
import com.bignerdranch.android.weatherappcurse.MainActivity
import com.bignerdranch.android.weatherappcurse.MyService
import com.bignerdranch.android.weatherappcurse.adapter.ViewPagerAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentMainBinding
import com.bignerdranch.android.weatherappcurse.isPermissionGranted
import com.bignerdranch.android.weatherappcurse.model.WeatherModel
import com.bignerdranch.android.weatherappcurse.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {

    private val fragmentList =
        listOf<Fragment>(HoursFragment.newInstance(), DaysFragment.newInstance())
    private var tableList = listOf("Hours", "Days")

    lateinit var binding: FragmentMainBinding
    lateinit var pLauncher: ActivityResultLauncher<String>
    private val viewModel : MyViewModel by activityViewModels()

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
        updateCurrentCard()
        requestWeatherData("London")

        requireActivity().startService(Intent(requireActivity(),MyService::class.java))
    }

    //Adapter viewPager TabLayout
    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tableList[position]
        }.attach()
    }

    /* регистрация pLauncher : ActivityResultLauncher<String
     калбек проверка в реальном времени дал ли разрешение пользователь и вернет либо true или false*/
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

    // запрос
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
            url, { result ->
                parseWeatherData(result)
            },
            { error ->
                Log.d("MyLog", "ОШИБКА ${error}")
            }
        )
        queue.add(stringRequest)
    }

    // тут вся кухня варится
    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        gettingInfoForTheCard(mainObject,list[0])
    }
    // обновление карточки
    private fun updateCurrentCard() = with(binding){
        viewModel.liveDataCurrent.observe(viewLifecycleOwner){ item ->
            val maxMin = "${item.maxTemp}ºC/${item.minTemp}ºC"
            tvData.text = item.time
            tvCity.text = item.city
            tvCurrentTemp.text = item.currentTemp
            tvCondition.text = item.condition
            tvMaxMin.text = maxMin
            Picasso.get().load("https:" + item.imageUrl).into(imWeather)
        }
    }

    // погода на один день - карточка
    private fun gettingInfoForTheCard(mainObject: JSONObject,weatherItem : WeatherModel) {
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp, weatherItem.minTemp,
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
        viewModel.liveDataCurrent.value = item
    }

    // погода на несколько дней
    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = ArrayList<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}