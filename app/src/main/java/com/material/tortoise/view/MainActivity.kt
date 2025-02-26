package com.material.tortoise.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.material.tortoise.view.nav.AppScreens
import com.material.tortoise.view.nav.TortoiseBottomNavigation
import com.material.tortoise.view.nav.TortoiseNavHost
import com.material.tortoise.view.nav.TortoiseTopAppBar
import com.material.tortoise.view.theme.ComposeTemplateTheme
import com.material.tortoise.viewmodel.TortoiseViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tortiseViewModel = TortoiseViewModel(
            (application as TortoiseApplication).repository,
            (application as TortoiseApplication).dataStorePrefRepo
        )
        setContent {
            TortiseApp(tortiseViewModel, this)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun TortiseApp(tortiseViewModel: TortoiseViewModel, context: Context) {
    ComposeTemplateTheme {
        val navScreens = listOf(
            AppScreens.DailyStatus,
            AppScreens.MultipleGoals,
            AppScreens.History
        )

        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                TortoiseBottomNavigation(navController = navController, items = navScreens)
            },
            topBar = {
                TortoiseTopAppBar(navController = navController)
            }
        ) { innerPadding ->
            TortoiseNavHost(
                navController = navController,
                tortiseViewModel,
                modifier = Modifier.padding(innerPadding),
                context
            )
        }
    }
}
