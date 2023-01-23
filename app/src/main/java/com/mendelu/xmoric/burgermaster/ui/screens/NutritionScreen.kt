package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mendelu.xmoric.burgermaster.database.Burger
import com.mendelu.xmoric.burgermaster.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun NutritionScreen(navigation: INavigationRouter, id: Long?, viewModel: NutritionScreenViewModel = getViewModel()) {

    viewModel.burgerId = id

    val nutritionUIState: NutritionUIState? by viewModel.nutritionUIState.collectAsState()

    var burgerErrorMessage: String by rememberSaveable{ mutableStateOf("") }

    var burger: Burger by rememberSaveable { mutableStateOf(viewModel.burger) }

    var isNotLoading: Boolean by rememberSaveable { mutableStateOf(false) }

    nutritionUIState?.let {
        when(it){
            NutritionUIState.Default -> {
                LaunchedEffect(it){
                    viewModel.initBurger()
                }
            }
            is NutritionUIState.BurgerError -> {
                burgerErrorMessage = stringResource(id = it.error)
            }
            NutritionUIState.BurgerLoaded -> {
                burger = viewModel.burger
                isNotLoading = true
            }
        }
    }

    BackArrowScreen(topBarText = "Nutrition Values for " + viewModel.burger.name,
        content = { PieChart1(viewModel = viewModel) }) {
        navigation.returnBack()
    }


}

@Composable
fun PieChart1(
    colors: List<Color> = listOf(Color(0xFF58BDFF), Color(0xFF125B7F), Color(0xFF092D40), Color(0xFF092D30)),
    legend: List<String> = listOf("Carbohydrates", "Protein", "Fat"),
    size: Dp = 200.dp,
    viewModel: NutritionScreenViewModel
) {

    val values: MutableList<Float> = mutableListOf()

    when (viewModel.burger.meat) {
        "1/2" -> { viewModel.carbohydrates += 3; viewModel.protein += 10; viewModel.fat += 4.5F; viewModel.calories += 150}
        "1x" -> { viewModel.carbohydrates += 3*2; viewModel.protein += 10*2; viewModel.fat += 4.5F*2; viewModel.calories += 150*2}
        "2x" -> { viewModel.carbohydrates += 3*4; viewModel.protein += 10*4; viewModel.fat += 4.5F*4; viewModel.calories += 150*4}
        "3x" -> { viewModel.carbohydrates += 3*6; viewModel.protein += 10*6; viewModel.fat += 4.5F*6; viewModel.calories += 150*6}
        else -> {
            run { viewModel.carbohydrates += 3; viewModel.protein += 10; viewModel.fat += 11; viewModel.calories += 150}
        }
    }

    when (viewModel.burger.bread) {
        "Dark" -> { viewModel.carbohydrates += 23.3F; viewModel.protein += 6.45F; viewModel.fat += 2.28F; viewModel.calories += 70}
        "White" -> { viewModel.carbohydrates += 22; viewModel.protein += 6.1F; viewModel.fat += 2.05F; viewModel.calories += 65}
        "Rustical" -> { viewModel.carbohydrates += 24.2F; viewModel.protein += 6.98F; viewModel.fat += 2.4F; viewModel.calories += 75}
        else -> {
            run { viewModel.carbohydrates += 23.3F; viewModel.protein += 6.45F; viewModel.fat += 2.28F; viewModel.calories += 70}
        }
    }

    if (viewModel.burger.sauce?.contains("Ketchup") == true) {
         viewModel.carbohydrates += 2.51F; viewModel.protein += 0.17F; viewModel.fat += 0.04F; viewModel.calories += 10
    }
    if (viewModel.burger.sauce?.contains("Mustard") == true) {
        viewModel.carbohydrates += 0.39F; viewModel.protein += 0.2F; viewModel.fat += 0.16F; viewModel.calories += 3
    }
    if (viewModel.burger.sauce?.contains("Bacon") == true) {
        viewModel.carbohydrates += 1.7F; viewModel.protein += 0.18F; viewModel.fat += 0.5F; viewModel.calories += 15
    }

    if (viewModel.burger.extras?.contains("Grilled Bacon") == true) {
        viewModel.carbohydrates += 1; viewModel.protein += 6; viewModel.fat += 8; viewModel.calories += 90
    }
    if (viewModel.burger.extras?.contains("Lettuce") == true) {
        viewModel.carbohydrates += 1; viewModel.protein += 1; viewModel.fat += 0; viewModel.calories += 7.5F
    }
    if (viewModel.burger.extras?.contains("Pickles") == true) {
        viewModel.carbohydrates += 2.68F; viewModel.protein += 0.4F; viewModel.fat += 0.12F; viewModel.calories += 12
    }
    if (viewModel.burger.extras?.contains("Tomato") == true) {
        viewModel.carbohydrates += 2; viewModel.protein += 1; viewModel.fat += 0; viewModel.calories += 10
    }

    values.add(0 ,viewModel.carbohydrates)
    values.add(1 ,viewModel.protein)
    values.add(2 ,viewModel.fat)

    // Sum of all the values
    val sumOfValues = values.sum()

    // Calculate each proportion value
    val proportions = values.map {
        it * 100 / sumOfValues
    }

    // Convert each proportions to angle
    val sweepAngles = proportions.map {
        360 * it / 100
    }

    Canvas(
        modifier = Modifier
            .size(size = size)
//            .padding(start = 128.dp)
    ) {

        var startAngle = -90f

        for (i in sweepAngles.indices) {
            drawArc(
                color = colors[i],
                startAngle = startAngle,
                sweepAngle = sweepAngles[i],
                useCenter = true
            )
            startAngle += sweepAngles[i]
        }

    }

    Spacer(modifier = Modifier.height(32.dp))

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(16.dp),
                thickness = 4.dp,
                color = colors[0]
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = legend[0] + ": ${values[0].toInt()} grams",
                color = Color.Black
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(16.dp),
                thickness = 4.dp,
                color = colors[1]
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = legend[1] + ": ${values[1].toInt()} grams",
                color = Color.Black
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(16.dp),
                thickness = 4.dp,
                color = colors[2]
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = legend[2] + ": ${values[2].toInt()} grams",
                color = Color.Black
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(16.dp),
                thickness = 4.dp,
                color = colors[2]
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Total calories: ${viewModel.calories.toInt()} kcal",
                color = Color.Black
            )
        }
    }

}

