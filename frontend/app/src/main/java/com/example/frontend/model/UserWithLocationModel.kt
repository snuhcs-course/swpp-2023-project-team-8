package com.example.frontend.model

data class UserWithLocationModel(
    val id: Long,
    val name: String,
    val email: String,
    val latitude: Double,
    val longitude: Double
)