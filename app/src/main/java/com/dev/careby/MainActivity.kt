package com.dev.careby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.dev.careby.navigation.MainNavHost
import com.dev.careby.screens.MainScreen
import com.dev.careby.ui.theme.CarebyTheme
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.SecondPrimary
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarebyTheme {
                val systemUiController = rememberSystemUiController()
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setStatusBarColor(color = if (darkTheme) SecondPrimary else Color.White, true)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

