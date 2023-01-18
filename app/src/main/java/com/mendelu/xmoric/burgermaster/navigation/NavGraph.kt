package com.mendelu.xmoric.burgermaster.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mendelu.xmoric.burgermaster.ui.screens.*


@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember { NavigationRouterImpl(navController) },
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination){

        composable(Destination.ListScreen.route) {
            ListScreen(navigation = navigation)
        }

        composable(Destination.MapScreen.route) {
            MapScreen(navigation = navigation)
        }

        composable(Destination.CreationScreen.route) {
            CreationScreen(navigation = navigation)
        }

        composable(Destination.DetailScreen.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = -1L
            })
        ){
            val id = it.arguments?.getLong("id")
            DetailScreen(
                navigation = navigation,
                id = if (id != -1L) id else null
            )
        }

        composable(Destination.ProfileScreen.route) {
            ProfileScreen(navigation = navigation)
        }

        composable(Destination.NutritionScreen.route) {
            NutritionScreen(navigation = navigation)
        }

        composable(Destination.ARScreen.route) {
            ARScreen(
//                navigation = navigation
            )
        }
    }
}
