package com.example.tests.fundamentos.recycle_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R

class TodoAdapter(private var datas: List<TodoData>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    
    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvFood).text = datas[position].foodName
            findViewById<CheckBox>(R.id.cbOpinion).isChecked = datas[position].preference
            /*
            cbOpinion.isChecked = datas[position].preference
            if (cbOpinion.isChecked){
                cbOpinion.text = "Amazing"
            }else
                cbOpinion.text = "Horible"
             */
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}