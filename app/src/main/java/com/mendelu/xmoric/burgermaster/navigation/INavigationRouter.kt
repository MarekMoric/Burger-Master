package com.mendelu.xmoric.burgermaster.navigation

import androidx.navigation.NavController
import com.mendelu.xmoric.burgermaster.database.Burger

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToMap()
    fun navigateToList()
    fun navigateToProfile()
    fun navigateToNutrition(id: Long?)
    fun navigateToAR()
    fun navigateToDetail(id: Long?)
}