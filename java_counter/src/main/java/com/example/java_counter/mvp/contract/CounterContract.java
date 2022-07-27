package com.example.java_counter.mvp.contract;

public interface CounterContract {

    interface CounterPresenterContract {
        void setPlusOne();

        void setMinusOne();

        void resetCount();

        void init();

    }

    interface CounterModelContract {
        void setCount(int count);

        int getCount();
    }

    interface CounterViewContract {
        void updateValue(int count);
    }
}
