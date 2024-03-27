package com.kzdev.appcursolucas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kzdev.appcursolucas.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {

   private val binding by lazy { ActivityContentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}