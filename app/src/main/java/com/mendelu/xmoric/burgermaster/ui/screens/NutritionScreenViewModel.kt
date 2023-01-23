package com.mendelu.xmoric.burgermaster.ui.screens

import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import com.mendelu.xmoric.ukol2.architecture.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutritionScreenViewModel(private val burgersRepository: IBurgerLocalRepository): BaseViewModel() {

    var burgerId: Long? = null
    var burger: Burger = Burger("")
    var bread: String = ""
    var meat: String = ""
    var sauce = mutableListOf("")
    var extras = mutableListOf("")

    var carbohydrates: Float = 0.0F
    var protein: Float = 0.0F
    var fat: Float = 0.0F
    var calories: Float = 0.0F


    private val _nutritionUIState = MutableStateFlow<NutritionUIState>(NutritionUIState.Default)

    val nutritionUIState: StateFlow<NutritionUIState> = _nutritionUIState

    fun initBurger() {
        if (burgerId != null) {
            launch {
                burger = burgersRepository.findById(id = burgerId!!)
                _nutritionUIState.value = NutritionUIState.BurgerLoaded
            }
        } else {
            _nutritionUIState.value = NutritionUIState.BurgerError(-666)
        }
    }
}