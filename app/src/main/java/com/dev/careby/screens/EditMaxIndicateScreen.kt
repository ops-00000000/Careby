package com.dev.careby.screens

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dev.careby.data.WaterScreenData
import com.dev.careby.dataBase.AppDb
import com.dev.careby.navigation.MainNavHost
import com.dev.careby.navigation.Screans
import com.dev.careby.repositories.WaterScreenRepoImpl
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.SecondPrimary


@Composable
fun EditMaxIndicateScreen(navController: NavController) {
    val rep = WaterScreenRepoImpl(
        AppDb.getInstance(context = LocalContext.current).WaterDao()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout {
            val (text, edittext, btn) = createRefs()
            Text(
                text = "Кажется вы не установили свой лимит воды!?",
                fontSize = MaterialTheme.typography.h5.fontSize,
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(edittext.top, margin = 12.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )
            var textV by remember {
                mutableStateOf(TextFieldValue(""))
            }

            OutlinedTextField(
                value = textV,
                onValueChange = { newValue -> textV = newValue },
                label = { Text(text = "Enter Max Indicate") },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .constrainAs(edittext) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(text.bottom, margin = 12.dp)
                        bottom.linkTo(btn.top, margin = 12.dp)
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(SecondPrimary)
            )

            Button(onClick =  {
                val dto = WaterScreenData(1L, 0, textV.text.toInt())
                rep.save(dto)
                navController.navigate(Screans.MainS.route)


            },
                shape = CircleShape,
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(btn) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(edittext.bottom, margin = 12.dp)
                        bottom.linkTo(parent.bottom)
                    }) {
                Text(text = "Add")
            }
        }

    }
}



@Composable
@Preview
fun EditMaxIndicateScreenPreview() {
    val navController = rememberNavController()
    EditMaxIndicateScreen(navController)
}
