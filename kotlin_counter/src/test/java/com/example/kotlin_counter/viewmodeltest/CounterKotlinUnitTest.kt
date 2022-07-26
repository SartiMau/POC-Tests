package com.example.kotlin_counter.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kotlin_counter.model.KotlinCounterModel
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel.KotlinCounterState.INIT
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel.KotlinCounterState.UPDATE_VALUE
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CounterKotlinUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: KotlinCounterViewModel

    private val mockedObserver = mockk<Observer<KotlinCounterViewModel.KotlinCounterData>>(relaxed = true)

    // This is just here to show how would you mock a class with MockK
    // private val mockedModel: KotlinCounterModel = mockkClass(KotlinCounterModel::class)

    private val model = KotlinCounterModel(ZERO_INT)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init()
        viewModel = KotlinCounterViewModel(model)
    }

    @After
    fun afterTests() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `ViewModel initialization test`() {
        val list = arrayListOf<KotlinCounterViewModel.KotlinCounterData>()

        viewModel.getLiveData().observeForever(mockedObserver)
        viewModel.init()

        verify { mockedObserver.onChanged(capture(list)) }

        assertEquals(INIT, list[0].state)
        assertEquals(ZERO_STRING, list[0].count)
    }

    @Test
    fun `ViewModel set plus one test`() {
        viewModel.init()

        // We can omit this step, depending if we are expecting more than one value to be posted from the ViewModel
        // in this case we are expecting two posts so we need this for catching them
        val list = arrayListOf<KotlinCounterViewModel.KotlinCounterData>()

        // Here we attach our mockedObserver to the ViewModel live data
        viewModel.getLiveData().observeForever(mockedObserver)
        // We use runBlocking to execute the coroutine, and since we are using a delay for simulation purposes,
        // we need to add a .join to the sentence to make it wait for the coroutine to finish.
        runBlocking {
            viewModel.setPlusOne().join()
        }

        // With this .onChanged() we capture the posted value in our list.
        verify { mockedObserver.onChanged(capture(list)) }

        // And here we verify that the posted values are what we expected.
        assertEquals(INIT, list[0].state)
        assertEquals(ZERO_STRING, list[0].count)
        assertEquals(UPDATE_VALUE, list[1].state)
        assertEquals(ONE_STRING, list[1].count)
    }

    @Test
    fun `ViewModel set minus one test`() {
        viewModel.init()

        val list = arrayListOf<KotlinCounterViewModel.KotlinCounterData>()

        viewModel.getLiveData().observeForever(mockedObserver)
        runBlocking {
            viewModel.setPlusOne().join()
            viewModel.setMinusOne().join()
        }

        verify { mockedObserver.onChanged(capture(list)) }

        assertEquals(INIT, list[0].state)
        assertEquals(ZERO_STRING, list[0].count)
        assertEquals(UPDATE_VALUE, list[1].state)
        assertEquals(ONE_STRING, list[1].count)
        assertEquals(UPDATE_VALUE, list[2].state)
        assertEquals(ZERO_STRING, list[2].count)
    }

    @Test
    fun `ViewModel reset counter test`() {
        viewModel.init()
        val list = arrayListOf<KotlinCounterViewModel.KotlinCounterData>()

        viewModel.getLiveData().observeForever(mockedObserver)

        runBlocking {
            viewModel.setPlusOne().join()
            viewModel.setReset().join()
        }

        verify { mockedObserver.onChanged(capture(list)) }

        assertEquals(INIT, list[0].state)
        assertEquals(ZERO_STRING, list[0].count)
        assertEquals(UPDATE_VALUE, list[1].state)
        assertEquals(ONE_STRING, list[1].count)
        assertEquals(UPDATE_VALUE, list[2].state)
        assertEquals(ZERO_STRING, list[2].count)
    }

    companion object {
        const val ZERO_STRING = "0"
        const val ONE_STRING = "1"
        const val ZERO_INT = 0
    }
}
