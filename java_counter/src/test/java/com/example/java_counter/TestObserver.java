package com.example.java_counter;


import androidx.lifecycle.Observer;
import com.example.java_counter.viewmodel.JavaCounterViewModel;
import java.util.ArrayList;

public class TestObserver implements Observer {

    ArrayList<JavaCounterViewModel.JavaCounterData> observedValues = new ArrayList<>();

    @Override
    public void onChanged(Object o) {
        observedValues.add((JavaCounterViewModel.JavaCounterData) o);
    }

    public ArrayList<JavaCounterViewModel.JavaCounterData> getObservedValues() {
        return observedValues;
    }
}
