package com.example.frontend.model

import androidx.compose.ui.graphics.Color

data class MissionModel(
    val name: String,
    val description: String,
    val userCompleted: Boolean?,
    val userProgress: Int?

) {
    val color: Color
        get() = Color(0xFFF3EDF7)
}