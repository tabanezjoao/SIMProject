package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun getUserRegistration(view: View)
    {
        val username = findViewById<TextView>(R.id.editTextUsername).text.toString()
        val password = findViewById<TextView>(R.id.editTextPassword).text.toString()
        val email = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val phone = findViewById<TextView>(R.id.editTextPhone).text.toString()
        Log.d("username", username)
        Log.d("password", password)
        Log.d("email", email)
        Log.d("phone", phone)
    }
}