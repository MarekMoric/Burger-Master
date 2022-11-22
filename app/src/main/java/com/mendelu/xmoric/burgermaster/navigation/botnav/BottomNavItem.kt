package com.mendelu.xmoric.burgermaster.navigation.botnav

import com.mendelu.xmoric.burgermaster.R

sealed class BottomNavItem(var title: String, var icon: Int, var route: String){

    object List : BottomNavItem("My List", R.drawable.ic_format_list_bulleted,"list_screen")
    object Create: BottomNavItem("Create",R.drawable.ic_add,"creation_screen")
    object Profile: BottomNavItem("Profile",R.drawable.ic_person,"profile_screen")
}