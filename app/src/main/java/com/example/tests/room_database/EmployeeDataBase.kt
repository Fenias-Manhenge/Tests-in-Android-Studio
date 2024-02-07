package com.example.tests.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDataBase: RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object{
        @Volatile
        private var INSTANCE: EmployeeDataBase? = null

        fun getInstance(context: Context): EmployeeDataBase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDataBase::class.java,
                        "Employee"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}