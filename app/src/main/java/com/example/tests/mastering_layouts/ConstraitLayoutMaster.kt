package com.example.tests.mastering_layouts

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.tests.R
import com.example.tests.databinding.ConstraitLayout1Binding

class ConstraintLayoutMaster: AppCompatActivity() {

    private val binding by lazy { ConstraitLayout1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnConstraint = Button(this)
        btnConstraint.text = "Continue"
        btnConstraint.id = View.generateViewId()

        val ivConstraint = ImageView(this)
        ivConstraint.setBackgroundColor(ContextCompat.getColor(this, R.color.md_theme_light_primary))
        ivConstraint.id = View.generateViewId()

        // Layout Params
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        val ivLayoutParams = ConstraintLayout.LayoutParams(700, 900)

        // Applying layout params to views
        btnConstraint.layoutParams = layoutParams
        ivConstraint.layoutParams = ivLayoutParams

        // Adding Views to Constraint Layout
        binding.constraint.addView(btnConstraint)
        binding.constraint.addView(ivConstraint)

        val myConstraint = binding.constraint

        // Create constraints for the textView
        val set = ConstraintSet()
        set.clone(myConstraint)

        // ImageView Constraint
        set.connect(
            ivConstraint.id, ConstraintSet.START,
            myConstraint.id, ConstraintSet.START
        )
        set.connect(
            ivConstraint.id, ConstraintSet.END,
            myConstraint.id, ConstraintSet.END
        )
        set.connect(
            ivConstraint.id, ConstraintSet.TOP,
            myConstraint.id, ConstraintSet.TOP
        )
        set.connect(
            ivConstraint.id, ConstraintSet.BOTTOM,
            btnConstraint.id, ConstraintSet.TOP
        )
        // Button Constraint
        set.connect(
            btnConstraint.id, ConstraintSet.START,
            myConstraint.id, ConstraintSet.START
        )
        set.connect(
            btnConstraint.id, ConstraintSet.END,
            myConstraint.id, ConstraintSet.END
        )
        set.connect(
            btnConstraint.id, ConstraintSet.BOTTOM,
            myConstraint.id, ConstraintSet.BOTTOM, 400
        )

        // Apply the constraints to the ConstraintLayout
        set.applyTo(myConstraint)

    }

}