package com.mendelu.xmoric.burgermaster.ui.screens

import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import com.mendelu.xmoric.ukol2.architecture.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val burgersRepository: IBurgerLocalRepository): BaseViewModel() {

    var burgerId: Long? = null
    var burger: Burger = Burger("")
    var bread: String = ""
    var meat: String = ""
    var sauce = mutableListOf("")
    var extras = mutableListOf("")


    private val _detailUIState
            = MutableStateFlow<DetailUIState>(DetailUIState.Default)

    val detailUIState: StateFlow<DetailUIState> = _detailUIState

    fun initBurger() {
        if (burgerId != null){
            launch {
                burger = burgersRepository.findById(id = burgerId!!)
                _detailUIState.value = DetailUIState.BurgerLoaded
            }
        }else{
            _detailUIState.value = DetailUIState.BurgerError(-666)
        }
    }

    fun deleteBurger() {
        launch {
            burgersRepository.delete(burger)
            _detailUIState.value = DetailUIState.BurgerRemoved
        }
    }
}