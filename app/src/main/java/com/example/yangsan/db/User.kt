package com.example.yangsan.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(

    val user_id: String,
    val user_password: String,

    @PrimaryKey()
    val id: Int = 0
)

