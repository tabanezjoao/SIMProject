package com.example.fitnessapp.database

import androidx.room.*
import java.util.*


@Dao
interface MyDao  {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(vararg users: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("select * from users where username = :usernameInput")
    fun getUser(usernameInput: String):User

    @Update
    fun updateUser(user: User?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInformation(information: Information?)

    @Query("select * from information where userId = :userId")
    fun getInformation(userId: Long):Information

    @Transaction
    @Query("Select * from users where username = :usernameInput")
    fun getUserAndInformation(usernameInput: String): UserAndInformation

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeights(vararg weights: Weight)

    @Query("select * from weight where date = :dateInput")
    fun getWeight(dateInput: Date):Weight

    @Query("select * from weight where userId = :userInput")
    fun getWeightWithUserId(userInput: Long):List<Weight>

    @Query("select * from weight")
    fun getAllWeights(): List<Weight>

    @Update
    fun updateWeight(weight: Weight)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWaters(vararg waters: Water)

    @Query("select * from Water where date = :dateInput")
    fun getWater(dateInput: Date):Water

    @Query("select * from Water where userId = :userInput")
    fun getWatersWithUserId(userInput: Long): List<Water>

    @Query("select * from water")
    fun getAllWaters(): List<Water>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCalories(vararg calories: Calories)

    @Query("select * from calories where date = :dateInput")
    fun getCalories(dateInput: Date):Calories

    @Query("select * from calories where userId = :userInput")
    fun getCaloriesWithUserId(userInput: Long):List<Calories>

    @Query("select * from calories")
    fun getAllCalories(): List<Calories>
}
