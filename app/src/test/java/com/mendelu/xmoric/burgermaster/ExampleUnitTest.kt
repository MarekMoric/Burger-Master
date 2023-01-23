package com.mendelu.xmoric.burgermaster

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mendelu.xmoric.burgermaster.dependency.DITest.provideLocalBurgerRepositoryTest
import com.mendelu.xmoric.burgermaster.ui.screens.ListScreenViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.internal.wait
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = StandardTestDispatcher()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getListSize() {
        Dispatchers.setMain(dispatcher)
        val viewModel = ListScreenViewModel(provideLocalBurgerRepositoryTest())
        viewModel.loadBurgers()
        assertNotNull(viewModel.testList)
        assertEquals(viewModel.testList.size, 1)

    }
}