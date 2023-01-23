package com.mendelu.xmoric.burgermaster.navigation

import androidx.navigation.NavController
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.navigation.Destination
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun getNavController(): NavController = navController

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToMap() {
        navController.navigate(Destination.MapScreen.route)
    }

    override fun navigateToList() {
        navController.navigate(Destination.ListScreen.route)
    }

    override fun navigateToProfile() {
        navController.navigate(Destination.ProfileScreen.route)
    }

    override fun navigateToNutrition(id: Long?) {
        navController.navigate(Destination.NutritionScreen.route + "/" + id)
    }

    override fun navigateToAR() {
        navController.navigate(Destination.ARScreen.route)
    }

    override fun navigateToDetail(id: Long?) {
        navController.navigate(Destination.DetailScreen.route + "/" + id)
    }
}