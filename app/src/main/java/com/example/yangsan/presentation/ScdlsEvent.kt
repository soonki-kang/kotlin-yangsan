package com.example.yangsan.presentation

import com.example.yangsan.db.Scdl
import java.time.LocalDateTime
import java.util.Date

sealed interface ScdlsEvent {

    object SortScdls: ScdlsEvent

    data class DeleteScdl(val scdl: Scdl) : ScdlsEvent

    data class SaveScdl(
        val scdl_type: Int,
        val scdl_date: Long,
        val scdl_time: Int
    ): ScdlsEvent

}