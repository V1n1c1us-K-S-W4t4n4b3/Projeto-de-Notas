package com.kzdev.appcursolucas.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kzdev.appcursolucas.data.Note

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