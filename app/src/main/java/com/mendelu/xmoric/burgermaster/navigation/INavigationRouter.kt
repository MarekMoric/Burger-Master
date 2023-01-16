package com.mendelu.xmoric.burgermaster.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToMap()
    fun navigateToList()
    fun navigateToProfile()
    fun navigateToNutrition()
    fun navigateToAR()
    fun navigateToDetail(id: Long?)
}