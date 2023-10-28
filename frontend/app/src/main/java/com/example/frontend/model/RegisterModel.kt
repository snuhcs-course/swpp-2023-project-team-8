package com.example.frontend.model

data class RegisterModel(
    val email: String,
    val email_verification_code: String,
    val name: String,
    val password: String
)