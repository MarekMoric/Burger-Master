package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import com.mendelu.xmoric.burgermaster.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailScreen(navigation: INavigationRouter,
                 viewModel: DetailScreenViewModel = getViewModel(),
                 id: Long?) {

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "Burger Detail Screen",
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )
//    }

    viewModel.burgerId = id

    val detailUIState: DetailUIState? by viewModel.detailUIState.collectAsState()

    var burgerErrorMessage: String by rememberSaveable{ mutableStateOf("") }

    var burger: Burger by rememberSaveable { mutableStateOf(viewModel.burger) }

    var isNotLoading: Boolean by rememberSaveable { mutableStateOf(false) }

    detailUIState?.let {
        when(it){
            DetailUIState.Default -> {
                LaunchedEffect(it){
                    viewModel.initBurger()
                }
            }
            is DetailUIState.BurgerError -> {
                burgerErrorMessage = stringResource(id = it.error)
            }
            DetailUIState.BurgerLoaded -> {
                burger = viewModel.burger
                isNotLoading = true
            }
            DetailUIState.BurgerRemoved -> {
                LaunchedEffect(it){ navigation.returnBack() }
            }
            DetailUIState.BurgerSaved -> {
                LaunchedEffect(it){ navigation.returnBack() }
            }
        }
    }

    BackArrowScreen(topBarText = burger.name!!,
        content = {  DetailScreenContent(
            viewModel = viewModel,
            burger = burger,
            burgerErrorMessage = burgerErrorMessage,
            isNotLoading = isNotLoading,
            navigation = navigation)},
        onBackClick = {navigation.returnBack()},
        actions = {
            if (viewModel.burgerId != null){
                IconButton(onClick = { viewModel.deleteBurger() }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        })
}

@Composable
fun DetailScreenContent(viewModel: DetailScreenViewModel,
                             burger: Burger,
                             burgerErrorMessage: String,
                             isNotLoading: Boolean,
                             navigation: INavigationRouter) {

    if(isNotLoading){


    }else{
        //loading animacie etc
    }


}