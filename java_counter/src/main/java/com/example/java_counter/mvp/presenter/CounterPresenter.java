package com.example.java_counter.mvp.presenter;

import com.example.java_counter.mvp.contract.CounterContract;
import com.example.java_counter.mvp.model.CounterModel;
import com.example.java_counter.mvp.view.CounterView;

public class CounterPresenter implements CounterContract.CounterPresenterContract {

    private final CounterView view;
    private final CounterModel model;
    private static final int ZERO = 0;

    public CounterPresenter(CounterView view, CounterModel model) {
        this.view = view;
        this.model = model;
    }

    private void updateValue() {
        view.updateValue(model.getCount());
    }

    @Override
    public void init() {
        view.updateValue(model.getCount());
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
