package com.mendelu.xmoric.burgermaster

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mendelu.xmoric.burgermaster.dependency.DITest.provideLocalBurgerRepositoryTest
import com.mendelu.xmoric.burgermaster.ui.screens.DetailScreenViewModel
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
    fun getList() {
        Dispatchers.setMain(dispatcher)
        val viewModel = ListScreenViewModel(provideLocalBurgerRepositoryTest())
        viewModel.loadBurgers()
        assertNotNull(viewModel.testList)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getListSize() {
        Dispatchers.setMain(dispatcher)
        val viewModel = ListScreenViewModel(provideLocalBurgerRepositoryTest())
        viewModel.loadBurgers()
        assertEquals(viewModel.testList.size, 1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryInit() {
        Dispatchers.setMain(dispatcher)
        val viewModel = DetailScreenViewModel(provideLocalBurgerRepositoryTest())
        viewModel.initBurger()
        assertNotEquals(viewModel.testBurger, null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryDelete() {
        Dispatchers.setMain(dispatcher)
        val viewModel = DetailScreenViewModel(provideLocalBurgerRepositoryTest())
        viewModel.initBurger()
        viewModel.deleteBurger()
        assertEquals(viewModel.testBurger.toString(), "com.mendelu.xmoric.burgermaster.database.Burger@2bd08376")
    }
}