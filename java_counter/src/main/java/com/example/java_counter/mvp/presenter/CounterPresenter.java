package com.example.java_counter.mvp.presenter;

import static com.example.java_counter.util.ConstantUtils.ZERO;
import com.example.java_counter.mvp.contract.CounterContract;

public class CounterPresenter implements CounterContract.Presenter {

    private final CounterContract.View view;
    private final CounterContract.Model model;

    public CounterPresenter(CounterContract.View view, CounterContract.Model model) {
        this.view = view;
        this.model = model;
    }

    private void updateValue() {
        view.updateValue(String.valueOf(model.getCount()));
    }

    @Override
    public void init() {
        updateValue();
    }

    @Override
    public void setPlusOne() {
        int count = model.getCount();
        count++;
        model.setCount(count);
        updateValue();
    }

    @Override
    public void setMinusOne() {
        int count = model.getCount();
        count--;
        model.setCount(count);
        updateValue();
    }

    @Override
    public void resetCount() {
        model.setCount(ZERO);
        updateValue();
    }
}
