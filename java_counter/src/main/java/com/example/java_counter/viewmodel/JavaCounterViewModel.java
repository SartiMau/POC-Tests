package com.example.java_counter.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.java_counter.model.JavaCounterModel;

import java.util.concurrent.CompletableFuture;

public class JavaCounterViewModel {

    private final int ZERO = 0;
    private JavaCounterModel model;
    private MutableLiveData<JavaCounterData> mutableLiveData = new MutableLiveData<>();

    public JavaCounterViewModel(JavaCounterModel javaCounterModel) {
        this.model = javaCounterModel;
    }

    public LiveData<JavaCounterData> getLiveData() {
        return mutableLiveData;
    }

    public void init() {
        mutableLiveData.setValue(new JavaCounterData(JavaCounterState.INIT, model.getCount()));
    }

    private void updateValue() {
        mutableLiveData.postValue(new JavaCounterData(JavaCounterState.UPDATE_VALUE, model.getCount()));
    }

    public void setPlusOne() {
        int count = model.getCount();
        count++;
        model.setCount(count);
        updateValue();
    }

    public void setMinusOne() {
        int count = model.getCount();
        count--;
        model.setCount(count);
        updateValue();
    }

    public void resetCount() {
        model.setCount(ZERO);
        updateValue();
    }

    public static class JavaCounterData {
        JavaCounterState state;
        int count;

        public JavaCounterData(JavaCounterState state, int count) {
            this.state = state;
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public JavaCounterState getState() {
            return state;
        }
    }

    public enum JavaCounterState {
        INIT,
        UPDATE_VALUE
    }
}
