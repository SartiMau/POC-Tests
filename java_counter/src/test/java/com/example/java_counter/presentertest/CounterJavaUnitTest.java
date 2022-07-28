package com.example.java_counter.presentertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private static final int ONE = 1;
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
    public void mockedViewReturnTest() {
        presenter.init();
        presenter.setPlusOne();

        verify(view, times(ONE)).updateValue(ONE_STRING);

        when(view.getCountShowed()).thenReturn(ONE_STRING);

        assertEquals(ONE_STRING, presenter.getCountShowed());
    }

    @Test
    public void presenterInitTest() {
        presenter.init();

        verify(view, times(ONE)).updateValue(ZERO_STRING);
    }

    @Test
    public void increaseCountTest() {
        presenter.init();
        presenter.setPlusOne();

        verify(view, times(ONE)).updateValue(ZERO_STRING);
        verify(view, times(ONE)).updateValue(ONE_STRING);
    }

    @Test
    public void decreaseCountTest() {
        presenter.setPlusOne();
        presenter.setMinusOne();

        verify(view, times(ONE)).updateValue(ONE_STRING);
        verify(view, times(ONE)).updateValue(ZERO_STRING);
    }

    @Test
    public void resetCountTest() {
        presenter.setPlusOne();
        presenter.setPlusOne();
        presenter.resetCount();

        verify(view, times(ONE)).updateValue(ONE_STRING);
        verify(view, times(ONE)).updateValue(TWO_STRING);
        verify(view, times(ONE)).updateValue(ZERO_STRING);
    }
}
