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
        setContentView(R.layout.activity_main)

        replaceWithHomeFragment(homeFragment)

        val userFound = intent.extras?.get("user") as User

        userMain = userFound

        var navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_person -> replaceFragment(profileFragment)
                R.id.ic_home -> replaceWithHomeFragment(homeFragment)
                R.id.ic_settings -> replaceFragment(settingsFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceWithHomeFragment(fragment : HomeFragment)
    {
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            userMain?.let { fragment.setUser(it) }
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
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