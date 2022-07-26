package com.example.java_counter.viewmodeltest;

import static org.junit.Assert.assertEquals;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.example.java_counter.TestObserver;
import com.example.java_counter.model.JavaCounterModel;
import com.example.java_counter.viewmodel.JavaCounterViewModel;
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
    private final JavaCounterModel model = new JavaCounterModel(0);
    private JavaCounterViewModel viewModel;
    private final TestObserver testObserver = new TestObserver();

    @Before
    public void setUp() {
        viewModel = new JavaCounterViewModel(model);
    }

    @Test
    public void viewModelInitTest() {
        viewModel.getLiveData().observeForever(testObserver);

        viewModel.init();

        assertEquals(
                JavaCounterViewModel.JavaCounterState.INIT,
                testObserver.getObservedValues().get(0).getState());
        assertEquals(
                ZERO,
                testObserver.getObservedValues().get(0).getCount());
    }

    @Test
    public void viewModelIncreaseTest() {
        viewModel.getLiveData().observeForever(testObserver);

        viewModel.setPlusOne();

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(0).getState());
        assertEquals(ONE, testObserver.getObservedValues().get(0).getCount());
    }

    @Test
    public void viewModelDecreaseTest() {
        viewModel.getLiveData().observeForever(testObserver);

        viewModel.setPlusOne();
        viewModel.setMinusOne();

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(0).getState());
        assertEquals(ONE, testObserver.getObservedValues().get(0).getCount());

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(1).getState());
        assertEquals(ZERO, testObserver.getObservedValues().get(1).getCount());
    }

    @Test
    public void viewModelResetCountTest() {
        viewModel.getLiveData().observeForever(testObserver);

        viewModel.setPlusOne();
        viewModel.setPlusOne();
        viewModel.resetCount();

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(0).getState());
        assertEquals(ONE, testObserver.getObservedValues().get(0).getCount());

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(1).getState());
        assertEquals(TWO, testObserver.getObservedValues().get(1).getCount());

        assertEquals(JavaCounterViewModel.JavaCounterState.UPDATE_VALUE, testObserver.getObservedValues().get(2).getState());
        assertEquals(ZERO, testObserver.getObservedValues().get(2).getCount());
    }
}
