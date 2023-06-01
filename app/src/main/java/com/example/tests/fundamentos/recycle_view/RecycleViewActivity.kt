package com.example.tests.fundamentos.recycle_view

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tests.databinding.RecycleViewBinding

class RecycleViewActivity : AppCompatActivity() {
    private lateinit var binding: RecycleViewBinding

    lateinit var todo: TodoData
    inner class RecycleViewMargin : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = 20
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = RecycleViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //DynamicColors.applyToActivitiesIfAvailable(this)
        val dataList = mutableListOf(
            TodoData("Pizza", true),
            TodoData("Sushi", false),
            TodoData("Croissant", true),
            TodoData("Burg", true),
            TodoData("Cake", true),
            TodoData("Cacana", false)
        )

        val adapter = TodoAdapter(dataList)
        binding.rvFood.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@RecycleViewActivity)
            addItemDecoration(RecycleViewMargin())
        }

        /*
        binding.btnFood.setOnClickListener {
            val etText = binding.etFood.text.toString()
            dataList.add(TodoData(etText, false))
            adapter.notifyItemInserted(dataList.size - 1)
        }*/

        binding.btnFood.setOnClickListener {
            val etText = binding.etFood.text.toString()
            todo = TodoData(etText, false)
        }

        binding.srlRecycle.setOnRefreshListener {
            dataList.add(todo)
            adapter.notifyItemInserted(dataList.size - 1)
            binding.srlRecycle.isRefreshing = false
        }
    }
}