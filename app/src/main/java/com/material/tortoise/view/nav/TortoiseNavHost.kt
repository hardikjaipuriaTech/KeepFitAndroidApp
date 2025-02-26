package com.material.tortoise.view.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.material.tortoise.viewmodel.TortoiseViewModel
import com.material.tortoise.view.screens.HistoryListBody
import com.material.tortoise.view.screens.HomeScreenBody
import com.material.tortoise.view.screens.GoalsListBody
import com.material.tortoise.view.screens.SettingsScreenBody

@ExperimentalMaterialNavigationApi
@Composable
fun TortoiseNavHost(
    navController: NavHostController,
    viewModel: TortoiseViewModel,
    modifier: Modifier = Modifier,
    context: Context,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.DailyStatus.route,
        modifier = modifier
    ) {
        composable(route = AppScreens.DailyStatus.route) {
            HomeScreenBody(
                onLaunchClicked = {
                    navController.navigate("${AppScreens.DailyStatus.route}$it")
                },
                viewModel,
                context
            )
        }
        composable(route = AppScreens.MultipleGoals.route) {
            GoalsListBody(
                onItemClicked = {
                    navController.navigate("${AppScreens.MultipleGoals.route}${it.goalName}")
                },
                viewModel.goalsList,
                viewModel,
                context
            )
        }

        composable(route = AppScreens.History.route) {
            HistoryListBody(
                onItemClicked = {
                    navController.navigate("${AppScreens.History.route}${it.goalName}")
                },
                viewModel.goalHistoryList,
                viewModel,
                context
            )

        }

        composable(route = "settings") {
            SettingsScreenBody(
                onItemClicked = { navController.navigate("settings") },
                viewModel = viewModel,
                context
            )
        }
    }
}
