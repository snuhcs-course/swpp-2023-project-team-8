package com.example.frontend.model

import androidx.compose.ui.graphics.Color

data class MissionModel(
    val title: String,
    val description: String,
    val completed: Boolean,
    val showMore: String,

    ){    val color: Color
        get() = if (completed) Color.Gray else Color(0xFFF3EDF7)
}