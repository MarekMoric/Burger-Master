package com.mendelu.xmoric.burgermaster.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mendelu.xmoric.burgermaster.R
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import com.mendelu.xmoric.burgermaster.ui.theme.LightBrown
import com.mendelu.xmoric.burgermaster.ui.theme.LightGreen
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(navigation: INavigationRouter,
                   viewModel: CreationScreenViewModel = getViewModel()) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "Burger Creation Screen",
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp
//        )

    val creationScreenUIState: CreationScreenUIState? by viewModel._creationScreenUIState.collectAsState()

    var burgerErrorMessage: String by rememberSaveable{ mutableStateOf("")}

    creationScreenUIState?.let {
        when(it){
            CreationScreenUIState.Default -> {

            }
            is CreationScreenUIState.BurgerError -> {
                burgerErrorMessage = stringResource(id = it.error)
            }
            CreationScreenUIState.BurgerSaved -> {
                LaunchedEffect(it){navigation.navigateToList()}
            }
        }
    }

    val breadTypes = listOf("White", "Dark", "Rustical")
    val meatTypes = listOf("1/2", "1x", "2x", "3x")
    val sauceTypes = listOf("Mustard", "Ketchup", "Bacon")
    val extrasTypes = listOf("Lettuce", "Grilled Bacon", "Tomato", "Pickles")
    var description by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf(viewModel.name) }


    Column {
        TopAppBar(
            title = { Text("Create your burger") },
            actions = {
                Button(
                    onClick = { viewModel.saveBurger() },
                    shape = RoundedCornerShape(100),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .testTag("Save Test"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = Color.DarkGray),
                    content = { Text(text = "Save", style = MaterialTheme.typography.bodySmall) },
                )
            }
        )
        TextField(
            value = name,
            onValueChange = {
                name = it
                viewModel.name = name
                            },
            singleLine = true,
            label = { Text(text = "Name your burger")},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        )

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
                .testTag("Creation needs")
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
        }
        Button(
            onClick = { navigation.navigateToMap() },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBrown,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .testTag("Map Test"),
            content = { Text(text = "Select a restaurant") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownList(content: List<String>, header: String, checkboxes: Boolean, viewModel: CreationScreenViewModel): String{
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(content[1]) }
    var checked: Boolean = false
    var icon: Int = 0

    icon = when (selectedOptionText) { //todo vsetky moznosti v extras a sauce, retazene stringy
        "White" -> R.drawable.white_bun
        "Dark" -> R.drawable.dark_bun
        "Rustical" -> R.drawable.rustical_bun
        "1/2" -> R.drawable.meat
        "1x" -> R.drawable.meat
        "2x" -> R.drawable.meat2x
        "3x" -> R.drawable.meat3x
        "Mustard" -> R.drawable.mustard_sauce
        "Ketchup" -> R.drawable.ketchup
        "Bacon" -> R.drawable.bacon_sauce
        "Grilled Bacon" -> R.drawable.bacon
        else -> {
            R.drawable.bacon
        }
    }

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(16.dp).testTag("Icon test"),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(header) },
            maxLines = 2,
            leadingIcon = { Image(painterResource(
                id = icon),
                contentDescription = "",
                modifier = Modifier.size(48.dp).testTag("Icon test")) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .testTag("Icon test"),
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
private fun DropdownList2(content: List<String>, header: String, checkboxes: Boolean, viewModel: CreationScreenViewModel): String{
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(content[1]) }
    var checked: Boolean = false
    var icon: Int = 0

    icon = when (selectedOptionText) {
        "Mustard" -> R.drawable.mustard_sauce
        "Mustard, Ketchup" -> R.drawable.ketchup
        "Mustard, Ketchup, Bacon" -> R.drawable.bacon_sauce
        "Mustard, Bacon" -> R.drawable.bacon_sauce
        "Mustard, Bacon, Ketchup" -> R.drawable.ketchup
        "Ketchup" -> R.drawable.ketchup
        "Ketchup, Bacon" -> R.drawable.bacon_sauce
        "Ketchup, Bacon, Mustard" -> R.drawable.mustard_sauce
        "Ketchup, Mustard" -> R.drawable.mustard_sauce
        "Ketchup, Mustard, Bacon" -> R.drawable.bacon_sauce
        "Bacon" -> R.drawable.bacon_sauce
        "Bacon, Ketchup" -> R.drawable.ketchup
        "Bacon, Ketchup, Mustard" -> R.drawable.mustard_sauce
        "Bacon, Mustard" -> R.drawable.mustard_sauce
        "Bacon, Mustard, Ketchup" -> R.drawable.ketchup
        else -> {
            R.drawable.ketchup
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
            leadingIcon = { Image(painterResource(
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
private fun DropdownList3(content: List<String>, header: String, checkboxes: Boolean, viewModel: CreationScreenViewModel): String{
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(content[1]) }
    var checked: Boolean = false
    var icon: Int = 0

    icon = when (selectedOptionText) {
        "Mustard" -> R.drawable.mustard_sauce
        "Mustard, Ketchup" -> R.drawable.ketchup
        "Mustard, Ketchup, Bacon" -> R.drawable.bacon_sauce
        "Mustard, Bacon" -> R.drawable.bacon_sauce
        "Mustard, Bacon, Ketchup" -> R.drawable.ketchup
        "Ketchup" -> R.drawable.ketchup
        "Ketchup, Bacon" -> R.drawable.bacon_sauce
        "Ketchup, Bacon, Mustard" -> R.drawable.mustard_sauce
        "Ketchup, Mustard" -> R.drawable.mustard_sauce
        "Ketchup, Mustard, Bacon" -> R.drawable.bacon_sauce
        "Bacon" -> R.drawable.bacon_sauce
        "Bacon, Ketchup" -> R.drawable.ketchup
        "Bacon, Ketchup, Mustard" -> R.drawable.mustard_sauce
        "Bacon, Mustard" -> R.drawable.mustard_sauce
        "Bacon, Mustard, Ketchup" -> R.drawable.ketchup
        else -> {
            R.drawable.ketchup
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
            leadingIcon = { Image(painterResource(
                id = R.drawable.bacon),
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



