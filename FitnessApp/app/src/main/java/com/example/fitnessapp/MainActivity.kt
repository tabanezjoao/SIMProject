package com.example.fitnessapp

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
import androidx.fragment.app.Fragment
import com.example.fitnessapp.database.Information
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.fragments.HomeFragment
import com.example.fitnessapp.fragments.ProfileFragment
import com.example.fitnessapp.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    // esta variavel vai servir para guardar a informaçao princiapl do utilizador
    var userMain: User? = null

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val settingsFragment = SettingsFragment()

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
            passwordAlert.setText("Username not found!")
            passwordAlert.visibility = View.VISIBLE
            return
        }

        // se a password for incorreta vamos avisar
        if(userFound.password != passwordInput) {
            Log.d("Status", "Password not correct!")
            passwordAlert.setText("Password not correct!")
            passwordAlert.visibility = View.VISIBLE
            return
        }

        Log.d("Status","User found!")

        // guardar a informaçao principal do utilizador
        userMain = userFound

        // vamos ver se o utilizador ja tem informaçao preenchida
        if(userFound.userId != null)
        {
            infoFound = myDatabase.DAO().getInformation(userFound.userId!!)
        }

        // nao existe entitie information
        if(infoFound == null)
        {
            setContentView(R.layout.activity_information)

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
        else
        {
            setContentView(R.layout.activity_main)
            replaceFragment(homeFragment)
            var helloUser = findViewById<TextView>(R.id.textViewGreet)
            //helloUser.setText("Hi " + userFound.username)

            var navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            navBar.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.ic_person -> replaceFragment(profileFragment)
                    R.id.ic_home -> replaceFragment(homeFragment)
                    R.id.ic_settings -> replaceFragment(settingsFragment)
                }
                return@setOnItemSelectedListener true
            }


        }
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

        val info: Information = Information(age= age.text.toString().toLong(), height = seekBarHeight.progress.toString().toLong(), weight = seekBarWeight.progress.toString().toLong(), gender = gender, userId = userMain?.userId)

        Log.d("Utilizador", "Exameplo")
        Log.d("Age", info.age.toString())
        Log.d("Height", info.height.toString())
        Log.d("Weight", info.weight.toString())
        info.gender?.let { Log.d("Gender", it) }

        myDatabase.DAO().insertInformation(info)

        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        var helloUser = findViewById<TextView>(R.id.textViewGreet)
        //helloUser.setText("Hi " + userMain?.username)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_person -> replaceFragment(profileFragment)
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_settings -> replaceFragment(settingsFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment : Fragment)
    {
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}