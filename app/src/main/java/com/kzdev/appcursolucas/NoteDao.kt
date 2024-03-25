package com.kzdev.appcursolucas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE uid IN (:notesIds)")
    fun loadAllByIds(notesIds: IntArray): List<Note>

    @Query("SELECT * FROM note WHERE note LIKE:note LIMIT 1")
    fun findByName(note: String): Note

    @Insert
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)
}