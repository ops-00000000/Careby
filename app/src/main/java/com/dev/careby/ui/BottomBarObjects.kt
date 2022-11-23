package com.dev.careby.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.dev.careby.R
import com.dev.careby.utils.Constants

sealed class BottomBarObjects(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {
    object WaterS : BottomBarObjects(
        route = Constants.Screens.WATER_BALANCE_SCREEN,
        title = "Water",
        icon = R.drawable.ic_outline_water_drop_24,
        icon_focused = R.drawable.ic_baseline_water_balance_24

    )

    object QuotesS : BottomBarObjects(
        route = Constants.Screens.QUOTES_SCREEN,
        title = "Quotes",
        icon = R.drawable.ic_outline_format_quote_24,
        icon_focused = R.drawable.ic_baseline_format_quote_24
    )

    object DreamS : BottomBarObjects(
        route = Constants.Screens.DREAM_SCREEN,
        title = "Dream",
        icon = R.drawable.ic_outline_bedtime_24,
        icon_focused = R.drawable.ic_baseline_bedtime_24
    )

    object FoodS : BottomBarObjects(
        route = Constants.Screens.FOOD_BALANCE_SCREEN,
        title = "Food",
        icon = R.drawable.ic_outline_fastfood_24,
        icon_focused = R.drawable.ic_baseline_fastfood_24
    )
}