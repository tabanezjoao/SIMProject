package com.example.fitnessapp.database
import android.widget.EditText
import androidx.room.*
import java.io.Serializable
import java.util.*


@Entity(tableName = "users", indices = [Index(value = ["username"],  unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId : Long? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone : String? = null,
) : Serializable

@Entity
data class Information(
    @PrimaryKey(autoGenerate = true)
    var informationId: Long? = null,
    var age: Long? = null,
    var height: Long? = null,
    var gender: String? = null,
    var caloriesIntake: Long? = null,
    var waterIntake: Long? = null,
    var userId: Long? = null
)

@Entity
data class Weight(
    @PrimaryKey(autoGenerate = true)
    var weightId: Long? = null,
    var weight: Long? = null,
    var date: Date? = null,
    var userId: Long? = null
)

@Entity
data class Water(
    @PrimaryKey(autoGenerate = true)
    var waterId: Long? = null,
    var water: Long? = null,
    var date: Date? = null,
    var userId: Long? = null
)

@Entity
data class Calories(
    @PrimaryKey(autoGenerate = true)
    var caloriesId: Long? = null,
    var calories: Long? = null,
    var date: Date? = null,
    var userId: Long? = null
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

// 1 -- N relationship: 1 User, N weights
data class InformationWithWeight(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val weights: List<Weight>
)

// 1 -- N relationship: 1 user, N Water
data class userWithWater(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val waters: List<Water>
)

// 1 -- N relationship: 1 user, N Calories
data class userWithCalories(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val calories: List<Calories>
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

