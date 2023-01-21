package com.mendelu.xmoric.burgermaster.ui.screens

import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import com.mendelu.xmoric.ukol2.architecture.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class ListScreenViewModel(private val burgersRepository: IBurgerLocalRepository): BaseViewModel() {

    private val _listUIState
            = MutableStateFlow<ListUIState>(ListUIState.Default)

    var testList = listOf(Burger(""))

    val listUIState: StateFlow<ListUIState> = _listUIState
    fun loadBurgers() {
        launch {
            burgersRepository.getAll().collect{
                _listUIState.value = ListUIState.BurgersLoaded(it)
                testList = it //unit testing
            }
        }
    }

}