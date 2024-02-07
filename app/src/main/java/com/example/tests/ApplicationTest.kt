package com.example.tests

import android.app.Application
import com.example.tests.room_database.EmployeeDataBase
import com.google.android.material.color.DynamicColors

class ApplicationTest: Application() {

    val db by lazy { EmployeeDataBase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}