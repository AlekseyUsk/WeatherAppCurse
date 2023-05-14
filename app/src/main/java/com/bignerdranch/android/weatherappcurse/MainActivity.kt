package com.bignerdranch.android.weatherappcurse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.weatherappcurse.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,MainFragment.newInstance())
            .commit()
    }
}