package com.example.poc_tests.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.java_counter.activity.JavaCounterActivity
import com.example.kotlin_counter.activity.KotlinCounterActivity
import com.example.poc_tests.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.mainScreenJavaButton.setOnClickListener {
            startActivity(Intent(applicationContext, JavaCounterActivity::class.java))
        }
        binding.mainScreenKotlinButton.setOnClickListener {
            startActivity(Intent(applicationContext, KotlinCounterActivity::class.java))
        }
    }
}
