package com.example.kotlin_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_counter.data.CountData
import com.example.kotlin_counter.databinding.KotlinMainActivityBinding
import com.example.kotlin_counter.util.Constants.ZERO
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel

class KotlinMainActivity : AppCompatActivity() {

    private lateinit var binding: KotlinMainActivityBinding
    private lateinit var viewModel: KotlinCounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = KotlinMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = KotlinCounterViewModel()
        viewModel.getLiveData().observe({ lifecycle }, ::updateUI)

        viewModel.init(CountData(ZERO))
    }

    private fun updateUI(data: KotlinCounterViewModel.KotlinCounterData) {
        when(data.state) {
            KotlinCounterViewModel.KotlinCounterState.INIT -> init(data.count)
            KotlinCounterViewModel.KotlinCounterState.UPDATE_VALUE -> binding.kotlinCounterText.text = data.count
        }
    }

    private fun init(count: String) {
        binding.kotlinCounterText.text = count
        setListeners()
    }

    private fun setListeners() {
        binding.kotlinCounterPlusButton.setOnClickListener {
            viewModel.setPlusOne()
        }

        binding.kotlinCounterMinusButton.setOnClickListener {
            viewModel.setMinusOne()
        }

        binding.kotlinCounterResetButton.setOnClickListener {
            viewModel.setReset()
        }
    }
}
