package com.mendelu.xmoric.burgermaster.ui.screens

import android.graphics.drawable.Icon
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

    val breadTypes = listOf("White", "Dark", "Rustical")
    val meatTypes = listOf("1/2", "1x", "2x", "3x")
    val sauceTypes = listOf("Ketchup", "Mustard", "Bacon")
    val extrasTypes = listOf("Lettuce", "Bacon", "Tomato", "Pickles")
    var description by rememberSaveable { mutableStateOf("") }


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
                .height(200.dp)
                .padding(start = 8.dp, end = 8.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(40),
                modifier = Modifier.padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green, //todo Pridaj dark green do Color.kt, tato je hnusna
                    contentColor = Color.White),
                content = { Text(text = "Save") }
            )
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(40),
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White),
                content = { Text(text = "Discard") }
            )
        }
        Button(
            onClick = { /*TODO*/ },
//            shape = RoundedCornerShape(40),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            content = { Text(text = "Select a restaurant") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(content: List<String>, header: String, checkboxes: Boolean, viewModel: CreationScreenViewModel): String{
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(content[1]) }
    var checked: Boolean = false
    var icon: Int = 0

    when (header) { //todo zmena na selectedOptionText a rozlisovat kazdu jednu volbu
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
            maxLines = 1,
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



