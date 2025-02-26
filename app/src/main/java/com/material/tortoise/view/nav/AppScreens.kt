package com.material.tortoise.view.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.AddTask
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.material.tortoise.R

sealed class AppScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object DailyStatus : AppScreens("daily", R.string.daily_status, Icons.Outlined.Home)
    object MultipleGoals : AppScreens("list", R.string.multiple_goals, Icons.Outlined.AddTask)
    object History : AppScreens("history", R.string.history, Icons.Outlined.History)
    object Settings : AppScreens("history", R.string.settings, Icons.Outlined.Settings)
}
