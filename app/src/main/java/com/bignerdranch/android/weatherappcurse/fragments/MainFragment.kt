package com.bignerdranch.android.weatherappcurse.fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.bignerdranch.android.weatherappcurse.adapter.ViewPagerAdapter
import com.bignerdranch.android.weatherappcurse.databinding.FragmentMainBinding
import com.bignerdranch.android.weatherappcurse.isPermissionGranted
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private val fragmentList = listOf<Fragment>(HoursFragment.newInstance(),DaysFragment.newInstance())
    private var tableList = listOf("Hours","Days")

    lateinit var binding: FragmentMainBinding
    lateinit var pLauncher: ActivityResultLauncher<String>

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
    }

    private fun init() = with(binding){
        val adapter = ViewPagerAdapter(activity as FragmentActivity,fragmentList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){
    tab,position -> tab.text = tableList[position]
        }.attach()
    }

    // регистрация pLauncher : ActivityResultLauncher<String
    // калбек проверка в реальном времени дал ли разрешение пользователь и вернет либо true или false
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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}