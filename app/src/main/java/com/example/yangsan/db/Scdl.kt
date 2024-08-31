package com.example.yangsan.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
//import java.time.Instant

@Entity
data class Scdl(

    val scdl_type: Int,
    val scdl_date: Long,
    val scdl_time: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
