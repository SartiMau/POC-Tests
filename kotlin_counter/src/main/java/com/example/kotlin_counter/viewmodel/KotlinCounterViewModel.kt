package com.example.kotlin_counter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.kotlin_counter.model.KotlinCounterModel
import com.example.kotlin_counter.util.Constants.ZERO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class KotlinCounterViewModel(private val model: KotlinCounterModel) : ViewModel() {

    private var mutableLiveData = MutableLiveData<KotlinCounterData>()

    fun getLiveData(): LiveData<KotlinCounterData> {
        return mutableLiveData
    }

    fun init() {
        mutableLiveData.value = KotlinCounterData(state = KotlinCounterState.INIT, count = model.count.toString())
    }

    private fun updateValue() {
        mutableLiveData.postValue(
            KotlinCounterData(state = KotlinCounterState.UPDATE_VALUE, count = model.count.toString())
        )
    }

    fun setPlusOne() = CoroutineScope(Dispatchers.IO).launch {
        delay(500)
        model.count++
        updateValue()
    }

    fun setMinusOne() = CoroutineScope(Dispatchers.IO).launch {
        delay(500)
        model.count--
        updateValue()
    }


    fun setReset() = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        model.count = ZERO
        updateValue()
    }


    data class KotlinCounterData(
        val state: KotlinCounterState,
        val count: String
    )

    enum class KotlinCounterState {
        INIT,
        UPDATE_VALUE
    }
}

