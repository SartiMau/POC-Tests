package com.example.java_counter.mvp.contract;

public interface CounterContract {

    interface Model {
        void setCount(int count);
        int getCount();
    }

    interface View {
        void updateValue(String count);
        String getCountShowed();
    }

    interface Presenter {
        void setPlusOne();
        void setMinusOne();
        void resetCount();
        void init();
        String getCountShowed();
    }
}
