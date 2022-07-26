package com.example.java_counter.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.java_counter.databinding.JavaActivityCounterBinding;
import com.example.java_counter.model.JavaCounterModel;
import com.example.java_counter.viewmodel.JavaCounterViewModel;

public class JavaCounterActivity extends AppCompatActivity {

    private JavaActivityCounterBinding binding;
    private JavaCounterViewModel viewModel;
    private final int ZERO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = JavaActivityCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new JavaCounterViewModel(new JavaCounterModel(ZERO));
        viewModel.getLiveData().observe(this, updateUI);

        viewModel.init();
    }

    final Observer<JavaCounterViewModel.JavaCounterData> updateUI =
            data -> {
                switch (data.getState()) {
                    case INIT: {
                        init(data.getCount());
                    }
                    case UPDATE_VALUE: {
                        binding.javaCounterText.setText(String.valueOf(data.getCount()));
                    }
                }
            };

    private void init(int count) {
        binding.javaCounterText.setText(String.valueOf(count));
        setListeners();
    }

    private void setListeners() {
        binding.javaCounterPlusButton.setOnClickListener(view -> viewModel.setPlusOne());

        binding.javaCounterMinusButton.setOnClickListener(view -> viewModel.setMinusOne());

        binding.javaCounterResetButton.setOnClickListener(view -> viewModel.resetCount());
    }
}
