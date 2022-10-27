package com.es.k_retrofit_image_upload

data class ImageUploadResponse(
    val destination: String,
    val encoding: String,
    val fieldname: String,
    val filename: String,
    val mimetype: String,
    val originalname: String,
    val path: String,
    val size: Int
)