package com.example.tests.fundamentos.recycle_view

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R
import com.example.tests.storage.InternalStoragePhoto

class InternalStorageAdapter(
    private var dataGallery: MutableList<InternalStoragePhoto>,
    private var listener:OnItemClickListener, val c: Context
): RecyclerView.Adapter<InternalStorageAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindWithViews() {
            itemView.findViewById<ImageView?>(R.id.ivPhotoTest).apply {
                setImageBitmap(dataGallery[adapterPosition].bitmap)
                setOnLongClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        popUp(it)
                    }

                    return@setOnLongClickListener true
                }
            }
        }

        private fun popUp(v: View){
            val popUpM = PopupMenu(c, v)
            popUpM.inflate(R.menu.context_menu)
            popUpM.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.option_1 ->{
                        listener.sharePhoto(adapterPosition)
                        //Toast.makeText(c, "Shared", Toast.LENGTH_LONG).show()
                        true
                    }
                    R.id.option_2->{
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            listener.onImageClicked(adapterPosition)
                            deletePhoto(adapterPosition)
                        }
                        true
                    }
                    else -> {true}
                }
            }
            popUpM.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.itemView.apply {
            holder.bindWithViews()
        }
    }

    fun deletePhoto(position: Int){
        dataGallery.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return dataGallery.size
    }
}