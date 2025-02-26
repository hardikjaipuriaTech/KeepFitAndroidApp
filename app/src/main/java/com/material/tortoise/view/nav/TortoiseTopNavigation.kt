package com.material.tortoise.view.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.material.tortoise.view.theme.margin_half


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TortoiseTopAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    TopAppBar(
        navigationIcon = {
            if (route.equals("daily")) {
                val iconToSet =
                    when (route) {
                        "daily" -> AppScreens.DailyStatus.icon
                        "list" -> AppScreens.MultipleGoals.icon
                        "history" -> AppScreens.History.icon
                        "settings" -> AppScreens.Settings.icon
                        else -> AppScreens.DailyStatus.icon
                    }
                Icon(
                    imageVector = iconToSet,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = margin_half),
                    tint = MaterialTheme.colorScheme.primary
                )
            } else if (route.equals("list") || route.equals("history") || route.equals("settings")) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back",tint = MaterialTheme.colorScheme.primary)
                }
            }
        }, actions = {
            IconButton(onClick = {
                navController.navigate("settings")
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings Screen to make goals editable and clear history"
                )
            }
        },
        title = {
            if (route.equals("daily")) {
                Text(
                    text = "Tortoise",
                    style = MaterialTheme.typography.headlineLarge
                )
            } else if (route.equals("list")) {
                Text(
                    text = "Goal Management",
                    style = MaterialTheme.typography.headlineLarge
                )
            } else if (route.equals("history")) {
                Text(
                    text = "History",
                    style = MaterialTheme.typography.headlineLarge
                )
            } else if (route.equals("settings")) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}



