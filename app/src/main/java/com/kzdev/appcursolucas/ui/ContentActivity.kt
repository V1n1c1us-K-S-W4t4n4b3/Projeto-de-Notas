package com.kzdev.appcursolucas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kzdev.appcursolucas.data.Note
import com.kzdev.appcursolucas.database.AppDatabase
import com.kzdev.appcursolucas.databinding.ActivityContentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityContentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btConcluded.setOnClickListener {
            val note = Note(note = binding.tietEvNote.text.toString())
            saveNote(note)
        }
    }

    private fun saveNote(note: Note) {

        lifecycleScope.launch {

            // escolhemos o thread IO impute output entrada e saida de info para rodar em segundo plano a ação
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "database-note"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

              db.noteDao().insertAll(note)
            }
        }
    }
}