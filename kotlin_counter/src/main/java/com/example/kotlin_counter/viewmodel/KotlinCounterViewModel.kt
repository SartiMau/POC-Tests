package com.example.kotlin_counter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_counter.data.CountData
import com.example.kotlin_counter.util.Constants.ONE
import com.example.kotlin_counter.util.Constants.ZERO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KotlinCounterViewModel : ViewModel() {

    private lateinit var data: CountData
    private var mutableLiveData = MutableLiveData<KotlinCounterData>()

    fun getLiveData(): LiveData<KotlinCounterData> {
        return mutableLiveData
    }

    fun init(data: CountData) {
        this.data = data
        mutableLiveData.value = KotlinCounterData(state = KotlinCounterState.INIT, count = data.count.toString())
    }

    private fun updateValue() {
        mutableLiveData.postValue(
            KotlinCounterData(state = KotlinCounterState.UPDATE_VALUE, count = data.count.toString())
        )
    }

    fun setPlusOne() = CoroutineScope(Dispatchers.IO).launch {
        delay(500)
        data.count += ONE
        updateValue()
    }

    fun setMinusOne() = CoroutineScope(Dispatchers.IO).launch {
        delay(500)
        data.count -= ONE
        updateValue()
    }


    fun setReset() = CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        data.count = ZERO
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

