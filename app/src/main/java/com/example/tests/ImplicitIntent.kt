package com.example.tests

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tests.databinding.ImplicitIntentBinding
import com.google.android.material.snackbar.Snackbar

class ImplicitIntent : AppCompatActivity() {

    private lateinit var binding: ImplicitIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ImplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddImage.setOnClickListener {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                rationaleDialog(
                    "XIMIX requires External Storage access",
                    "We need your External Storage permission to access the Gallery"
                )
            }else {
                externalStoragePermission.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private val openGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK && it.data != null)
            binding.ivPhoto.setImageURI(it.data?.data)
    }

    private val intentGalleryContent = Intent(Intent.ACTION_GET_CONTENT).also {
        it.type = "image/*"
    }

    private val externalStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGrated->
        if (isGrated) {
            openGallery.launch(intentGalleryContent)
        }else {
            Snackbar.make(binding.root, "Permission denied for Camera", Snackbar.LENGTH_SHORT)
                .setAction("Allow") {
                    rationaleDialog(
                        "XIMIX requires External Storage access",
                        "We need your External Storage permission to access the Gallery"
                    )
                }
                .setActionTextColor(ContextCompat.getColor(this, R.color.md_theme_dark_surfaceTint))
                .show()
        }
    }

    private fun rationaleDialog(title: String, message: String){
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setIcon(R.drawable.storage_hdd)
            setCancelable(false)

            setPositiveButton("Ok"){ dialog, _->
                dialog.dismiss()
                externalStoragePermission.launch(android.Manifest.permission.CAMERA)
            }
        }.show()
    }
}