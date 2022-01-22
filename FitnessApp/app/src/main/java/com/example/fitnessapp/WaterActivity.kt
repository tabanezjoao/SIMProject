package com.example.fitnessapp

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.database.Water
import java.text.SimpleDateFormat
import java.util.*

class WaterActivity : AppCompatActivity() {
    var userMain: User? = null

    var waterValue: Long = 0

    var waterCups: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)

        userMain = intent.extras?.get("user") as User

        var subButton = findViewById<Button>(R.id.buttonHydrationSub)
        var hydrationText = findViewById<TextView>(R.id.textViewHydrationSentence)
        var progressBarWater = findViewById<ProgressBar>(R.id.progressBarHydration)

        subButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#655E5E"))

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        var waters: List<Water>? = userMain?.userId?.let { myDatabase.DAO().getWatersWithUserId(it) }

        if(waters != null)
        {
            // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
            var rightNow = Calendar.getInstance()
            var day_date = SimpleDateFormat("dd")
            var day_now: Int = day_date.format(rightNow.getTime()).toInt()

            var day_water: Int

            waters.forEach {
                day_water = day_date.format(Calendar.getInstance().getTime()).toInt()
                if(day_water == day_now)
                {
                    waterValue += it.water!!
                }
            }
        }

        hydrationText.text = "Today you took\n${waterValue} ml of water!"
        progressBarWater.progress = waterValue.toInt()
    }

    fun addCupWater(view: View)
    {
        var subButton = findViewById<Button>(R.id.buttonHydrationSub)

        subButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#562197"))

        waterCups++

        var waterCupsText = findViewById<TextView>(R.id.textViewHydrationQuantity)

        waterCupsText.text = "${waterCups}x Glass 200 ml"
    }

    fun subCupWater(view: View)
    {
        if(waterCups != 1)
        {
            waterCups--

            var waterCupsText = findViewById<TextView>(R.id.textViewHydrationQuantity)

            waterCupsText.text = "${waterCups}x Glass 200 ml"

            if(waterCups == 1)
            {
                var subButton = findViewById<Button>(R.id.buttonHydrationSub)

                subButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#655E5E"))
            }
        }
    }

    fun submitCupWater(view: View)
    {
        var progressBarWater = findViewById<ProgressBar>(R.id.progressBarHydration)
        var hydrationText = findViewById<TextView>(R.id.textViewHydrationSentence)

        var waterValueOnSubmit: Int = waterCups * 200

        var water: Water = Water(water = waterValueOnSubmit.toLong(), date=Calendar.getInstance().getTime(), userId = userMain?.userId)

        Log.d("Water", water.water.toString())

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        myDatabase.DAO().insertWaters(water)

        waterValue += waterValueOnSubmit

        progressBarWater.progress = waterValue.toInt()

        hydrationText.text = "Today you took\n${waterValue} ml of water!"
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
