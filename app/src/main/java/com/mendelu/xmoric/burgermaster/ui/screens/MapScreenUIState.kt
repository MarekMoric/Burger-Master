package com.mendelu.xmoric.burgermaster.ui.screens

sealed class MapScreenUIState<out T>{
    object Start : MapScreenUIState<Nothing>()
    class Success<T>(var data: T) : MapScreenUIState<T>()
    object Error : MapScreenUIState<Nothing>()
}