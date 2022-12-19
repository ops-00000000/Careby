package com.dev.careby.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.Primary3
import com.dev.careby.ui.theme.Primary4
import com.dev.careby.utils.AlarmReceiver
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context.ALARM_SERVICE
import android.content.SharedPreferences
import android.icu.number.Precision.integer
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.navigation.NavController
import com.dev.careby.data.AlarmData
import com.dev.careby.dataBase.AppDb
import com.dev.careby.repositories.AlarmRepoImpl
//import io.ak1.jetalarm.data.viewmodels.AlarmViewModel
import org.koin.java.KoinJavaComponent.inject
import java.lang.Integer.getInteger
import java.time.LocalDate
import java.util.*


@Composable
fun DreamScreen() {
    val contextForToast = LocalContext.current.applicationContext
    val context = LocalContext.current
    val c: Calendar = Calendar.getInstance()
    val hour: Int = c.get(Calendar.HOUR_OF_DAY)
    val minute: Int = c.get(Calendar.MINUTE)
    val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    var time = remember {
        mutableStateOf("0")
    }

    val rep = AlarmRepoImpl(
        AppDb.getInstance(context = context).AlarmDao()
    )

    var lastId = 1L
    while (rep.getData(lastId) != null){
        lastId++
    }
    if (rep.getData(lastId-1) != null && rep.getData(lastId - 1).data == LocalDate.now().toString()){
        time.value = rep.getData(lastId - 1).time
    }
    else{
        time.value = "set Alarm"
    }



    var alarmData: AlarmData

//    if (rep.getData(0) == null) {
//
//
//        if (hour < 10 && minute < 10) {
//            time.value = "0$hour:0$minute"
//            alarmData = AlarmData(lastId, "0$hour:0$minute", LocalDate.now().toString())
//            rep.saveTime(alarmData)
//            lastId++
//        }
//        if (hour < 10) {
//            time.value = "0$hour:$minute"
//            alarmData = AlarmData(lastId, "0$hour:$minute", LocalDate.now().toString())
//            rep.saveTime(alarmData)
//            lastId++
//        }
//        if (minute < 10) {
//            time.value = "$hour:0$minute"
//            alarmData = AlarmData(lastId, "$hour:0$minute", LocalDate.now().toString())
//            rep.saveTime(alarmData)
//            lastId++
//        }
//        if (hour > 10 && minute > 10) {
//            time.value = "$hour:$minute"
//            alarmData = AlarmData(lastId, "$hour:0$minute", LocalDate.now().toString())
//            rep.saveTime(alarmData)
//            lastId++
//
//        }
//    }


    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                timeDialogState.show()
            },
            Modifier.size(223.dp, 128.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Primary)
        ) {
            Text(text = time.value.toString(), fontSize = 60.sp)
        }
        Text(text = "Поставить будильник")
    }





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
        },
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
            val hourOfDay = it.hour
            val min = it.minute
            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
            c.set(Calendar.MINUTE, min)
            alarmData = AlarmData(lastId, "$hourOfDay:$min", LocalDate.now().toString())

            time.value = "$hourOfDay:$min"

            if (hourOfDay < 10 && min < 10) {
                time.value = "0$hourOfDay:0$min"
                alarmData = AlarmData(lastId, "0$hourOfDay:0$min", LocalDate.now().toString())
            }
            if (hourOfDay < 10) {
                time.value = "0$hourOfDay:$min"
                alarmData = AlarmData(lastId, "0$hourOfDay:$min", LocalDate.now().toString())
            }
            if (min < 10) {
                time.value = "$hourOfDay:0$min"
                alarmData = AlarmData(lastId, "$hourOfDay:0$min", LocalDate.now().toString())
            }

            val intent = Intent(context, AlarmReceiver::class.java)
            val requestCode = 991
            var pendingIntent = PendingIntent.getBroadcast(contextForToast, 0, intent, 0)


            var times = c.timeInMillis - c.timeInMillis % 60000
            Log.e(
                "compare",
                "${System.currentTimeMillis()}  $times     difference = ${System.currentTimeMillis()}"
            )
            if (System.currentTimeMillis() > times) {
                times += 1000 * 60 * 60 * 24
            }
//            viewModel.saveNewAlarm(c.time.time, "desc", requestCode)
            lastId++
            rep.saveTime(alarmData)


            alarmManager.set(AlarmManager.RTC_WAKEUP, times, pendingIntent)


            Log.e("alarmManager", "${alarmManager.nextAlarmClock}")
        }
    }
}

@Composable
@Preview
fun DreamScreenPreview() {
    WaterScreen()
}