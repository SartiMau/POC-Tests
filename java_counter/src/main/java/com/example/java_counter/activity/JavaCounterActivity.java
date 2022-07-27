package com.example.java_counter.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.java_counter.databinding.JavaActivityCounterBinding;
import com.example.java_counter.mvp.model.CounterModel;
import com.example.java_counter.mvp.presenter.CounterPresenter;
import com.example.java_counter.mvp.view.CounterView;

public class JavaCounterActivity extends AppCompatActivity {

    private JavaActivityCounterBinding binding;
    private CounterPresenter presenter;
    private static final int ZERO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = JavaActivityCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new CounterPresenter(new CounterView(this, binding), new CounterModel(ZERO));
        presenter.init();

        setListeners();
    }

    private void setListeners() {
        binding.javaCounterPlusButton.setOnClickListener(view -> presenter.setPlusOne());

        binding.javaCounterMinusButton.setOnClickListener(view -> presenter.setMinusOne());

        binding.javaCounterResetButton.setOnClickListener(view -> presenter.resetCount());
    }
}
