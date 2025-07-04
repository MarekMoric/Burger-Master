package com.mendelu.xmoric.burgermaster.ui.screens

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.mendelu.xmoric.burgermaster.MainActivity
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import com.mendelu.xmoric.burgermaster.ui.elements.BackArrowScreen
import com.mendelu.xmoric.burgermaster.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel
import com.mendelu.xmoric.burgermaster.R.drawable
import com.mendelu.xmoric.burgermaster.ui.theme.LightBrown
import kotlin.coroutines.coroutineContext

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
            id = id,
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
                             id: Long?,
                             isNotLoading: Boolean,
                             navigation: INavigationRouter) {

    val breadTypes = listOf("White", "Dark", "Rustical")
    val meatTypes = listOf("1/2", "1x", "2x", "3x")
    val sauceTypes = listOf("Mustard", "Ketchup", "Bacon")
    val extrasTypes = listOf("Lettuce", "Grilled Bacon", "Tomato", "Pickles")
    var description by rememberSaveable { mutableStateOf("") }

    var context = LocalContext.current

    if(isNotLoading){
        Column {
            DropdownList(content = breadTypes, header = "Bread type", false, viewModel)
            DropdownList(content = meatTypes, header = "Meat", false, viewModel)
            DropdownList2(content = sauceTypes, header = "Sauce", true, viewModel)
            DropdownList3(content = extrasTypes, header = "Extras", true, viewModel)

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
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navigation.navigateToNutrition(id) },
                    shape = RoundedCornerShape(40),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .testTag("Nutrition Test"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBrown,
                        contentColor = Color.Black
                    ),
                    content = { Text(text = "Nutrition Values") }
                )
                Button(
                    onClick = {
                        context.startActivity(Intent(context, ARScreen::class.java))
                              },
                    shape = RoundedCornerShape(40),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .testTag("AR Test"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBrown,
                        contentColor = Color.Black
                    ),
                    content = { Text(text = "3D Model") }
                )
            }
            Button(
                onClick = { navigation.navigateToMap() },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("Map Test"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightBrown,
                    contentColor = Color.Black
                ),
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
            maxLines = 2,
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
    when (header) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownList2(content: List<String>, header: String, checkboxes: Boolean, viewModel: DetailScreenViewModel): String{

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

    icon = when (selectedOptionText) {
        "Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Mustard, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Mustard, Ketchup, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Mustard, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Mustard, Bacon, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Ketchup, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Ketchup, Bacon, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Ketchup, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Ketchup, Mustard, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Bacon, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Bacon, Ketchup, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Bacon, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Bacon, Mustard, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        else -> {
            com.mendelu.xmoric.burgermaster.R.drawable.ketchup
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
            maxLines = 2,
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
            Button(
                enabled = true,
                onClick = { selectedOptionText = "Ketchup" },
                shape = RoundedCornerShape(40),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray),
                content = { Text(text = "Clear", style = MaterialTheme.typography.bodySmall) })
        }
    }

    when (header) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownList3(content: List<String>, header: String, checkboxes: Boolean, viewModel: DetailScreenViewModel): String{

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

    icon = when (selectedOptionText) {
        "Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Mustard, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Mustard, Ketchup, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Mustard, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Mustard, Bacon, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Ketchup, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Ketchup, Bacon, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Ketchup, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Ketchup, Mustard, Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Bacon" -> com.mendelu.xmoric.burgermaster.R.drawable.bacon_sauce
        "Bacon, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        "Bacon, Ketchup, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Bacon, Mustard" -> com.mendelu.xmoric.burgermaster.R.drawable.mustard_sauce
        "Bacon, Mustard, Ketchup" -> com.mendelu.xmoric.burgermaster.R.drawable.ketchup
        else -> {
            com.mendelu.xmoric.burgermaster.R.drawable.ketchup
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
            maxLines = 2,
            leadingIcon = { Image(
                painterResource(
                    id = com.mendelu.xmoric.burgermaster.R.drawable.bacon),
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
            Button(
                enabled = true,
                onClick = { selectedOptionText = "Grilled Bacon" },
                shape = RoundedCornerShape(40),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray),
                content = { Text(text = "Clear", style = MaterialTheme.typography.bodySmall) })
        }
    }
    when (header) {
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