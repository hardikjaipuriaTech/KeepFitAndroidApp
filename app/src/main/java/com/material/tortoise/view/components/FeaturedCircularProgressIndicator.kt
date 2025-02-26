package com.material.tortoise.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeaturedCircularProgressIndicator(progressSteps: Float) {
    CircularProgressIndicator(
        progress = progressSteps,
        modifier = Modifier
            .padding(15.dp)
            .size(128.dp)
            .fillMaxWidth(),
        color = ProgressIndicatorDefaults.circularColor,
        strokeWidth = 20.dp
    )
}
