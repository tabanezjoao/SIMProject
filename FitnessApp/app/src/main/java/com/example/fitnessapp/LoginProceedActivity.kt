package com.example.fitnessapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fitnessapp.database.Information
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import java.io.Serializable

class LoginProceedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_proceed)
    }

    fun loginSucceed(view: View)
    {
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)
        var userFound: User? = null
        var infoFound: Information? = null

        // vamos buscar o input do utilizador
        val usernameInput = findViewById<EditText>(R.id.editTextUsernameLogin).text.toString()
        val passwordInput = findViewById<EditText>(R.id.editTextPasswordLogin).text.toString()
        val passwordAlert = findViewById<TextView>(R.id.passwordIncorrect)

        // vamos ver se existe o utilizador introduzido
        userFound = myDatabase.DAO().getUser(usernameInput)

        // se o username nao existir vamos avisar
        if(userFound == null)
        {
            passwordAlert.text = "Username not found!"
            passwordAlert.visibility = View.VISIBLE
            return
        }

        // se a password for incorreta vamos avisar
        if(userFound.password != passwordInput) {
            Log.d("Status", "Password not correct!")
            passwordAlert.text = "Password not correct!"
            passwordAlert.visibility = View.VISIBLE
            return
        }

        Log.d("Status","User found!")

        // vamos ver se o utilizador ja tem informaçao preenchida
        if(userFound.userId != null)
        {
            infoFound = myDatabase.DAO().getInformation(userFound.userId!!)
        }

        // nao existe entitie information
        if(infoFound == null)
        {
            val intent = Intent(this, InformationActivity::class.java)
            intent.putExtra("user", userFound as Serializable)
            startActivity(intent)
            finish()
        }
        else
        {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", userFound as Serializable)
            startActivity(intent)
            finish()
        }
    }
}