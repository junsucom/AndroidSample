package com.junsu.sample.ui.liveData

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.junsu.sample.util.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LiveDataViewModelTest {
    private lateinit var viewModel: LiveDataViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initViewModel() {
        viewModel = LiveDataViewModel()
    }

    @Test
    fun getNumberData() {
        val currentValue: Int = viewModel.numberData.getOrAwaitValue()
        viewModel.addNumberData()
        TestCase.assertEquals(viewModel.numberData.getOrAwaitValue(), currentValue + 1)
    }

    @Test
    fun getListData() {
        val currentValueSize = viewModel.listData.getOrAwaitValue().size
        viewModel.addListData("cc")
        TestCase.assertEquals(viewModel.listData.getOrAwaitValue().size, currentValueSize + 1)
    }
}