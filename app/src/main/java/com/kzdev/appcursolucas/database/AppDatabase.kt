package com.kzdev.appcursolucas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kzdev.appcursolucas.data.Note
import com.kzdev.appcursolucas.dao.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}