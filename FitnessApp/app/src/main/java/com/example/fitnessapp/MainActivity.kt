package com.example.fitnessapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.fitnessapp.database.Information
import com.example.fitnessapp.database.MyDatabase
import com.example.fitnessapp.database.User
import com.example.fitnessapp.database.Water
import com.example.fitnessapp.fragments.HomeFragment
import com.example.fitnessapp.fragments.ProfileFragment
import com.example.fitnessapp.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.*
import kotlin.concurrent.timer
import android.os.Build.VERSION.SDK_INT
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    // esta variavel vai servir para guardar a informaçao princiapl do utilizador
    var userMain: User? = null

    companion object {
        public var permissionsGranted = false
    }

    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val settingsFragment = SettingsFragment()
    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Titulo Notification")
            .setContentText("This is the content text")
            .setSmallIcon(R.drawable.ic_sunny)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        val btnShowNotification = findViewById<Button>(R.id.btnShowNotification)
        btnShowNotification.setOnClickListener{
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        val userFound = intent.extras?.get("user") as User

        userMain = userFound

        replaceWithHomeFragment(homeFragment)

        var navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_person -> replaceFragment(profileFragment)
                R.id.ic_home -> replaceWithHomeFragment(homeFragment)
                R.id.ic_settings -> replaceFragment(settingsFragment)
            }
            return@setOnItemSelectedListener true
        }

        obtainPermissions()

        val sensorModel = SensorModel()
        
        Toast.makeText(this, sensorModel.statusMessage, Toast.LENGTH_SHORT).show()
    }

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
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

    private fun obtainPermissions() {
        var granted = true
        val permissions = arrayOf(  //ARRAY WITH ALL THE NECESSARY PERMISSIONS
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION,
            //....
        )
        for(i in  permissions.indices){
            granted = granted && checkPermissionItem(permissions[i])
        }
        if (!granted) {
            ActivityCompat.requestPermissions(this, permissions,0)
            return
        } else {
            permissionsGranted = true
            SensorModel.initializeSensorManager(applicationContext)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permission_granted = true
        for (i in 0 until permissions.size) {
            val grantResult = grantResults[i]
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                permission_granted = false
            }
        }
        if( permission_granted) {
            permissionsGranted = true
            SensorModel.initializeSensorManager(applicationContext)
        }
    }

    private fun checkPermissionItem(permission: String): Boolean {
        if (ActivityCompat.checkSelfPermission(this, permission ) != PackageManager.PERMISSION_GRANTED)
            return false
        return true
    }

    fun addWater(view: View)
    {
        val intent = Intent(this, WaterActivity::class.java)
        intent.putExtra("user", userMain as Serializable)
        startActivity(intent)
    }

    fun addWeight(view: View)
    {
        val intent = Intent(this, WeightActivity::class.java)
        intent.putExtra("user", userMain as Serializable)
        startActivity(intent)
    }

    fun addCalories(view: View)
    {
        val intent = Intent(this, CaloriesActivity::class.java)
        intent.putExtra("user", userMain as Serializable)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

    }
}