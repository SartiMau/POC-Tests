package com.example.kotlin_counter.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kotlin_counter.data.CountData
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel.KotlinCounterState.INIT
import com.example.kotlin_counter.viewmodel.KotlinCounterViewModel.KotlinCounterState.UPDATE_VALUE
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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

    private lateinit var viewModel: KotlinCounterViewModel

    private val mockedObserver = mockk<Observer<KotlinCounterViewModel.KotlinCounterData>>(relaxed = true)

    // This is just here to show how would you mock a class with MockK
    // private val mockedDataBase: CountData = mockkClass(CountData::class)

    private val dataBase = CountData(ZERO_INT)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxed = true)
        viewModel = KotlinCounterViewModel()
    }

    @After
    fun afterTests() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `ViewModel initialization test`() {
        val emmitedResultList = mutableListOf<KotlinCounterViewModel.KotlinCounterData>()

        viewModel.getLiveData().observeForever(mockedObserver)
        viewModel.init(dataBase)

        verify { mockedObserver.onChanged(capture(emmitedResultList)) }

        assertEquals(INIT, emmitedResultList[0].state)
        assertEquals(ZERO_STRING, emmitedResultList[0].count)
    }

    @Test
    fun `ViewModel set plus one test`() {
        viewModel.init(dataBase)

        // Here we use the UnconfinedTestDispatcher for executing coroutines.
        runTest(UnconfinedTestDispatcher()) {

            // We can omit this step, depending if we are expecting more than one value to be posted from the ViewModel
            // in this case we are expecting two posts so we need this for catching them
            val emmitedResultList = mutableListOf<KotlinCounterViewModel.KotlinCounterData>()

            // Here we attach our mockedObserver to the ViewModel live data
            viewModel.getLiveData().observeForever(mockedObserver)

            // we need to add a .join to the sentence to make it wait for the coroutine to finish.
            viewModel.setPlusOne().join()

            // With this .onChanged() we capture the posted value in our list.
            coVerify { mockedObserver.onChanged(capture(emmitedResultList)) }

            // And here we verify that the posted values are what we expected.
            assertEquals(INIT, emmitedResultList[0].state)
            assertEquals(ZERO_STRING, emmitedResultList[0].count)
            assertEquals(UPDATE_VALUE, emmitedResultList[1].state)
            assertEquals(ONE_STRING, emmitedResultList[1].count)
        }
    }

    @Test
    fun `ViewModel set minus one test`() {
        viewModel.init(dataBase)

        runTest(UnconfinedTestDispatcher()) {
            val emmitedResultList = mutableListOf<KotlinCounterViewModel.KotlinCounterData>()

            viewModel.getLiveData().observeForever(mockedObserver)

            viewModel.setPlusOne().join()
            viewModel.setMinusOne().join()

            coVerify { mockedObserver.onChanged(capture(emmitedResultList)) }

            assertEquals(INIT, emmitedResultList[0].state)
            assertEquals(ZERO_STRING, emmitedResultList[0].count)
            assertEquals(UPDATE_VALUE, emmitedResultList[1].state)
            assertEquals(ONE_STRING, emmitedResultList[1].count)
            assertEquals(UPDATE_VALUE, emmitedResultList[2].state)
            assertEquals(ZERO_STRING, emmitedResultList[2].count)
        }
    }

    @Test
    fun `ViewModel reset counter test`() {
        viewModel.init(dataBase)

        runTest(UnconfinedTestDispatcher()) {
            val emmitedResultList = arrayListOf<KotlinCounterViewModel.KotlinCounterData>()

            viewModel.getLiveData().observeForever(mockedObserver)

            runBlocking {
                viewModel.setPlusOne().join()
                viewModel.setReset().join()
            }

            coVerify { mockedObserver.onChanged(capture(emmitedResultList)) }

            assertEquals(INIT, emmitedResultList[0].state)
            assertEquals(ZERO_STRING, emmitedResultList[0].count)
            assertEquals(UPDATE_VALUE, emmitedResultList[1].state)
            assertEquals(ONE_STRING, emmitedResultList[1].count)
            assertEquals(UPDATE_VALUE, emmitedResultList[2].state)
            assertEquals(ZERO_STRING, emmitedResultList[2].count)
        }
    }

    companion object {
        const val ZERO_STRING = "0"
        const val ONE_STRING = "1"
        const val ZERO_INT = 0
    }
}
