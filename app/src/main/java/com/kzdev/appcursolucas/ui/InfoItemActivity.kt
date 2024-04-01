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
        val uid = intent.getIntExtra("uid", 0)
        val note = intent.getStringExtra("note")

        binding.tvUid.text = uid.toString()
        binding.tvNote.text = note
    }

    private fun deleteNote() {


        val uid = intent.getIntExtra("uid", 0)
        val note = intent.getStringExtra("note")

        val identification = Note(uid, note)


        lifecycleScope.launch {

            // escolhemos o thread IO impute output entrada e saida de info para rodar em segundo plano a ação
            withContext(Dispatchers.IO) {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-note"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                db.noteDao().delete(identification)

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
        val dialog = builder.create()
        dialog.show()
    }
}