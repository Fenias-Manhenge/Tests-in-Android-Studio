package com.example.tests.components

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.example.tests.R
import com.example.tests.databinding.ActivityDialogsBinding
import com.example.tests.databinding.CustomDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class Dialogs : AppCompatActivity() {

    private lateinit var binding: ActivityDialogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSnackBar.setOnClickListener {
            Snackbar.make(binding.rootNode, "SnackBar", Snackbar.LENGTH_INDEFINITE)
                .setAction("Yes"){
                    val customSnackbar = Snackbar.make(binding.rootNode, "", Snackbar.LENGTH_INDEFINITE)
                    val layout = customSnackbar.view as SnackbarLayout
                    val bind: CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater)
                    layout.setPadding(0)
                    layout.addView(bind.root)
                    customSnackbar.show()
                }
                .show()
        }

        binding.btnAlertDialog.setOnClickListener {
            alertDialog()
        }

        binding.btnProgressBarDialog.setOnClickListener {
            customDialog()
        }
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Alert Dialog")
            setMessage("Learning to create Alert Dialog")
            setIcon(R.drawable.book_and_pencil_100px)
            setCancelable(false)

            setPositiveButton("Yes"){ dialog, _ ->
                Snackbar.make(binding.btnAlertDialog, "Yes clicked", Snackbar.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }.show()
    }

    private fun customDialog(){
        Dialog(this).apply {
            setContentView(R.layout.custom_progress_bar)
        }.show()
    }
}