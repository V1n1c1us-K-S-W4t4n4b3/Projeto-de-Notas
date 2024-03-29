package com.kzdev.appcursolucas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kzdev.appcursolucas.dao.NoteDao
import com.kzdev.appcursolucas.data.Note

@Database(entities = [Note::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}