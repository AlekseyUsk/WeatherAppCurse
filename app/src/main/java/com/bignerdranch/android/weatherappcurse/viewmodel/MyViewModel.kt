package com.bignerdranch.android.weatherappcurse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val liveDataCurrent = MutableLiveData<String>()    //карточка сегодняшнего дня
    val liveDataList = MutableLiveData<List<String>>() //карточка двух последующих дней
}