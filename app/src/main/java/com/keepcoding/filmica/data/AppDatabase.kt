package com.keepcoding.filmica.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Film::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
   abstract fun filmDao(): FilmDao
}