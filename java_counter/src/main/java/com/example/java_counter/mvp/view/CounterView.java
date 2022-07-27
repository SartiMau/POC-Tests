package com.example.java_counter.mvp.view;

import android.app.Activity;
import com.example.java_counter.databinding.JavaActivityCounterBinding;
import com.example.java_counter.mvp.contract.CounterContract;
import com.example.java_counter.mvp.view.base.ActivityView;

public class CounterView extends ActivityView implements CounterContract.CounterViewContract {

    private JavaActivityCounterBinding binding;

    public CounterView(Activity activity, JavaActivityCounterBinding binding) {
        super(activity);
        this.binding = binding;
    }

    @Override
    public void updateValue(int count) {
        binding.javaCounterText.setText(String.valueOf(count));
    }
}
