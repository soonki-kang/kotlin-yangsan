package com.example.yangsan.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yangsan.db.User

data class UserState(

    val users: List<User> = emptyList(),
    val user_id: MutableState<String> = mutableStateOf(""),
    val user_password: MutableState<String> = mutableStateOf(""),
)
