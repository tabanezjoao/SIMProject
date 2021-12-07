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
    // esta variavel vai servir para guardar a informaçao princiapl do utilizador
    var userMain: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    //  Register functions
    fun setUserRegister(view: View)
    {
        setContentView(R.layout.activity_register)
    }


    fun getUserRegistration(view: View)
    {
        // vamos buscar o input introduzido pelo utilizador
        val usernameInput = findViewById<TextView>(R.id.editTextUsername).text.toString()
        val passwordInput = findViewById<TextView>(R.id.editTextPassword).text.toString()
        val emailInput = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val phoneInput = findViewById<TextView>(R.id.editTextPhone).text.toString()
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

        setContentView(R.layout.activity_login)
    }


    // login functions
    fun loginProceed(view: View)
    {
        setContentView(R.layout.activity_login_proceed)
    }

    fun loginSucceed(view: View)
    {
        var myDatabase: MyDatabase = MyDatabase.build(applicationContext)

        // vamos buscar o input do utilizador
        val usernameInput = findViewById<EditText>(R.id.editTextUsernameLogin).text.toString()
        val passwordInput = findViewById<EditText>(R.id.editTextPasswordLogin).text.toString()
        val passwordAlert = findViewById<TextView>(R.id.passwordIncorrect)

        // vamos ver se existe o utilizador introduzido
        val userFound = myDatabase.DAO().getUser(usernameInput)

        // se o username nao existir vamos avisar
        if(userFound == null)
        {
            passwordAlert.setText("Username not found!")
            passwordAlert.visibility = View.VISIBLE
            return
        }

        // se a password for incorreta vamos avisar
        if(userFound.password != passwordInput) {
            Log.d("Status", "User NOT found!")
            passwordAlert.setText("Username not found!")
            passwordAlert.visibility = View.VISIBLE
            return
        }

        Log.d("Status","User found!")

        // guardar a informaçao principal do utilizador
        userMain = userFound

        // vamos ver se o utilizador ja tem informaçao preenchida
        val infoFound = myDatabase.DAO().getUserAndInformation(usernameInput)

        if(infoFound == null)
        {
            setContentView(R.layout.activity_information)
        }
        else
        {
            setContentView(R.layout.activity_main)
        }
    }

    // main function
}