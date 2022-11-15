package com.dev.careby.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.careby.utils.Constants

@Composable
fun WaterScreen() {

        
        CircularProgressBar(percentage = 0.88f, number = 100 )


}

@Composable
fun CircularProgressBar (
    percentage: Float,
    number: Int,
    fontSize : TextUnit = 28.sp,
    radius: Dp = 100.dp,
    color: Color = Color.Blue,
    strokeWidth: Dp = 8.dp,
    animDuration : Int = 1000,
    animDelay : Int = 0
){
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState (
            targetValue = if(animationPlayed) percentage else 0f,
    animationSpec = tween (
        durationMillis = animDuration,
        delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            progressBar,
        progressText
        ) = createRefs()
        Box(
            modifier = Modifier.size(radius * 2f).constrainAs(progressBar){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 128.dp)
                top.linkTo(parent.top , margin = 100.dp)
            }

        ){
            Canvas(modifier = Modifier.size(radius * 2f)){
                drawArc(
                    color = color,
                    -90f,
                    360 * curPercentage.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)

                )
            }
        }

        Text(
            text =(curPercentage.value * number).toInt().toString() + "%",
            color = Color.Black,
            fontSize = fontSize,
            fontWeight =  FontWeight.Bold,
            modifier = Modifier.constrainAs(progressText){
                start.linkTo(progressBar.start, margin = 12.dp)
                end.linkTo(progressBar.end, margin = 0.dp)
                top.linkTo(progressBar.top, margin = 0.dp)
                bottom.linkTo(progressBar.bottom, margin = 0.dp)
            }

        )


    }

}

@Composable
@Preview
fun WaterScreenPreview() {
    WaterScreen()
}