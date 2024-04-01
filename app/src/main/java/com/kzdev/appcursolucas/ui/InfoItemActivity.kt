package com.kzdev.appcursolucas.ui

import android.os.Bundle
import android.widget.EditText
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

        binding.btUpdate.setOnClickListener { showUpdateDialog() }
    }

    private fun receiveData() {
        binding.tvUid.text = intent.getIntExtra("uid", 0).toString()

        binding.tvNote.text = intent.getStringExtra("note")
    }

    private fun showUpdateDialog() {
        val currentNote = binding.tvNote.text.toString()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atualizar Nota")
        val input = EditText(this)
        input.setText(currentNote) // Preenche o texto atual na caixa de texto
        builder.setView(input)

        builder.setPositiveButton("Atualizar") { _, _ ->
            val updatedNote = input.text.toString()
            updateNoteInDatabase(updatedNote)
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateNoteInDatabase(updatedNote: String) {
        val uid = intent.getIntExtra("uid", 0)
        val note = Note(uid, updatedNote)

        lifecycleScope.launch {
            // Execute em segundo plano usando o thread IO
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-note"
                ).fallbackToDestructiveMigration().build()

                db.noteDao().update(note)
            }

            withContext(Dispatchers.Main) {
                binding.tvNote.text = updatedNote
            }
        }
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