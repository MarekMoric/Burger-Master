package com.mendelu.xmoric.burgermaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mendelu.xmoric.burgermaster.navigation.Destination
import com.mendelu.xmoric.burgermaster.navigation.NavGraph
import com.mendelu.xmoric.burgermaster.navigation.botnav.BottomBarWithFabDem
import com.mendelu.xmoric.burgermaster.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BurgerMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    NavGraph(startDestination = Destination.ListScreen.route)
                    BottomBarWithFabDem()
                }
            }
        }
    }
}
