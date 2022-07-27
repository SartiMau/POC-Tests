package com.example.java_counter.presentertest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.example.java_counter.mvp.contract.CounterContract;
import com.example.java_counter.mvp.model.CounterModel;
import com.example.java_counter.mvp.presenter.CounterPresenter;
import com.example.java_counter.mvp.view.CounterView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CounterJavaUnitTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private static final int ZERO = 0;
    private static final String ZERO_STRING = "0";
    private static final String ONE_STRING = "1";
    private static final String TWO_STRING = "2";

    private CounterContract.Presenter presenter;
    private final CounterContract.View view = mock(CounterView.class);

    @Before
    public void setUp() {
        presenter = new CounterPresenter(view, new CounterModel(ZERO));
    }

    @Test
    public void viewModelInitTest() {
        presenter.init();

        verify(view, times(1)).updateValue(ZERO_STRING);
    }

    @Test
    public void viewModelIncreaseTest() {
        presenter.init();
        presenter.setPlusOne();

        verify(view, times(1)).updateValue(ZERO_STRING);
        verify(view, times(1)).updateValue(ONE_STRING);
    }

    @Test
    public void viewModelDecreaseTest() {
        presenter.setPlusOne();
        presenter.setMinusOne();

        verify(view, times(1)).updateValue(ONE_STRING);
        verify(view, times(1)).updateValue(ZERO_STRING);
    }

    @Test
    public void viewModelResetCountTest() {
        presenter.setPlusOne();
        presenter.setPlusOne();
        presenter.resetCount();

        verify(view, times(1)).updateValue(ONE_STRING);
        verify(view, times(1)).updateValue(TWO_STRING);
        verify(view, times(1)).updateValue(ZERO_STRING);
    }
}
