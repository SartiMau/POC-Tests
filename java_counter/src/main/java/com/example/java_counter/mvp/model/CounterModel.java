package com.example.java_counter.mvp.model;

import com.example.java_counter.mvp.contract.CounterContract;

public class CounterModel implements CounterContract.CounterModelContract {
    private int count;

    public CounterModel(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

}
