package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User

class PasswordRecoveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)
    }

    fun passwordRecovered(view: View)
    {
        //verificar se o username existe
        //se existir verificar se a password esta igual
        //se estiver igual guardar na base dados a nova password
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)
        var userFound: User? = null

        // vamos buscar o input do utilizador
        val usernameInput = findViewById<EditText>(R.id.editTextusernameRecovery).text.toString()
        val passwordInput = findViewById<EditText>(R.id.editTextPasswordRecovery).text.toString()
        val passwordInput2 = findViewById<EditText>(R.id.editTextPasswordRecovery2).text.toString()
        val passwordAlert = findViewById<TextView>(R.id.textViewPasswordRecoveryError)

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
        if(passwordInput != passwordInput2) {
            Log.d("Status", "Password not equal!")
            passwordAlert.text = "Password inserted not equal!"
            passwordAlert.visibility = View.VISIBLE
            return
        }

        userFound.password = passwordInput

        myDatabase.DAO().updateUser(userFound)

        finish()
    }
}