package com.example.frontend.model

data class MissionModel(
    val tittle: String,
    val description: String,
    val completed: Boolean,

){    val color: Color
        get() = if (completed) Color.Gray else Color(0xFFF3EDF7)
}