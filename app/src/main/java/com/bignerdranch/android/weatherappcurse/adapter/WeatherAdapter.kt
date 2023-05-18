package com.bignerdranch.android.weatherappcurse.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.weatherappcurse.databinding.ListItemBinding
import com.bignerdranch.android.weatherappcurse.model.WeatherModel

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        fun bind(item: WeatherModel) = with(binding) {
            textViewData.text = item.time
            textViewCondition.text = item.condition
            textViewTemp.text = item.currentTemp
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel>(){
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem  == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("Not yet implemented")
    }
}