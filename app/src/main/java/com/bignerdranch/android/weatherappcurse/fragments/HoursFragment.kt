package com.bignerdranch.android.weatherappcurse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.weatherappcurse.adapter.WeatherAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentHourseBinding
import com.bignerdranch.android.weatherappcurse.model.WeatherModel

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHourseBinding
    private lateinit var adapter: WeatherAdapter

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
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        val list = listOf(WeatherModel("Новосибирск", "22:00", "sunny", "45", "", "", "", ""),WeatherModel("Омск", "12:00", "sunny", "25", "", "", "", ""))
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HoursFragment().apply {
            }
    }
}