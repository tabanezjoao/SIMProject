package com.example.fitnessapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.fitnessapp.database.Information
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.database.Weight
import java.io.Serializable
import java.util.*

class InformationActivity : AppCompatActivity() {
    var userMain: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val userFound = intent.extras?.get("user") as User

        userMain = userFound

        var seekBarHeight = findViewById<SeekBar>(R.id.seekBarHeight)
        var textHeight = findViewById<TextView>(R.id.textViewHeight)

        seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textHeight.setText(progress.toString() + " cm")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                return
            }
        })

        var seekBarWeight = findViewById<SeekBar>(R.id.seekBarWeight)
        var textWeight = findViewById<TextView>(R.id.textViewWeight)

        seekBarWeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textWeight.setText(progress.toString() + " Kg")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                return
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                return
            }
        })

        var helloUser = findViewById<TextView>(R.id.textViewHelloUser)
        helloUser.setText("Hello " + userFound.username +"!")
    }

    fun maleButtonClick(view: View)
    {
        var maleButton = findViewById<Button>(R.id.buttonMale)
        var femaleButton = findViewById<Button>(R.id.buttonFemale)
        maleButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3952FB"))
        femaleButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#562197"))
    }

    fun femaleButtonClick(view: View)
    {
        var maleButton = findViewById<Button>(R.id.buttonMale)
        var femaleButton = findViewById<Button>(R.id.buttonFemale)
        maleButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#562197"))
        femaleButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3952FB"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitInformation(view: View)
    {
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)
        var seekBarHeight = findViewById<SeekBar>(R.id.seekBarHeight)
        var seekBarWeight = findViewById<SeekBar>(R.id.seekBarWeight)
        var age = findViewById<EditText>(R.id.editTextNumberAge)
        var maleButton = findViewById<Button>(R.id.buttonMale)
        var femaleButton = findViewById<Button>(R.id.buttonFemale)
        var gender: String? = null

        if(maleButton.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#3952FB")))
        {
            gender = "Male"
        }
        else if(femaleButton.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#3952FB")))
        {
            gender = "Female"
        }

        var info: Information = Information(age= age.text.toString().toLong(), height = seekBarHeight.progress.toString().toLong(), gender = gender, userId = userMain?.userId)

        Log.d("Utilizador", "Exameplo")
        Log.d("Age", info.age.toString())
        Log.d("Height", info.height.toString())
        info.gender?.let { Log.d("Gender", it) }

        myDatabase.DAO().insertInformation(info)

        info = userMain?.userId?.let { myDatabase.DAO().getInformation(it) }!!

        var weight: Weight = Weight(date = Calendar.getInstance().getTime(), informationId = info.informationId, weight = seekBarWeight.progress.toString().toLong())

        Log.d("Weight", weight.weight.toString())

        myDatabase.DAO().insertWeights(weight)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", userMain as Serializable)
        startActivity(intent)
        finish()
    }
}