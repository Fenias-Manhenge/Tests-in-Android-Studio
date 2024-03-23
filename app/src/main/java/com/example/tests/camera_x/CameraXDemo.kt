package com.example.tests.camera_x

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tests.R
import com.example.tests.databinding.CameraXdemoBinding

class CameraXDemo : AppCompatActivity() {

    private val binding by lazy { CameraXdemoBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILE_FORMAT = "yyyy-MM-dd-HH-mm-ss"

        // Applying the required permissions considering the android level
        private val RequiredPermissions =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    Manifest.permission.READ_MEDIA_AUDIO
                    Manifest.permission.READ_MEDIA_IMAGES
                    Manifest.permission.READ_MEDIA_VIDEO
                }
            }.toTypedArray()

        fun hasPermissions(context: Context) = RequiredPermissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private val permissionsGrantedOrDenied =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            var permissionGranted = true

            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value

                if (permissionName in RequiredPermissions && isGranted == false)
                    permissionGranted = false
            }

            if (!permissionGranted)
                Toast.makeText(this, "Permission request Denied", Toast.LENGTH_LONG).show()
            else
                TODO("startCamera")
        }
}