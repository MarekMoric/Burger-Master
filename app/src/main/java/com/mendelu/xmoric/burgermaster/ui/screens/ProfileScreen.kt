package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mendelu.xmoric.burgermaster.navigation.INavigationRouter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navigation: INavigationRouter) {
    
    var fName by rememberSaveable { mutableStateOf("") }
    var lName by rememberSaveable { mutableStateOf("") }
    var street by rememberSaveable { mutableStateOf("") }
    var zip by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var cardNumber by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(colorResource(id = R.color.vector_tint_theme_color))
            .wrapContentSize(Alignment.TopStart)
            .padding(8.dp)
    ) {
        Text(
            text = "Home address",
            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )
        Row() {
            TextField(
                value = fName,
                onValueChange = { fName = it},
                singleLine = true,
                label = { Text(text = "First name")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = lName,
                onValueChange = { lName = it},
                singleLine = true,
                label = { Text(text = "Last name")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(150.dp)
            )
        }
        TextField(
            value = street,
            onValueChange = { street = it},
            singleLine = true,
            label = { Text(text = "Street no.")},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Row() {
            TextField(
                value = zip,
                onValueChange = { zip = it},
                singleLine = true,
                label = { Text(text = "Zip")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = city,
                onValueChange = { city = it},
                singleLine = true,
                label = { Text(text = "City")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(150.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = state,
                onValueChange = { state = it},
                singleLine = true,
                label = { Text(text = "State")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(80.dp)
            )
        }
        BasicTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it.take(16) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = { creditCardFilter(it) }
        )
    }
}

fun creditCardFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text

    val mask = "1234  5678  1234  5678"

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i % 4 == 3 && i != 15) {
                append("  ")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 2
            if (offset <= 11) return offset + 4
            if (offset <= 16) return offset + 6
            return 22
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 2
            if (offset <= 14) return offset - 4
            if (offset <= 19) return offset - 6
            return 16
        }
    }

    return TransformedText(annotatedString, creditCardOffsetTranslator)
}


//reusable
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TextFieldComponent2(value: String, onValueChange: (String) -> Unit, placeholder: String) {
//    TextField(
//        value = value,
//        onValueChange = { textFieldValue -> onValueChange(textFieldValue) },
//        placeholder = { Text(placeholder, color = MaterialTheme.colors.secondary) }
//    )
//}

