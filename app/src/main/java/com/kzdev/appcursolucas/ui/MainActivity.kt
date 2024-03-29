package com.kzdev.appcursolucas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kzdev.appcursolucas.data.Note
import com.kzdev.appcursolucas.database.AppDatabase
import com.kzdev.appcursolucas.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val i = Intent(this, ContentActivity::class.java)
            startActivity(i)
        }

        recoverNote()
    }

    private fun recoverNote() {

        lifecycleScope.launch {

            val listNotes = mutableListOf<Note>()

            withContext(Dispatchers.IO) {

                val db =
                    Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java,
                        "database-note"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                listNotes.addAll(db.noteDao().getAll())
            }

            withContext(Dispatchers.Main) {
                binding.tvNote.text = listNotes.joinToString { it.note ?: "<Sem Valor>" }
            }
        }
    }
}