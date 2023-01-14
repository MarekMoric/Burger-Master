package com.mendelu.xmoric.burgermaster.ui.screens

import android.graphics.drawable.Icon
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.decode.ImageSource
import com.mendelu.xmoric.burgermaster.R
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(navigation: INavigationRouter) {
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

    val breadTypes = listOf("White", "Dark", "Rustical")
    val meatTypes = listOf("1/2", "1x", "2x", "3x")
    val sauceTypes = listOf("Ketchup", "Mustard", "Bacon")
    val extrasTypes = listOf("Lettuce", "Bacon", "Tomato", "Pickles")
    var description by rememberSaveable { mutableStateOf("") }


    Column {
        DropdownList(content = breadTypes, header = "Bread type", false)
        DropdownList(content = meatTypes, header = "Meat", false)
        DropdownList(content = sauceTypes, header = "Sauce", true)
        DropdownList(content = extrasTypes, header = "Extras", true)
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
                .height(200.dp)
                .padding(start = 8.dp, end = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(content: List<String>, header: String, checkboxes: Boolean){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(content[1]) }
    var checked: Boolean = false
    var icon: Int = 0

    when (header) {
        "Bread type" -> icon = R.drawable.burger_meat
        "Meat" -> icon =  R.drawable.burger_meat
        "Sauce" -> icon =  R.drawable.sauce
        "Extras" -> icon =  R.drawable.bacon
        else -> {
            icon =  R.drawable.bacon
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
//                        Checkbox(checked = checked, onCheckedChange = { selectedOptionText += ", $item"; checked = true }) }
                        Text("Add Extra", style = MaterialTheme.typography.bodySmall) } //vymen za button, mas tam totizto onlick ktory potrebujes
                     }
                )
            }
        }
    }
}



