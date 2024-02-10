package com.example.tests.room_database

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R

class RecordsAdapter(private val data: List<EmployeeEntity>, private val context: Context,
                     private val updateListener:(id: Int) -> Unit, private val deleteListener: (id:Int) -> Unit
    ): RecyclerView.Adapter<RecordsAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val name: TextView = item.findViewById(R.id.tvName)
        val email: TextView = item.findViewById(R.id.tvEmail)
        val edit: ImageButton = item.findViewById(R.id.ibEdit)
        val delete: ImageButton = item.findViewById(R.id.ibDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_record_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.email.text = data[position].email
        holder.edit.setOnClickListener {
            updateListener.invoke(data[position].id)
        }
        holder.delete.setOnClickListener {
            deleteListener.invoke(data[position].id)
        }

        if (position % 2 == 0){
            val bgColorRes = if (isDarkMode()) {
                Color.parseColor("#40484C")
            }
            else {
                Color.parseColor("#BFC8CC")
            }

            holder.itemView.setBackgroundColor(bgColorRes)
            holder.edit.backgroundTintList = ColorStateList.valueOf(bgColorRes)
            holder.delete.backgroundTintList = ColorStateList.valueOf(bgColorRes)
        }
    }

    private fun isDarkMode(): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

}