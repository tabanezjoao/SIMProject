package com.example.fitnessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [(User::class),(Information::class)],exportSchema = false, version = 4)
//@TypeConverters(Converters::class)
public abstract class MyDatabase : RoomDatabase() {
    abstract fun DAO(): MyDao

    companion object {
        var database: MyDatabase? = null
        public fun build(context: Context) : MyDatabase{
            if(database==null) {
                database = Room.databaseBuilder(context, MyDatabase::class.java, "mydb")
                    // fallbackToDestructiveMigration
                    // should be used very carefully in production mode
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }
}
