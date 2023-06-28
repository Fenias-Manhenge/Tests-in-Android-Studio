package com.example.tests.fundamentos.recycle_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tests.R
import com.example.tests.storage.InternalStoragePhoto
import java.net.InterfaceAddress

class GalleryAdapter(var dataGallery: List<InternalStoragePhoto>): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.itemView.apply {
            val photo = dataGallery[position].bitmap
            val imageView= findViewById<ImageView>(R.id.ivPhotoTest)
            imageView.setImageBitmap(photo)
        }

//        holder.itemView.apply {
//            val ivPhoto = findViewById<ImageView>(R.id.ivPhoto)
//            val image = dataGallery[position].fileName
//            Glide.with(this.context).load(image).into(ivPhoto)
//        }
    }

    override fun getItemCount(): Int {
        return dataGallery.size
    }
}