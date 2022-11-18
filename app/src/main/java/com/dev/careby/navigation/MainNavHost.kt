package com.dev.careby.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.careby.screens.DreamScreen
import com.dev.careby.screens.FoodScreen
import com.dev.careby.screens.QuotesScreen
import com.dev.careby.screens.WaterScreen
import com.dev.careby.utils.Constants


sealed class Screans(val route: String) {
    object WaterS : Screans(route = Constants.Screens.WATER_BALANCE_SCREEN)
    object FoodS : Screans(route = Constants.Screens.FOOD_BALANCE_SCREEN)
    object QuotesS : Screans(route = Constants.Screens.QUOTES_SCREEN)
    object DreamS : Screans(route = Constants.Screens.DREAM_SCREEN)
}

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screans.WaterS.route
    ) {
        composable(route = Screans.WaterS.route) {
            WaterScreen()
        }
        composable(Screans.FoodS.route) {
            FoodScreen()
        }
        composable(Screans.QuotesS.route) {
            QuotesScreen()
        }
        composable(Screans.DreamS.route) {
            DreamScreen()
        }
    }
}