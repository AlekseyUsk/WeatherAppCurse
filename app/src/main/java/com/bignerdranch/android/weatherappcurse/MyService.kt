package com.bignerdranch.android.weatherappcurse

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyService : IntentService("IntentService") {
    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "привет")
    }
}