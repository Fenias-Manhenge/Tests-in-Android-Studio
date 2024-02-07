package com.example.tests.room_database

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.ApplicationTest
import com.example.tests.R
import com.example.tests.databinding.RoomDemoBinding
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

        binding.rvRecords.layoutManager = LinearLayoutManager(this)

        //binding.rvRecords.adapter = RecordsAdapter(employeeDao.fetchAllEmployees())
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
}