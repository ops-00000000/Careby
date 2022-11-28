package com.dev.careby.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.layout.ModifierLocalPinnableParent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.careby.R
import com.dev.careby.utils.Constants

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
            Modifier.Companion.width(100.dp)
                .absoluteOffset(20.dp)
            )
        Image(
            painter = painterResource(id = R.drawable.authorization),
            contentDescription = "authorization",
            Modifier.Companion.width(100.dp)
                .absoluteOffset(300.dp,10.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "quotes_scree",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }

    }
}

@Composable
@Preview
fun QuotesScreenPreview() {
    QuotesScreen()
}