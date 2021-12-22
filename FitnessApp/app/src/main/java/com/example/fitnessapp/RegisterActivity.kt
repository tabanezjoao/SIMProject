package com.example.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun getUserRegistration(view: View)
    {
        // vamos buscar o input introduzido pelo utilizador
        val usernameInput = findViewById<EditText>(R.id.editTextUsername).text.toString()
        val passwordInput = findViewById<EditText>(R.id.editTextPassword).text.toString()
        val emailInput = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val phoneInput = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val usernameAlert = findViewById<TextView>(R.id.textViewUsernameIncorrect)

        Log.d("username", usernameInput)
        Log.d("password", passwordInput)
        Log.d("email", emailInput)
        Log.d("phone", phoneInput)

        // init a variavel para ir buscar a base de dados
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        // procuramos por um utilizador com o username introduzido
        val userFound = myDatabase.DAO().getUser(usernameInput)

        // se ja existe registado algum user com o username introduzido
        if(userFound != null)
        {
            usernameAlert.visibility = View.VISIBLE
            return
        }

        // caso contrario introduzimos o novo utilizador na base de dados
        val user: User = User(username = usernameInput, password = passwordInput, email = emailInput, phone = phoneInput)
        myDatabase.DAO().insertUser(user)

        // close activity
        finish()
    }
}