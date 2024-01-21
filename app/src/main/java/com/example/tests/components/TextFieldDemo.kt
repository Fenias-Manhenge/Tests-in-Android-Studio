package com.example.tests.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tests.R
import com.example.tests.databinding.TextFieldBinding
import com.google.android.material.button.MaterialButtonToggleGroup

class TextFieldDemo : AppCompatActivity() {

    val binding by lazy { TextFieldBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mConstraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toggleGroup: MaterialButtonToggleGroup = binding.toggleMetrics

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.btnMetricUnit.id -> adjustParams(R.layout.implicit_intent)

                    binding.btnUSUnit.id -> adjustParams(R.layout.slider)
                }
            }
        }
    }

    private fun inflateLayout(layoutResID: Int): View{
        return LayoutInflater.from(this).inflate(layoutResID, null)
    }

    private fun adjustParams(layoutResID: Int){
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )

        binding.mConstraintLayout1.removeAllViews()
        val layoutInflated = inflateLayout(layoutResID)
        layoutInflated.layoutParams = layoutParams
        binding.mConstraintLayout1.addView(layoutInflated)
    }
}