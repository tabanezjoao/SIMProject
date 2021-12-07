package com.example.fitnessapp.database
import android.graphics.Bitmap
import androidx.room.*


@Entity(tableName = "users", indices = [Index(value = ["username"],  unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId : Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone : String? = null,
)
