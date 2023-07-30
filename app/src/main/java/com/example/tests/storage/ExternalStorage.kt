package com.example.tests.storage

import android.net.Uri

data class ExternalStorage(
    val id: Long,
    val name: String,
    val width: Int,
    val height: Int,
    val contentUri: Uri
)
