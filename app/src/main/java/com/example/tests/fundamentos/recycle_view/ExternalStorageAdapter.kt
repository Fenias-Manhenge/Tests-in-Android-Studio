package com.example.tests.fundamentos.recycle_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R
import com.example.tests.storage.ExternalStorage

class ExternalStorageAdapter(private var externalDataGallery: MutableList<ExternalStorage>, val c: Context, private val listener: OnItemClickListener):RecyclerView.Adapter<ExternalStorageAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemViewHolder: View): RecyclerView.ViewHolder(itemViewHolder){
        fun bindWithViews(){
            itemView.findViewById<ImageView>(R.id.ivPhotoTest).apply {
                val data = externalDataGallery[adapterPosition].contentUri
                setImageURI(data)
                setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        popUpMenu(it)
                }
            }
        }

        private fun popUpMenu(v: View){
            val popUp = PopupMenu(c, v)
            popUp.inflate(R.menu.context_menu)
            popUp.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.option_1 -> {
                        listener.sharePhoto(adapterPosition)
                        true
                    }
                    R.id.option_2 -> {
                        Toast.makeText(c,"Delete", Toast.LENGTH_LONG).show()
                        true
                    }
                    R.id.option_3 ->{
                        listener.showUri(adapterPosition)
                        true
                    }
                    else -> {true}
                }
            }
            popUp.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return externalDataGallery.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindWithViews()
    }

    fun deleteImage(position: Int){
        externalDataGallery.removeAt(position)
        this.notifyItemRemoved(position)
    }
}