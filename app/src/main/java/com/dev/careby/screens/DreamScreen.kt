package com.dev.careby.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.Primary3
import com.dev.careby.ui.theme.Primary4
import com.dev.careby.ui.theme.SecondPrimary
import com.dev.careby.utils.AlarmReceiver
import com.dev.careby.utils.Constants
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.acos

@Composable
fun DreamScreen() {
    val contextForToast = LocalContext.current.applicationContext
    var pendingIntent:PendingIntent? = null
    val intent = Intent(contextForToast, AlarmReceiver::class.java)
pendingIntent = PendingIntent.getBroadcast(contextForToast,0,intent, 0)

    var alarmManager:AlarmManager = contextForToast.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }

    var time: Long

//    val formattedTime by remember {
//        derivedStateOf {
//            DateTimeFormatter
//                .ofPattern("hh:mm")
//                .format(pickedTime)
//        }
//    }

    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            timeDialogState.show()
        },
        Modifier.size(223.dp,128.dp),
        shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Primary)
        ) {
            Text(text = pickedTime.toString(), fontSize = 60.sp)
        }
        Text(text = "Поставить будильник")
    }

    val timeInMillis = System.currentTimeMillis()


    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = Primary,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    contextForToast,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Select time",
            timeRange = LocalTime.MIN..LocalTime.MAX,
            is24HourClock = true,
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = Primary4,
                inactiveBackgroundColor = Primary3,
                activeTextColor = Color.Black,
                inactiveTextColor = Color.Black,
                inactivePeriodBackground = Color.Blue,
                selectorColor = Color.Black,
                selectorTextColor = Primary3
            )
        ) {
            val hour = it.hour
            val minute = it.minute
            val timeSec = System.currentTimeMillis() + 5000
            pickedTime = it
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                timeSec,
                pendingIntent
            )
        }
    }
}

@Composable
@Preview
fun DreamScreenPreview() {
    WaterScreen()
}