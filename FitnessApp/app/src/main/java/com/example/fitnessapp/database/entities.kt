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
    var userId: Long? = null
)

@Entity
data class Weight(
    @PrimaryKey(autoGenerate = true)
    var weightId: Long? = null,
    var weight: Long? = null,
    var date: Date? = null,
    var informationId: Long? = null
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

// 1 -- N relationship: 1 information, N weights
data class InformationWithWeight(
    @Embedded val information: Information,
    @Relation(
        parentColumn = "informationId",
        entityColumn = "informationId"
    )
    val weights: List<Weight>
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

