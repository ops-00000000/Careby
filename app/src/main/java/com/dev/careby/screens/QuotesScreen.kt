package com.dev.careby.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.careby.R
import com.dev.careby.ui.theme.Typography
import com.dev.careby.ui.theme.fontCareby


@Composable
fun QuotesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    )  {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        )  {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            Modifier.Companion
                .width(150.dp)
                .absoluteOffset(20.dp,-20.dp)
            )
            Box(
                modifier = Modifier

            ){
            Image(
                painter = painterResource(id = R.drawable.authorization),
                 contentDescription = "authorization",
                Modifier.Companion
                    .width(115.dp)
                    .absoluteOffset(280.dp, 10.dp)
            )}
        }
        Box(
            modifier = Modifier
                .absoluteOffset(2.dp,-50.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "BELIEVE IN YOUR " +
                        "INFINITE POTENTIAL. " +
                        "YOUR ONLY " +
                        "LIMINATIONS ARE THOSE " +
                        "YOU     SET UPON YOURSELF.",
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.Black,
                fontFamily = fontCareby,
                modifier = Modifier.padding(12.dp)

            )
        }
        Box(
            modifier = Modifier
                .absoluteOffset(110.dp,50.dp),


        ){
            Text(
                text = "Roy T. Bennett",
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.Black,
                fontFamily = fontCareby
            )
        }

    }
}

@Composable
@Preview
fun QuotesScreenPreview() {
    QuotesScreen()
}




//файлы с шрифтом должны быть .xml