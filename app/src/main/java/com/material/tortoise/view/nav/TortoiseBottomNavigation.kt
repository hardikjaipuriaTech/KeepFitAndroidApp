package com.material.tortoise.view.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TortoiseBottomNavigation(
    navController: NavHostController, items: List<AppScreens>
) {
    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val route = navBackStackEntry?.destination
        items.forEach { screen ->
            val isSelected: Boolean = route?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(icon = {
                Icon(
                    screen.icon, contentDescription = screen.resourceId.toString()
                )
            },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = isSelected,
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    // If on current screen, ignore button press to avoid redraw
                    if (screen.route != route?.route) {
                        navController.navigate(screen.route)
                    }
                })
        }
    }
}