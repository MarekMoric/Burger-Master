package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter

@Composable
fun DetailScreen(navigation: INavigationRouter) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.vector_tint_theme_color))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Burger Detail Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}