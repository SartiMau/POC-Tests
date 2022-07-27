package com.example.kotlin_counter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.kotlin_counter.databinding.KotlinCounterActivityBinding
import com.example.kotlin_counter.mvvm.model.KotlinCounterModel
import com.example.kotlin_counter.util.Constants.ZERO
import com.example.kotlin_counter.mvvm.viewmodel.KotlinCounterViewModel

interface IdlingResource {
    fun setIdler(countingIdlingResource: CountingIdlingResource)
}

class KotlinCounterActivity : AppCompatActivity(), IdlingResource {

    private lateinit var binding: KotlinCounterActivityBinding
    private lateinit var viewModel: KotlinCounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = KotlinCounterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = KotlinCounterViewModel(KotlinCounterModel(ZERO))
        viewModel.getLiveData().observe({ lifecycle }, ::updateUI)

        viewModel.init()
    }

    private fun updateUI(data: KotlinCounterViewModel.KotlinCounterData) {
        when (data.state) {
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

    override fun setIdler(countingIdlingResource: CountingIdlingResource) {

    }
}
