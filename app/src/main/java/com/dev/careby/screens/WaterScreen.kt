package com.dev.careby.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout



@Composable
fun WaterScreen() {

ConstraintLayout() {

    var value by remember {
        mutableStateOf(0)
    }

    var maxIndicate by remember {
        mutableStateOf(2000)
    }


    val (progressBar, Addbtn
    ) = createRefs()

        Box(modifier = Modifier.constrainAs(progressBar){
            start.linkTo(parent.start, margin = 50.dp)
            end.linkTo(parent.end, margin = 50.dp)
            top.linkTo(parent.top, margin = 100.dp)
            bottom.linkTo(parent.bottom, margin = 128.dp)
        }){
            CustomComponent(indicatorValue = value, maxIndicatorValue = maxIndicate )
        }
    Button(onClick = { value += 300 },
    modifier = Modifier.constrainAs(Addbtn){
        start.linkTo(parent.start, margin = 50.dp)
        end.linkTo(parent.end, margin = 50.dp)
        top.linkTo(progressBar.bottom, margin = 8.dp)
        bottom.linkTo(parent.bottom, margin = 120.dp)
    },
    shape = CircleShape) {
            Text(text = "Add 300ml")
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
@Preview
fun WaterScreenPreview() {
    WaterScreen()
}