package com.mendelu.xmoric.burgermaster.ui.screens

import android.util.Log
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.database.IBurgerLocalRepository
import com.mendelu.xmoric.ukol2.architecture.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreationScreenViewModel(private val burgerRepository: IBurgerLocalRepository): BaseViewModel() {

    var name: String = ""
    var bread: String = ""
    var meat: String = ""
    var sauce = mutableListOf("")
    var extras = mutableListOf("")

    var burgerId: Long? = null
    var burger: Burger = Burger("")

    val _creationScreenUIState
            = MutableStateFlow<CreationScreenUIState>(CreationScreenUIState.Default)

    fun saveBurger() {
        if (name == ""){
            name = "Burger"
        }
        burger.name = name
        burger.bread = bread
        burger.meat = meat
        burger.sauce = sauce[0]
        burger.extras = extras[0]
        if(burger.bread.isNullOrBlank() && burger.meat.isNullOrBlank() && burger.sauce.isNullOrBlank() && burger.extras.isNullOrBlank()){
            Log.d("burger properties err", burger.bread!! + burger.meat!! + burger.sauce + burger.extras )
            _creationScreenUIState.value = CreationScreenUIState.BurgerError(-666)
        }else{
            launch {
//                if (burgerId != null) {
//                    burgerRepository.update(burger)
//                }else{
                Log.d("burger properties", burger.bread!! + burger.meat!! + burger.sauce + burger.extras )
                    burgerRepository.insert(burger)
                }
            _creationScreenUIState.value = CreationScreenUIState.BurgerSaved
        }
    }

}