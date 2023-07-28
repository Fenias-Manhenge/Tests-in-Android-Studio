package com.example.tests.storage

import java.net.URI

data class ExternalStorage(
    val id: Long,
    val name: String,
    val width: Int,
    val height: Int,
    val contentUri: URI
)
