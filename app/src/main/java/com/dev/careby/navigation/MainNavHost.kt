package com.dev.careby.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.careby.screens.*
import com.dev.careby.utils.Constants


sealed class Screans(val route: String) {
    object WaterS : Screans(route = Constants.Screens.WATER_BALANCE_SCREEN)
    object QuotesS : Screans(route = Constants.Screens.QUOTES_SCREEN)
    object DreamS : Screans(route = Constants.Screens.DREAM_SCREEN)
    object FoodS : Screans(route = Constants.Screens.FOOD_BALANCE_SCREEN)
    object EditMaxS : Screans(route = Constants.Screens.EDIT_MAX_INDICATE_SCREEN)
    object MainS : Screans(route = Constants.Screens.MAIN_SCREEN)
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    startDestination:String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screans.WaterS.route) {
            WaterScreen()
        }
        composable(Screans.QuotesS.route) {
            QuotesScreen()
        }
        composable(Screans.DreamS.route) {
            DreamScreen()
        }
        composable(Screans.FoodS.route) {
            FoodScreen()
        }
        composable(Screans.EditMaxS.route){
            EditMaxIndicateScreen(
                navController
            )
        }
        composable(Screans.MainS.route){
            MainScreen()
        }
    }
}