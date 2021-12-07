package com.example.fitnessapp.database

import androidx.room.*


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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOffice(information: Information?)

    @Transaction
    @Query("Select * from users where username = :usernameInput")
    fun getUserAndInformation(usernameInput: String): UserAndInformation
}
