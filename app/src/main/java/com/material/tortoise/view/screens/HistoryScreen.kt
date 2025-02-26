@file:OptIn(ExperimentalMaterial3Api::class)

package com.material.tortoise.view.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.material.tortoise.data.GoalHistory
import com.material.tortoise.view.components.HistoryDatePicker
import com.material.tortoise.view.theme.margin_half
import com.material.tortoise.view.theme.margin_standard
import com.material.tortoise.viewmodel.TortoiseViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * The Tortoise History List Screen
 */

@Composable
fun HistoryListBody(
    onItemClicked: (GoalHistory) -> Unit,
    historyItems: LiveData<List<GoalHistory>>?,
    viewModel: TortoiseViewModel,
    context: Context,
) {
    val userItems by viewModel.goalHistoryList.observeAsState(listOf())
    val openDialog = remember { mutableStateOf(false) }
    Column() {
        Column() {
            HistoryDatePicker(viewModel,context)
        }
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(items = userItems) { pres ->
                    HistoryListItem(
                        historyItem = pres,
                        onItemClicked = { },
                        modifier = Modifier.fillParentMaxWidth(),
                        viewModel
                    )
                }
            }

            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                },
                modifier = Modifier.padding(start = 300.dp, bottom = 30.dp),
                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteForever,
                    contentDescription = "Delete Full History",
                    tint = Color.Red
                )
            }
            deleteHistoryDialogBox(openDialog, viewModel)
        }

    }
}


@OptIn(coil.annotation.ExperimentalCoilApi::class)
@Composable
fun HistoryListItem(
    historyItem: GoalHistory,
    onItemClicked: (GoalHistory) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TortoiseViewModel,
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = margin_standard, vertical = margin_half),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(Modifier.width(margin_standard))
            Text(
                text = dateFormatWithWeekday(historyItem.dateCreated),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    Row(modifier = modifier
        .clickable { }
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = historyItem.goalName.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(margin_standard)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = historyItem.stepsCount.toString(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(margin_standard)
            )
            if (viewModel.isHistoryEditale()) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Steps in History",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            }
        }

        Text(
            text = historyItem.targetedSteps.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(margin_standard)
        )
    }

}

fun getCurrentDateTime(): Date {

    return Calendar.getInstance().time
}

fun getYesterdayDateTime(): Date {
    val mCalendar = Calendar.getInstance()
    mCalendar.add(Calendar.DATE, -1)
    return mCalendar.time
}


fun getDateBeforeYesterdayDateTime(): Date {
    val mCalendar = Calendar.getInstance()
    mCalendar.add(Calendar.DATE, -2)
    return mCalendar.time
}


fun dateFormatWithWeekday(dateToParse: Date?): String {
    val formatter = SimpleDateFormat("EEE, dd-MMM-yyyy")
    val formatted = formatter.format(dateToParse)
    return formatted.toString()

}

@Composable
fun deleteHistoryDialogBox(openDialog: MutableState<Boolean>, viewModel: TortoiseViewModel) {
    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Deleting all History")
            },
            text = {
                Text("This will permanently delete all of the History. Please confirm ?")
            },
            confirmButton = {
                Button(

                    onClick = {
                        viewModel.deleteAllGoalHistory()
                        openDialog.value = false
                    }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
