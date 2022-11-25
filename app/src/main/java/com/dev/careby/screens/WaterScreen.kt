package com.dev.careby.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.careby.R
import com.dev.careby.dataBase.AppDb
import com.dev.careby.repositories.DateRepoImpl
import com.dev.careby.repositories.WaterScreenRepoImpl
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.SecondPrimary
import java.time.LocalDate

var dioValue = 0

@Composable
fun WaterScreen() {


    // https://www.youtube.com/watch?v=gQ8_Lo45Q6c


    val rep = WaterScreenRepoImpl(
        AppDb.getInstance(context = LocalContext.current).WaterDao()
    )
    val repD = DateRepoImpl(
        AppDb.getInstance(context = LocalContext.current).DateDao()
    )

    val date = repD.getDate(1L)

    if (date.localDate != LocalDate.now().toString()){
rep.updateCurIndicate(1L,0)
        repD.updateDate(1L,LocalDate.now().toString())
    }

    val data = rep.getData(1L)
    var indicate = data.curIndicate
    var maxIndicate1 = data.maxIndicate



    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        var value by remember {
            mutableStateOf(indicate)
        }

        var maxIndicate by remember {
            mutableStateOf(maxIndicate1)
        }

        var openDialog by remember {
            mutableStateOf(false)
        }

        if(openDialog){
             addDialog { value += dioValue; openDialog = false; rep.updateCurIndicate(1L, value) }

        }



        var (progressBar, Addbtn, Choosebtn) = createRefs()

        Box(modifier = Modifier.constrainAs(progressBar) {
            start.linkTo(parent.start, margin = 50.dp)
            end.linkTo(parent.end, margin = 50.dp)
            top.linkTo(parent.top, margin = 100.dp)
            bottom.linkTo(parent.bottom, margin = 128.dp)
        }) {
            CustomComponent(indicatorValue = value, maxIndicatorValue = maxIndicate)
        }
        Button(
            onClick = {
                value += 300; indicate += 300;
                rep.updateCurIndicate(1, indicate)
            },
            modifier = Modifier
                .constrainAs(Addbtn) {
                    start.linkTo(parent.start, margin = 50.dp)
                    end.linkTo(parent.end, margin = 50.dp)
                    top.linkTo(progressBar.bottom, margin = 0.dp)
                    bottom.linkTo(parent.bottom, margin = 200.dp)
                },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Primary)
        ) {
            Text(text = "Add 300ml")
        }
        FloatingActionButton(onClick = { openDialog = true },
            shape = CircleShape,
            contentColor = SecondPrimary,
            backgroundColor = Primary,
            modifier = Modifier.constrainAs(Choosebtn) {
                start.linkTo(Addbtn.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 28.dp)
                bottom.linkTo(parent.bottom, margin = 184.dp)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = "Add",
            )
        }

    }
}

@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int,
    maxIndicatorValue: Int,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 100f,
    foregroundIndicatorColor: Color = Color.Blue,
    foregroundIndicatorStrokeWidth: Float = 100f,
//    indicatorStrokeCap: StrokeCap = StrokeCap.Round,
    bigTextFontSize: TextUnit = MaterialTheme.typography.h3.fontSize,
    bigTextColor: Color = MaterialTheme.colors.onSurface,
    bigTextSuffix: String = "ML",
    smallText: String = "Воды выпито:",
    smallTextFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    smallTextColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
) {


    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage =
        (animatedIndicatorValue / maxIndicatorValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    val receivedValue by animateIntAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(1000)
    )

    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0)
            MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
        else
            bigTextColor,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
//                    indicatorStokeCap = indicatorStrokeCap
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
//                    indicatorStokeCap = indicatorStrokeCap
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbeddedElements(
            bigText = receivedValue,
            bigTextFontSize = bigTextFontSize,
            bigTextColor = animatedBigTextColor,
            bigTextSuffix = bigTextSuffix,
            smallText = smallText,
            smallTextColor = smallTextColor,
            smallTextFontSize = smallTextFontSize
        )
    }


}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
//    indicatorStokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
//    indicatorStokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Int,
    bigTextFontSize: TextUnit,
    bigTextColor: Color,
    bigTextSuffix: String,
    smallText: String,
    smallTextColor: Color,
    smallTextFontSize: TextUnit
) {
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )
    Text(
        text = "$bigText ${bigTextSuffix.take(2)}",
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun addDialog(onDismiss: () -> Unit){
    val contextForToast = LocalContext.current.applicationContext
    var pickerValue by remember { mutableStateOf("") }
    var value = 0
    Dialog(
        onDismissRequest = {
            onDismiss();
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 4.dp
        ) {



                Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                   OutlinedTextField(
                       value = pickerValue,
                       onValueChange ={ newValue -> pickerValue = newValue },
                       label = { Text(text = "Enter Add Water") },
                       modifier = Modifier
                           .padding(12.dp)
                           .fillMaxWidth(),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                       colors = TextFieldDefaults.textFieldColors(SecondPrimary)
                   )

                    val rep = WaterScreenRepoImpl(
                        AppDb.getInstance(context = LocalContext.current).WaterDao()
                    )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Primary),
                    onClick = {
                        value = pickerValue.toInt()
                        dioValue = value
//                 rep.updateCurIndicate(1, pickerValue.toInt())
                        onDismiss()
                        Toast.makeText(
                            contextForToast,
                            "Добавлено",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    Text(
                        text = "Добавить",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }

                TextButton(
                    onClick = {
                        pickerValue = "0"
                        onDismiss()
                    }) {
                    Text(
                        text = "Не сейчас",
                        color = Color(0xFF35898f),
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun WaterScreenPreview() {
    WaterScreen()
}