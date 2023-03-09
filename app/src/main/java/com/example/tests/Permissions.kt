package com.example.tests

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.tests.databinding.ActivityPermissionsBinding

class Permissions : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            requestPermissions()
        }
    }

    private fun hasCameraAccessPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun hasInternetAccessPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED

    private fun hasCoarseLocationAccessPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasFineLocationAccessPermission() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(){
        val permissionsToRequest = mutableListOf<String>()

        if (!hasCameraAccessPermission())
            permissionsToRequest.add(android.Manifest.permission.CAMERA)

        if (!hasInternetAccessPermission())
            permissionsToRequest.add(android.Manifest.permission.INTERNET)

        if (!hasCoarseLocationAccessPermission() && !hasFineLocationAccessPermission()){
            permissionsToRequest.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            permissionsToRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissionsToRequest.isNotEmpty())
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "${permissions[i]}: Granted", Toast.LENGTH_LONG).show()

                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(this, "${permissions[i]}: Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}