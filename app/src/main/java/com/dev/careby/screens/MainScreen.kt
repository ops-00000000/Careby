package com.dev.careby.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev.careby.data.WaterScreenData
import com.dev.careby.dataBase.AppDb
import com.dev.careby.navigation.MainNavHost
import com.dev.careby.navigation.Screans
import com.dev.careby.repositories.WaterScreenRepoImpl
import com.dev.careby.ui.BottomBarObjects
import com.dev.careby.ui.theme.Primary
import com.dev.careby.ui.theme.SecondPrimary

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val rep = WaterScreenRepoImpl(
        AppDb.getInstance(context = LocalContext.current).WaterDao()
    )
    val dto = WaterScreenData(1L, 0, 2000)

    if (rep.getData(dto.id) == null) {
        MainNavHost(navController = navController, Screans.EditMaxS.route)
    } else {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            },
        ) {

            MainNavHost(navController = navController, Screans.WaterS.route)

        }
    }

}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarObjects.WaterS,
        BottomBarObjects.QuotesS,
        BottomBarObjects.DreamS,
        BottomBarObjects.FoodS,
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    ConstraintLayout() {

        val (
            bottombar
        ) = createRefs()

        Row(
            modifier = Modifier.height(56.dp)
                .constrainAs(bottombar) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .background(Primary)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarObjects,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) SecondPrimary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview
fun BottomNavPreview() {
    MainScreen()
}
