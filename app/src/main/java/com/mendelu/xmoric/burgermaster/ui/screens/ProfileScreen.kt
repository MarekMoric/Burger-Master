package com.mendelu.xmoric.burgermaster.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
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
    var expiration by remember { mutableStateOf("") }
    var securityCode by remember { mutableStateOf("") }

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
        Row {
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
                    .fillMaxWidth()
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
        Row {
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
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp))
        Text(
            text = "Financial information",
            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp))
        TextField(
            value = cardNumber,
            visualTransformation = CardNumberMask("-"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.length <= 16) cardNumber = it
            },
            label = { Text("Card number") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Row {
            TextField(
                value = expiration,
                visualTransformation = ExpirationDateMask(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.length <= 4) expiration = it
                },
                label = { Text("Expiry date") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(230.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = securityCode,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.length <= 3) securityCode = it
                },
                label = { Text("Security code") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

class ExpirationDateMask : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return makeExpirationFilter(text)
    }

    private fun makeExpirationFilter(text: AnnotatedString): TransformedText {
        // format: XX/XX
        val trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += "/"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

class CardNumberMask(private val separator: String = " ") : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return makeCardNumberFilter(text, separator)
    }

    private fun makeCardNumberFilter(text: AnnotatedString, separator: String): TransformedText {
        // format: XXXX XXXX XXXX XXXX by default
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 3 || i == 7 || i == 11) out += separator
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 3) offset
                else if (offset <= 7) offset + 1
                else if (offset <= 11) offset + 2
                else if (offset <= 16) offset + 3
                else 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 4) offset
                else if (offset <= 9) offset - 1
                else if (offset <= 14) offset - 2
                else if (offset <= 19) offset - 3
                else 16
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
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

