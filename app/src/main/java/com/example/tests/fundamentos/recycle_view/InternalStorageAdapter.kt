package com.example.tests.fundamentos.recycle_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R
import com.example.tests.storage.InternalStoragePhoto

class InternalStorageAdapter(var dataGallery: List<InternalStoragePhoto>): RecyclerView.Adapter<InternalStorageAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val photo: ImageView = itemView.findViewById(R.id.ivPhotoTest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.itemView.apply {
            val data = dataGallery[position].bitmap
            holder.photo.setImageBitmap(data)
        }
    }

    override fun getItemCount(): Int {
        return dataGallery.size
    }
}