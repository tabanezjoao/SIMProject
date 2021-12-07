package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun setUserRegister(view: View)
    {
        setContentView(R.layout.activity_register)
    }


    fun getUserRegistration(view: View)
    {
        val usernameInput = findViewById<TextView>(R.id.editTextUsername).text.toString()
        val passwordInput = findViewById<TextView>(R.id.editTextPassword).text.toString()
        val emailInput = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val phoneInput = findViewById<TextView>(R.id.editTextPhone).text.toString()

        Log.d("username", usernameInput)
        Log.d("password", passwordInput)
        Log.d("email", emailInput)
        Log.d("phone", phoneInput)

        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        val user: User = User(username = usernameInput, password = passwordInput, email = emailInput, phone = phoneInput)
        myDatabase.DAO().insertUser(user)

        setContentView(R.layout.activity_login)
    }

    fun loginProceed(view: View)
    {
        setContentView(R.layout.activity_login_proceed)
    }

    fun loginSucceed(view: View)
    {
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        val usernameInput = findViewById<EditText>(R.id.editTextUsernameLogin).text.toString()
        val passwordInput = findViewById<EditText>(R.id.editTextPasswordLogin).text.toString()
        val passwordAlert = findViewById<TextView>(R.id.passwordIncorrect)

        val userFound = myDatabase.DAO().getUser(usernameInput)

        if(userFound.password != passwordInput)
        {
            Log.d("Status","User NOT found!")
            passwordAlert.visibility = View.VISIBLE
        }
        else
        {
            Log.d("Status","User found!")
            setContentView(R.layout.activity_main)
        }
    }
}