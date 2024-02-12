package com.example.tests.room_database

import androidx.room.*

@Entity(tableName = "Employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int = 0,
    @ColumnInfo(name = "Name")
    val name: String = "",
    @ColumnInfo(name = "email-ID")
    val email: String = ""
)
