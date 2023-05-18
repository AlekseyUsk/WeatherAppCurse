package com.bignerdranch.android.weatherappcurse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weatherappcurse.model.WeatherModel

class MyViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherModel>()    //карточка сегодняшнего дня
    val liveDataList = MutableLiveData<List<WeatherModel>>() //карточка двух последующих дней
}