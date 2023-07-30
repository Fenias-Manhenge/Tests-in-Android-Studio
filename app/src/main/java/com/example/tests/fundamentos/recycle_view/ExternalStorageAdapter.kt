package com.example.tests.fundamentos.recycle_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R
import com.example.tests.storage.ExternalStorage

class ExternalStorageAdapter(var externalDataGallery: List<ExternalStorage>):RecyclerView.Adapter<ExternalStorageAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemViewHolder: View): RecyclerView.ViewHolder(itemViewHolder){
        val photo: ImageView = itemViewHolder.findViewById(R.id.ivPhotoTest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return externalDataGallery.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.itemView.apply {
            val data = externalDataGallery[position].contentUri
            holder.photo.setImageURI(data)
        }
    }
}