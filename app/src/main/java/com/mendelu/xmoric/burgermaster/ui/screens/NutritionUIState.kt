package com.mendelu.xmoric.burgermaster.ui.screens

sealed class NutritionUIState{
    object Default : NutritionUIState()
    object BurgerLoaded : NutritionUIState()
    class BurgerError(val error: Int) : NutritionUIState()

}