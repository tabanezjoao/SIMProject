package com.example.fitnessapp.database
import android.widget.EditText
import androidx.room.*


@Entity(tableName = "users", indices = [Index(value = ["username"],  unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId : Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone : String? = null,
)

@Entity
data class Information(
    @PrimaryKey(autoGenerate = true)
    var informationId: Long? = null,
    var age: Long?,
    var height: Long?,
    var weight: Long?,
    var gender: String?,
    var userId: Long?
)

// 1 -- 1 relationship: 1 user, 1 information
data class UserAndInformation(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val information: Information
)