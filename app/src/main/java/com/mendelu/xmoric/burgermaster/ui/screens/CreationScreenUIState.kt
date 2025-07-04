package com.mendelu.xmoric.burgermaster.ui.screens

sealed class CreationScreenUIState{
    object Default : CreationScreenUIState()
    object BurgerSaved : CreationScreenUIState()
    class BurgerError(val error: Int) : CreationScreenUIState()

}
