package com.example.tests.fundamentos.recycle_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tests.R
import com.example.tests.storage.ExternalStorage
import com.example.tests.storage.Gallery

interface OnItemClickListener{
    fun onImageClicked(position: Int)
}

class ExternalStorageAdapter(var externalDataGallery: MutableList<ExternalStorage>):RecyclerView.Adapter<ExternalStorageAdapter.GalleryViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    inner class GalleryViewHolder(itemViewHolder: View): RecyclerView.ViewHolder(itemViewHolder){
        val photo: ImageView = itemViewHolder.findViewById(R.id.ivPhotoTest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view).apply {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onImageClicked(position)
                    //deleteImage(position)
                }
            }
        }
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

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    private fun onImageClicked(position: Int) {
        onItemClickListener?.onImageClicked(position)
    }

    fun deleteImage(position: Int){
        externalDataGallery.removeAt(position)
        this.notifyItemRemoved(position)
        //notifyItemRangeChanged(position, externalDataGallery.size)
    }
}