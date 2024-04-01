package com.kzdev.appcursolucas.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kzdev.appcursolucas.data.Note
import com.kzdev.appcursolucas.database.AppDatabase
import com.kzdev.appcursolucas.databinding.ActivityInfoItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoItemActivity : AppCompatActivity() {

    private val binding by lazy { ActivityInfoItemBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        receiveData()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btDelete.setOnClickListener { showConfirmationDialog() }
    }

    private fun receiveData() {
        binding.tvUid.text = intent.getIntExtra("uid", 0).toString()

        binding.tvNote.text = intent.getStringExtra("note")
    }

    private fun deleteNote() {

        val note = Note(intent.getIntExtra("uid", 0), intent.getStringExtra("note"))

        lifecycleScope.launch {

            // escolhemos o thread IO impute output entrada e saida de info para rodar em segundo plano a ação
            withContext(Dispatchers.IO) {

                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-note"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                db.noteDao().delete(note)

                finish()
            }
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Confirmação")

        builder.setMessage("Tem certeza que deseja deletar esta anotação?")

        builder.setPositiveButton("Sim") { _, _ ->
            deleteNote()
        }

        builder.setNegativeButton("Não") { _, _ ->
        }

        builder.create().show()
    }
}