package com.example.tests.components

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tests.R

class ToggleButtonAndViewStub: AppCompatActivity() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        val viewStub: ViewStub = findViewById(R.id.viewStub)
        val toggleGroup: MaterialButtonToggleGroup = findViewById(R.id.toggleGroup)

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                R.id.buttonOption1 -> inflateViewStub(R.layout.layout_option1)
                R.id.buttonOption2 -> inflateViewStub(R.layout.layout_option2)
                // Adicione mais casos conforme necessário
            }
        }
    }

    private fun inflateViewStub(layoutResId: Int) {
        val viewStub: ViewStub = findViewById(R.id.viewStub)
        val inflatedView = viewStub.inflate()
        val dynamicContent: ViewGroup = inflatedView.findViewById(R.id.dynamicContent)
        dynamicContent.removeAllViews() // Limpar qualquer conteúdo anterior

        // Inflar o layout desejado no ViewStub
        layoutInflater.inflate(layoutResId, dynamicContent, true)
    }

     */
}