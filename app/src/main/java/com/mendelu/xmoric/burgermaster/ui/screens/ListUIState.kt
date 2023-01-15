package com.mendelu.xmoric.burgermaster.ui.screens

import com.mendelu.xmoric.burgermaster.database.Burger

sealed class ListUIState{
    object Default : ListUIState()
    class BurgersLoaded(val burgers: List<Burger>) : ListUIState()

}
