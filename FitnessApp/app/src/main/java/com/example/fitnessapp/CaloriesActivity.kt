package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.fitnessapp.database.*
import java.text.SimpleDateFormat
import java.util.*

class CaloriesActivity : AppCompatActivity() {
    var userMain: User? = null

    var caloriesValue: Long = 0

    var caloriesGoal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories)

        userMain = intent.extras?.get("user") as User

        var calorieIntakeText = findViewById<TextView>(R.id.textViewCaloriesSentence)
        var progressBarCalorie = findViewById<ProgressBar>(R.id.progressBarCaloriesIntake)
        var progressBarCaloriePercentage = findViewById<TextView>(R.id.textViewProgressBarPercentage)

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        var info: Information? = userMain!!.userId?.let { myDatabase.DAO().getInformation(it) }

        var calories: List<Calories>? = userMain?.userId?.let { myDatabase.DAO().getCaloriesWithUserId(it) }

        if (info != null) {
            progressBarCalorie.max = (info.caloriesIntake)?.toInt()!!
            caloriesGoal = (info.caloriesIntake)?.toInt()!!
        }

        if(calories != null)
        {
            // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
            var rightNow = Calendar.getInstance()
            var day_date = SimpleDateFormat("dd")
            var day_now: Int = day_date.format(rightNow.getTime()).toInt()

            var day_calories: Int

            calories.forEach {
                day_calories = day_date.format(Calendar.getInstance().getTime()).toInt()
                if(day_calories == day_now)
                {
                    caloriesValue += it.calories!!
                }
            }
        }

        calorieIntakeText.text = "Today you have\nconsumed ${caloriesValue} cal!"
        progressBarCalorie.progress = caloriesValue.toInt()
        if (info != null) {
            progressBarCaloriePercentage.text = "${((caloriesValue*100)/ caloriesGoal).toInt()}%"
        }

    }

    fun submitCalories(view: View)
    {
        var calorieIntakeText = findViewById<TextView>(R.id.textViewCaloriesSentence)
        var progressBarCalorie = findViewById<ProgressBar>(R.id.progressBarCaloriesIntake)
        var progressBarCaloriePercentage = findViewById<TextView>(R.id.textViewProgressBarPercentage)

        var caloriesValueOnSubmitString = findViewById<EditText>(R.id.editTextNumberCaloriesIntake).text.toString()

        var caloriesValueOnSubmit = caloriesValueOnSubmitString.toLong()

        if(caloriesValueOnSubmitString.equals(""))
        {
            return
        }

        var calories: Calories = Calories(calories = caloriesValueOnSubmit, date=Calendar.getInstance().getTime(), userId = userMain?.userId)

        Log.d("Calories", calories.calories.toString())

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        myDatabase.DAO().insertCalories(calories)

        caloriesValue += caloriesValueOnSubmit

        progressBarCalorie.progress = caloriesValueOnSubmit.toInt()

        calorieIntakeText.text = "Today you have\nconsumed ${caloriesValue} cal!"
        progressBarCalorie.progress = caloriesValue.toInt()
        progressBarCaloriePercentage.text = "${((caloriesValue*100)/ caloriesGoal).toInt()}%"
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}