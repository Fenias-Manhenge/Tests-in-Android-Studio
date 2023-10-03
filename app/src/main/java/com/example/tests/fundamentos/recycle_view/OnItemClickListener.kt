package com.example.tests.fundamentos.recycle_view

interface OnItemClickListener {
    fun onImageClicked(position: Int)

    fun sharePhoto(position: Int)

    fun showUri(position: Int)
}