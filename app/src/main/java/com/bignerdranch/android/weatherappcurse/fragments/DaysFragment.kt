package com.bignerdranch.android.weatherappcurse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.weatherappcurse.R
import com.bignerdranch.android.weatherappcurse.adapter.WeatherAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentDaysBinding
import com.bignerdranch.android.weatherappcurse.databinding.FragmentMainBinding
import com.bignerdranch.android.weatherappcurse.viewmodel.MyViewModel

class DaysFragment : Fragment() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: FragmentDaysBinding
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    //инициализация recyclerView и заполнение
    private fun initRecyclerView() = with(binding) {
        recyclerViewDays.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        recyclerViewDays.adapter = adapter
        viewModel.liveDataList.observe(viewLifecycleOwner) {
            adapter.submitList(it.subList(1, it.size))
        }

    }

    companion object {
        fun newInstance() = DaysFragment()
    }
}