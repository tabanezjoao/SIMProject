package com.example.fitnessapp

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData


class SensorModel: SensorEventListener {
    companion object {
        private var sensorManager: SensorManager? = null
        private var stepSensor : Sensor? = null

        fun initializeSensorManager(context: Context) {
            if(sensorManager == null) {
                sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                Log.e("sensorManager","initializeSensorManager()")
            }
        }
    }

    private var running = false
    public  var totalSteps = MutableLiveData<Float>().apply { value = 0f }
    public var currentSteps = MutableLiveData<Int>().apply { value = 0 }
    private var previousTotalSteps = 0f
    public var statusMessage = ""

    init {
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            statusMessage = "No sensor detected on this device"
        } else {
            //resetSteps()
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            statusMessage = "Sensor Detected"
            Log.e("init","registerListener")
        }
    }

    public fun setRunningState(state: Boolean) {
        this.running = state
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running)
        {
            totalSteps.value = event!!.values[0]

            Log.e("steps:", "totalSteps.value = "+ totalSteps.value)

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            val currentStepsNow = totalSteps.value!!.toInt() - previousTotalSteps.toInt()

            if(currentStepsNow> 0 && previousTotalSteps==0F){
                previousTotalSteps = currentStepsNow.toFloat()
            }

            currentSteps.value = currentStepsNow
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}