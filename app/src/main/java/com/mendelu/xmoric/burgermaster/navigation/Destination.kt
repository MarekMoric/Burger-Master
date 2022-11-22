package com.mendelu.xmoric.burgermaster.navigation

sealed class Destination(
    val route: String
) {
    object MapScreen : Destination(route = "map_screen")
    object ListScreen : Destination(route = "list_screen")
    object CreationScreen : Destination(route = "creation_screen")
    object DetailScreen : Destination(route = "detail_screen")
    object ProfileScreen : Destination(route = "profile_screen")
    object NutritionScreen : Destination(route = "nutrition_screen")
    object ARScreen : Destination(route = "ar_screen")
}
