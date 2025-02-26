package com.material.tortoise.view.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.material.tortoise.viewmodel.TortoiseViewModel
import com.material.tortoise.view.theme.margin_standard

@Composable
fun SettingsScreenBody(
    onItemClicked: (String) -> Unit,
    viewModel: TortoiseViewModel,
    context: Context
) {
    Column() {

        Spacer(modifier = Modifier.padding(margin_standard))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var goalEditable by remember {
                    mutableStateOf(true)
                }
                goalEditable = viewModel.isGoalEditale()
                Text(
                    text = "Turn Goal Editable",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(margin_standard))
                Switch(
                    checked = goalEditable,
                    onCheckedChange = { goalEditable_ ->
                        goalEditable = goalEditable_
                        viewModel.toggleGoalEditable()
                    },
                    modifier = Modifier.size(32.dp)
                )
                //Text(text = if (goalEditable) "ON" else "OFF")
            }
        }
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                var historyEditable by remember {
                    mutableStateOf(true)
                }

                historyEditable =  viewModel.isHistoryEditale()

                Text(
                    text = "Turn History Editable",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(margin_standard))
                Switch(
                    checked = historyEditable,
                    onCheckedChange = { historyEditable_ ->
                        historyEditable = historyEditable_
                        viewModel.toggleHistoryEditable()
                    },
                    modifier = Modifier.size(64.dp)
                )
               // Text(text = if (historyEditable) "ON" else "OFF")
            }
        }
    }
}
