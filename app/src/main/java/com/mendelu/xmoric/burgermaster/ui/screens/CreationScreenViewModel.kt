package com.mendelu.xmoric.burgermaster.ui.screens

import com.mendelu.xmoric.ukol2.architecture.BaseViewModel

class CreationScreenViewModel(): BaseViewModel() {

    var bread: String = ""
    var meat: String = ""
    var sauce = mutableListOf("")
    val extras = mutableListOf("")

}