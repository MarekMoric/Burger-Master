package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navigation: INavigationRouter) {

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = {Text("Name")}
            )

            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = {Text("Surname")}
            )
        }

    }
}