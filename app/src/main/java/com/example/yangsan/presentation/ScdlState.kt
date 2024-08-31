package com.example.yangsan.presentation

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.yangsan.db.Scdl
import java.time.LocalDateTime
import java.time.ZoneId

data class ScdlState(

    val scdls: List<Scdl> = emptyList(),
    val scdl_type: MutableIntState = mutableIntStateOf(0),
    val scdl_date: MutableLongState = mutableLongStateOf(
        LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()?.toEpochMilli() ?: 0
    ),
//    val scdl_date: MutableState<Long> = mutableLongStateOf(0),
    val scdl_time: MutableIntState = mutableIntStateOf(0)
)
