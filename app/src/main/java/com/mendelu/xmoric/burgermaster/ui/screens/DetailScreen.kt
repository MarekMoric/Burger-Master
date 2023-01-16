package com.mendelu.xmoric.burgermaster.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import com.mendelu.xmoric.burgermaster.ui.elements.BackArrowScreen
import com.mendelu.xmoric.burgermaster.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel
import com.mendelu.xmoric.burgermaster.R.drawable

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(viewModel: DetailScreenViewModel,
                             burger: Burger,
                             burgerErrorMessage: String,
                             isNotLoading: Boolean,
                             navigation: INavigationRouter) {

    val breadTypes = listOf("White", "Dark", "Rustical")
    val meatTypes = listOf("1/2", "1x", "2x", "3x")
    val sauceTypes = listOf("Mustard", "Ketchup", "Bacon")
    val extrasTypes = listOf("Lettuce", "Grilled Bacon", "Tomato", "Pickles")
    var description by rememberSaveable { mutableStateOf("") }

    if(isNotLoading){
        Column {
            DropdownList(content = breadTypes, header = "Bread type", false, viewModel)
            Log.d("vypis bread", viewModel.bread)
            DropdownList(content = meatTypes, header = "Meat", false, viewModel)
            Log.d("vypis meat", viewModel.meat)
            DropdownList(content = sauceTypes, header = "Sauce", true, viewModel)
            Log.d("vypis sauce", viewModel.sauce.toString())
            DropdownList(content = extrasTypes, header = "Extras", true, viewModel)
            Log.d("vypis extras", viewModel.extras.toString())

            OutlinedTextField(
                value = description,
                onValueChange = { description = it},
                label = { Text(text = "Do you have any specials needs?")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navigation.navigateToNutrition() },
                    shape = RoundedCornerShape(40),
                    modifier = Modifier.padding(start = 8.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Green, //todo Pridaj dark green do Color.kt, tato je hnusna
//                        contentColor = Color.White),
                    content = { Text(text = "Nutrition Values") }
                )
                Button(
                    onClick = { navigation.navigateToAR() },
                    shape = RoundedCornerShape(40),
                    modifier = Modifier.padding(end = 8.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Red,
//                        contentColor = Color.White),
                    content = { Text(text = "3D Model") }
                )
            }
            Button(
                onClick = { navigation.navigateToMap() },
//            shape = RoundedCornerShape(40),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                content = { Text(text = "Select a restaurant") }
            )
        }

    }else{
        LoadingScreen()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownList(content: List<String>, header: String, checkboxes: Boolean, viewModel: DetailScreenViewModel): String{

    val vmData = when (header) {
        "Bread type" -> viewModel.burger.bread!!
        "Meat" -> viewModel.burger.meat!!
        "Sauce" -> viewModel.burger.sauce!!
        "Extras" -> viewModel.burger.extras!!
        else -> {
            ""
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(vmData) }
    var icon: Int = 0

    icon = when (selectedOptionText) { //todo vsetky moznosti v extras a sauce, retazene stringy
        "White" -> drawable.white_bun
        "Dark" -> drawable.dark_bun
        "Rustical" -> drawable.rustical_bun
        "1/2" -> drawable.meat
        "1x" -> drawable.meat
        "2x" -> drawable.meat2x
        "3x" -> drawable.meat3x
        "Mustard" -> drawable.mustard_sauce
        "Ketchup" -> drawable.ketchup
        "Bacon" -> drawable.bacon_sauce
        "Grilled Bacon" -> drawable.bacon
        else -> {
            drawable.bacon
        }
    }

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(header) },
            maxLines = 1,
            leadingIcon = { Image(
                painterResource(
                id = icon),
                contentDescription = "",
                modifier = Modifier.size(48.dp)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            content.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedOptionText = item
                        expanded = false
                    },
                    trailingIcon = {
                        if (checkboxes) {
                            Button(
                                enabled = !selectedOptionText.contains(item),
                                onClick = { selectedOptionText += ", $item" },
                                shape = RoundedCornerShape(40),
                                modifier = Modifier.padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.DarkGray),
                                content = { Text(text = "Add", style = MaterialTheme.typography.bodySmall) },
                            )
                        }
                    }
                )
            }
        }
    }
    when (header) { //todo zmena na selectedOptionText a rozlisovat kazdu jednu volbu
        "Bread type" -> viewModel.bread = selectedOptionText
        "Meat" -> viewModel.meat = selectedOptionText
        "Sauce" -> viewModel.sauce[0] = selectedOptionText
        "Extras" -> viewModel.extras[0] = selectedOptionText
        else -> {
            Text(text = "else branch")
        }
    }
    return selectedOptionText
}