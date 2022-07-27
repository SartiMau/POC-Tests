package com.example.java_counter.mvp.view;

import android.app.Activity;
import com.example.java_counter.databinding.JavaActivityCounterBinding;
import com.example.java_counter.mvp.contract.CounterContract;
import com.example.java_counter.mvp.view.base.ActivityView;

public class CounterView extends ActivityView implements CounterContract.View {

    private final JavaActivityCounterBinding binding;

    public CounterView(Activity activity, JavaActivityCounterBinding binding) {
        super(activity);
        this.binding = binding;
    }

    @Override
    public void updateValue(String count) {
        binding.javaCounterText.setText(count);
    }
}
