package com.bignerdranch.android.weatherappcurse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.weatherappcurse.adapter.WeatherAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentHourseBinding
import com.bignerdranch.android.weatherappcurse.model.WeatherModel
import com.bignerdranch.android.weatherappcurse.viewmodel.MyViewModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHourseBinding
    private lateinit var adapter: WeatherAdapter
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        displayThewWeatherListByHour()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
    }

    private fun getWeatherListByHour(weatherItem: WeatherModel): List<WeatherModel> {
        val hoursArray = JSONArray(weatherItem.hours)
        val list = ArrayList<WeatherModel>()
        for (i in 0 until hoursArray.length()) {
            val item = WeatherModel(
                weatherItem.city,
                (hoursArray[i] as JSONObject).getString("time"),//взял из массива обжект и достал time по позиции i получив ВРЕМЯ
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "", "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    private fun displayThewWeatherListByHour() {
        viewModel.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getWeatherListByHour(it))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HoursFragment().apply {
            }
    }
}