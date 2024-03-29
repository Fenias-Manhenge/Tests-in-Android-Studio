package com.example.tests.room_database

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tests.ApplicationTest
import com.example.tests.R
import com.example.tests.databinding.RoomDemoBinding
import com.example.tests.databinding.RoomUpdateDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class RoomDemo : AppCompatActivity() {

    private val binding by lazy { RoomDemoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val employeeDao = (application as ApplicationTest).db.employeeDao()

        binding.btnSaveRecord.setOnClickListener {
            addRecord(employeeDao)
        }

        lifecycleScope.launch {
            employeeDao.fetchAllEmployees().collect{
                setupListDataIntoRecyclerView(it, employeeDao)
            }
        }
    }

    private fun setupListDataIntoRecyclerView(
        employeeList: List<EmployeeEntity>, employeeDao: EmployeeDao
    ){
        if(employeeList.isNotEmpty()){
            binding.rvRecords.apply {
                layoutManager = LinearLayoutManager(this@RoomDemo)
                adapter = RecordsAdapter(employeeList, this@RoomDemo,
                    { updateID ->
                        updateRecord(updateID, employeeDao)
                    },
                    { deleteID ->
                        deleteRecord(deleteID, employeeDao)
                    }
                )
            }
        }
    }

    private fun addRecord(employeeDao: EmployeeDao){
        val name = binding.etName.text.toString()
        val email = binding.etEMail.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()){
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(this@RoomDemo, "Record Saved", Toast.LENGTH_SHORT).show()
                binding.etName.text?.clear()
                binding.etEMail.text?.clear()
            }
        }else
            Toast.makeText(this@RoomDemo, "Name or email can not be blank", Toast.LENGTH_SHORT).show()
    }

    private fun updateRecord(id: Int, employeeDao: EmployeeDao){
        val binding = RoomUpdateDialogBinding.inflate(layoutInflater)

        val updateDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        updateDialog.show()

        lifecycleScope.launch {
            employeeDao.fetchEmployeeByID(id).collect {
                binding.etName.setText(it.name)
                binding.etEMail.setText(it.email)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val name: String = binding.etName.text.toString()
            val email: String = binding.etEMail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    Toast.makeText(applicationContext, "Record updated", Toast.LENGTH_SHORT).show()
                    updateDialog.dismiss()
                }
            }else
                Toast.makeText(this@RoomDemo, "Name or email can not be blank", Toast.LENGTH_SHORT).show()
        }

        binding.btnCancel.setOnClickListener {
            updateDialog.dismiss()
        }
    }

    private fun deleteRecord(id: Int, employeeDao: EmployeeDao){
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setIcon(ContextCompat.getDrawable(this, R.drawable.delete_30px))
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this?")
            .setPositiveButton("Yes"){ dialog, _ ->
                lifecycleScope.launch {
                    employeeDao.delete(EmployeeEntity(id))
                    Toast.makeText(this@RoomDemo, "Record Deleted", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}