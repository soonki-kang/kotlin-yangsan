package com.example.yangsan.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Scdl::class, User::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class RsrvDatabase:RoomDatabase() {
    abstract val dao: ScdlDao
    abstract val userDao: UserDao
}