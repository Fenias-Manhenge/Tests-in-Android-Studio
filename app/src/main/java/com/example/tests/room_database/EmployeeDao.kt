package com.example.tests.room_database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Query("SELECT* FROM Employee")
    fun fetchAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT* FROM Employee WHERE ID=:id")
    fun fetchEmployeeByID(id: Int): Flow<EmployeeEntity>
}