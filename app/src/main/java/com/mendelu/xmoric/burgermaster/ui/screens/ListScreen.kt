package com.mendelu.xmoric.burgermaster.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import org.koin.androidx.compose.getViewModel
import com.mendelu.xmoric.burgermaster.R
import com.mendelu.xmoric.burgermaster.ui.theme.LightGreen
import cz.mendelu.pef.compose.petstore.constants.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navigation: INavigationRouter,
               viewModel: ListScreenViewModel = getViewModel()) {

    val burgers = remember{ mutableListOf<Burger>()}
    val taskListUIState: ListUIState? by viewModel.listUIState.collectAsState()

    val onReturnRefresh = navigation.getNavController().currentBackStackEntry?.savedStateHandle
        ?.getLiveData<Boolean>(Constants.REFRESH_SCREEN)
        ?.observeAsState() //refresh ked sa vratim z deletu napriklad

    onReturnRefresh?.value?.let {
        if (it) {
            LaunchedEffect(it) {
                viewModel.loadBurgers()
            }
        }
    }

    taskListUIState?.let {
        when(it){
            ListUIState.Default -> {
                LaunchedEffect(it){
                    viewModel.loadBurgers()
                }
            }
            is ListUIState.BurgersLoaded -> {
                burgers.clear()
                burgers.addAll(it.burgers)

            }
        }
    }

    Scaffold(
        content = {
            ListScreenContent(navigation = navigation, burgers = burgers)
        },
        topBar = {
            TopAppBar(
                title = { Text("Your burgers") },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenContent(navigation: INavigationRouter,
                          burgers: List<Burger>) {

    LazyColumn(modifier = Modifier.testTag("BurgerTagList")
    ){
        burgers.forEach{
            item(key = it.id){
                BurgerRow(
                    burger = it,
                    onRowClick = {
                        navigation.navigateToDetail(it.id)
                                 Log.d("IDcko", it.id.toString())
                                 },
                )
            }
        }
    }
}

@Composable
fun BurgerRow(burger: Burger,
            onRowClick: () -> Unit) {

    Row (modifier = Modifier
        .fillMaxWidth()
        .clickable { onRowClick() }
        .testTag("Burger Row${burger.id}"),
        verticalAlignment = Alignment.CenterVertically)
    {
        Surface(
            shape = RoundedCornerShape(5),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
//                .border(1.dp, color = Color.DarkGray, shape = RoundedCornerShape(10))
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(painter = painterResource(id = R.drawable.burger_icon), contentDescription = "")

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        text = burger.name ?: "",
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Text(
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        text = "Average price 8.49€",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}