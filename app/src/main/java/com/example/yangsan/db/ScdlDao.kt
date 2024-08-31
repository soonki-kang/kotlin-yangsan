package com.example.yangsan.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ScdlDao {

    @Upsert
    suspend fun upsertScdl(scdl: Scdl)

    @Delete
    suspend fun deleteScdl(scdl: Scdl)

    @Query("SELECT * FROM scdl ORDER BY scdl_date ASC")
    fun getScdlsOrderedByDate(): Flow<List<Scdl>>

}