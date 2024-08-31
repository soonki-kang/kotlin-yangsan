package com.example.yangsan.presentation

sealed interface UsersEvent {
    data class SaveUser(
        val user_id: String,
        val user_password: String,
    ): UsersEvent
}