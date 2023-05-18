package com.bignerdranch.android.weatherappcurse

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(p : String) : Boolean{
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity,p) == PackageManager.PERMISSION_GRANTED
}
// создал фун расширения для фрагментов в круглых скобках передаю
// параметр p - название разрешения и возвращать либо true либо false
// после чего проверку устраиваю ContextCompat.checkSelfPermission - эта функция выдает число Int
// -1 отклонил пользователь разрешение 0 - дал разрешение пользователь
// activity as AppCompatActivity - так как у фрагментов есть активити то указываем это
//,p - указываем название разрешение например место положение
// == PackageManager.PERMISSION_GRANTED делаем проверку
// ContextCompat.checkSelfPermission(activity as AppCompatActivity,p) - эта фун проверяет и возвращает число
// которое потом сравниваем с == PackageManager.PERMISSION_GRANTED и если слева фун и справа одинаковые то выдапст true
// PERMISSION_GRANTED - это ноль 0 в противном случае false вернет
// теперь идем к фрагменту и делаем проверку
// во фрагменте нам нужен специальный лаунчер который юудет запускать и спрашивать разрешение диалог такой
// вот он lateinit var pLauncher : ActivityResultLauncher<String>
// потом нужно будет лаунчер проинициализтровать
// регистрация pLauncher : ActivityResultLauncher<String
// калбек проверка в реальном времени дал ли разрешение пользователь и вернет либо true или false
//private fun permissionListener() {
//    pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        Toast.makeText(activity,"Permission is ${it}", Toast.LENGTH_SHORT).show() }}}
