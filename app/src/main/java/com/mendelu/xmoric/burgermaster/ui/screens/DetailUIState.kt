package com.mendelu.xmoric.burgermaster.ui.screens

sealed class DetailUIState{
    object Default : DetailUIState()
    object BurgerLoaded : DetailUIState()
    object BurgerSaved : DetailUIState()
    class BurgerError(val error: Int) : DetailUIState()
    object BurgerRemoved : DetailUIState()

}
