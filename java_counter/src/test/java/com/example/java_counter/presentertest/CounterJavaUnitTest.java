package com.example.java_counter.presentertest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.example.java_counter.mvp.model.CounterModel;
import com.example.java_counter.mvp.presenter.CounterPresenter;
import com.example.java_counter.mvp.view.CounterView;
import static org.mockito.Mockito.*;
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
    private static final int ONE = 1;
    private static final int TWO = 2;
    private CounterModel model;
    private final CounterView view = mock(CounterView.class);
    private CounterPresenter presenter;

    @Before
    public void setUp() {
        model = new CounterModel(0);
        presenter = new CounterPresenter(view, model);
    }

    @Test
    public void viewModelInitTest() {
        presenter.init();

        verify(view, times(1)).updateValue(ZERO);
    }

    @Test
    public void viewModelIncreaseTest() {
        presenter.init();
        presenter.setPlusOne();

        verify(view, times(1)).updateValue(ZERO);
        verify(view, times(1)).updateValue(ONE);
    }

    @Test
    public void viewModelDecreaseTest() {
        presenter.setPlusOne();
        presenter.setMinusOne();

        verify(view, times(1)).updateValue(ONE);
        verify(view, times(1)).updateValue(ZERO);
    }

    @Test
    public void viewModelResetCountTest() {
        presenter.setPlusOne();
        presenter.setPlusOne();
        presenter.resetCount();

        verify(view, times(1)).updateValue(ONE);
        verify(view, times(1)).updateValue(TWO);
        verify(view, times(1)).updateValue(ZERO);
    }
}
