package com.example.tests.coroutines

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tests.databinding.CoroutinesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Coroutines: AppCompatActivity(){

    private lateinit var binding: CoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCoroutines.setOnClickListener {
            lifecycleScope.launch {
                coroutineTest()
            }
        }
    }

    private suspend fun coroutineTest(){
        withContext(Dispatchers.IO){
            for (i in 1..300000){
                Log.e("delay", "$i")
            }
            Toast.makeText(this@Coroutines,   "Done", Toast.LENGTH_SHORT).show()
        }
    }

    private fun test(){
        for (i in 1..500000){
            Log.e("Delay", "$i")
        }
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
    }
}