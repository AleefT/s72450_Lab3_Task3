package com.example.s72450_lab3_task3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import com.example.s72450_lab3_task3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentName: String = ""
    private var isGreetingEnabled: Boolean = false
    private var isShoutMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        // TextWatcher for EditText
        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentName = s?.toString()?.trim() ?: ""
                updateGreeting()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Switch listener
        binding.switchEnable.setOnCheckedChangeListener { _, isChecked ->
            isGreetingEnabled = isChecked
            updateStatusText()
            updateGreeting()
        }

        // CheckBox listener for Shout Mode
        binding.checkShout.setOnCheckedChangeListener { _, isChecked ->
            isShoutMode = isChecked
            updateGreeting()
        }

        // SeekBar listener for text size
        binding.seekTextSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = if (progress < 12) 12f else progress.toFloat()
                binding.txtGreeting.textSize = size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateStatusText() {
        binding.txtStatus.text = if (isGreetingEnabled) {
            "Greeting is enabled."
        } else {
            "Greeting is disabled."
        }
    }

    private fun updateGreeting() {
        val greeting = when {
            isGreetingEnabled && currentName.isNotEmpty() -> "Hello, $currentName!"
            isGreetingEnabled -> "Please type your name."
            else -> ""
        }
        binding.txtGreeting.text = if (isShoutMode) greeting.uppercase() else greeting
    }
}