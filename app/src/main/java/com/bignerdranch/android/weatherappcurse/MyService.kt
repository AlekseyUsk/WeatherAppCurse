package com.bignerdranch.android.weatherappcurse

import android.app.IntentService
import android.content.Intent
import android.util.Log

const val KEY = "KEY"
class MyService : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "привет из сервиса")
        intent?.let {
            it.getStringExtra(KEY)
            Log.d("@@@", "привет из сервиса / ${it.getStringExtra(KEY)}")
        }
    }
}