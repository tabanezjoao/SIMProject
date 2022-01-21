package com.example.fitnessapp

import android.content.ClipData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    // SensorModel
    public val sensorModel = SensorModel()

    //Database ...
    //public myDatabase ...

    public val valorInteiro = MutableLiveData<Int>(0)

    fun incValor() {
        // basicamente valorInteiro++;
        valorInteiro.value?.let { it ->
            valorInteiro.value = it+ 1
        }
        Log.e("incValor", "incValor()" + valorInteiro.value )
    }

    val selected = MutableLiveData<ClipData.Item>()

    fun select(item: ClipData.Item) {
        selected.value = item
    }
}