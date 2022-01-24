package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.database.Weight
import java.text.SimpleDateFormat
import java.util.*

class WeightActivity : AppCompatActivity() {

    var userMain: User? = null

    var todayInputWeight: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        userMain = intent.extras?.get("user") as User

        var alertWeight = findViewById<TextView>(R.id.textViewAlertWeight)

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        var weights: List<Weight> = userMain!!.userId?.let { myDatabase.DAO().getWeightWithUserId(it) }!!

        if(weights != null)
        {
            // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
            var rightNow = Calendar.getInstance()
            var day_date = SimpleDateFormat("dd")
            var day_now: Int = day_date.format(rightNow.getTime()).toInt()

            var day_weight: Int

            weights.forEach {
                day_weight = day_date.format(Calendar.getInstance().getTime()).toInt()
                if(day_weight == day_now)
                {
                    //vamos avisar que ja introduziu o peso hoje!
                    alertWeight.visibility = View.VISIBLE
                    todayInputWeight = true
                }
            }
        }
    }

    fun submitWeight(view: View)
    {
        var weightValueString = findViewById<EditText>(R.id.editTextNumberCaloriesIntake).text.toString()
        var alertWeight = findViewById<TextView>(R.id.textViewAlertWeight)

        var weightValue = weightValueString.toLong()

        if(weightValueString.equals(""))
        {
            return
        }

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        if(todayInputWeight)
        {
            var weights: List<Weight> = userMain!!.userId?.let { myDatabase.DAO().getWeightWithUserId(it) }!!

            if(weights != null)
            {
                // queremos mostrar a informaçao caso ja tenhamos uma quantidade de agua adicionada
                var rightNow = Calendar.getInstance()
                var day_date = SimpleDateFormat("dd")
                var day_now: Int = day_date.format(rightNow.getTime()).toInt()

                var day_weight: Int

                weights.forEach {
                    day_weight = day_date.format(Calendar.getInstance().getTime()).toInt()
                    if(day_weight == day_now)
                    {
                        it.date = Calendar.getInstance().time
                        it.weight = weightValue
                        myDatabase.DAO().updateWeight(it)
                    }
                }
            }
        }
        else
        {
            var weight: Weight = Weight(weight = weightValue, date=Calendar.getInstance().time, userId = userMain?.userId)

            Log.d("Weight", weight.weight.toString())

            myDatabase.DAO().insertWeights(weight)
            alertWeight.visibility = View.VISIBLE
            todayInputWeight = true
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}