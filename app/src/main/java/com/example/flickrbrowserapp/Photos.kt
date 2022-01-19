package com.example.flickrbrowserapp

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<PhotosDetails>,
    val total: Int
)