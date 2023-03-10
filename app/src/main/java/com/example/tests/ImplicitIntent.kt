package com.example.tests

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tests.databinding.ImplicitIntentBinding

class ImplicitIntent : AppCompatActivity() {

    private lateinit var binding: ImplicitIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ImplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK && it.data != null)
                binding.ivPhoto.setImageURI(it.data?.data)
        }

        binding.btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).also { intent ->
                intent.type = "image/*"
            }
            openGallery.launch(intent)
        }
    }
}