package com.antoniocostadossantos.chat_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.antoniocostadossantos.chat_app.R
import com.antoniocostadossantos.chat_app.databinding.ActivityScreenNameBinding
import com.antoniocostadossantos.chat_app.util.USERNAME_KEY

class ScreenNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sendName()
    }

    private fun sendName() {
        binding.nameLabel.setEndIconOnClickListener {
            if (binding.nameInput.text.toString().isEmpty()) {
                binding.nameLabel.error = "The name cannot be empty."
            } else {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(
                        USERNAME_KEY,
                        binding.nameInput.text.toString()
                    )
                })
                finish()
            }
        }
        binding.nameInput.addTextChangedListener { binding.nameLabel.error = null }
    }
}