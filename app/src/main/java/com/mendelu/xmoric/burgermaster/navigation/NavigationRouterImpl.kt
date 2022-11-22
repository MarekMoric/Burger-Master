package com.mendelu.xmoric.burgermaster.navigation

import androidx.navigation.NavController
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

    override fun navigateToNutrition() {
        navController.navigate(Destination.NutritionScreen.route)
    }

    override fun navigateToAR() {
        navController.navigate(Destination.ARScreen.route)
    }

    override fun navigateToDetail() {
        navController.navigate(Destination.DetailScreen.route)
    }
}