package com.example.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    //  Register functions
    fun setUserRegister(view: View)
    {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun loginProceed(view: View)
    {
        val intent = Intent(this, LoginProceedActivity::class.java)
        startActivity(intent)
    }

    fun passwordRecovery(view: View)
    {
        val intent = Intent(this, PasswordRecoveryActivity::class.java)
        startActivity(intent)
    }
}