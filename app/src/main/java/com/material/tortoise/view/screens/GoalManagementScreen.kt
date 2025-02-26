package com.material.tortoise.view.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddTask
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.annotation.ExperimentalCoilApi
import com.material.tortoise.data.Goals
import com.material.tortoise.view.theme.amer_silver
import com.material.tortoise.view.theme.elevation
import com.material.tortoise.view.theme.margin_double
import com.material.tortoise.view.theme.margin_half
import com.material.tortoise.view.theme.md_theme_light_primaryContainer
import com.material.tortoise.viewmodel.TortoiseViewModel

/**
 * The Goal Management Screen
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun GoalsListBody(
    onItemClicked: (Goals) -> Unit,
    goalItems: LiveData<List<Goals>>,
    viewModel: TortoiseViewModel,
    context: Context,
) {

    val userItems by viewModel.goalsList.observeAsState(listOf())
    val goalsList by viewModel.allGoalList.observeAsState(listOf())
    Surface() {
        Column {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = margin_half)
            ) {
                items(items = userItems) { goals ->
                    GoalListItem(
                        goalItem = goals,
                        onItemClicked = { },
                        modifier = Modifier.fillParentMaxWidth(),
                        viewModel,
                        context
                    )
                }
            }

            val openAddGoalDialog = remember {
                mutableStateOf(false)
            }
            FloatingActionButton(
                onClick = {
                    openAddGoalDialog.value = true
                },
                modifier = Modifier.padding(start = 300.dp, bottom = 30.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Goals",
                    modifier = Modifier.padding(15.dp),
                    tint = Color.Green,
                )
            }
            addGoalDialogBox(openAddGoalDialog, viewModel)

        }
    }
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoalListItem(
    goalItem: Goals,
    onItemClicked: (Goals) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TortoiseViewModel,
    context: Context
) {

    val activeGoal by viewModel.activeGoal.observeAsState()
    val background = if (goalItem.statusFlag) md_theme_light_primaryContainer else amer_silver
    Card(
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    )
    {
        Row(
            modifier = modifier
                .background(background)
                .fillMaxWidth()
                .padding(horizontal = margin_double, vertical = margin_half),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {

                Text(
                    text = goalItem.goalName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = goalItem.targetedSteps.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(end = 10.dp)
                )

            }
            Column(horizontalAlignment = Alignment.End) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var switchOn by remember {
                        mutableStateOf(goalItem.statusFlag)
                    }
                    if (goalItem.statusFlag) {
                        Text(
                            text = "Active",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.labelMedium
                        )
                    } else {
                        Switch(
                            checked = switchOn, onCheckedChange = { switchOn_ ->
                                switchOn = switchOn_
                                if (switchOn_) {
                                    goalItem.statusFlag = switchOn_
                                    viewModel.updateGoalDetails(goalItem)
                                    activeGoal.let {
                                        it?.statusFlag = false
                                        if (it != null) {
                                            viewModel.updateGoalDetails(it)
                                        }
                                    }

                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Green,
                                checkedTrackColor = Color.Gray
                            )
                        )
                    }

                    val openEditGoalDialog = remember { mutableStateOf(false) }

                    if (viewModel.isGoalEditale()) {
                        IconButton({ openEditGoalDialog.value = true }) {
                            Icon(
                                Icons.Outlined.Edit, "Button for editing a Goal of the Goals list.",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        editGoalDialogBox(openEditGoalDialog, goalItem, viewModel)
                    }

                    val openDeleteDialog = remember { mutableStateOf(false) }
                    IconButton({ openDeleteDialog.value = true }) {
                        Icon(
                            Icons.Outlined.Delete, "Button for deleting a Goal of the Goals list.",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    deleteGoalDialogBox(openDeleteDialog, viewModel, goalItem)

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editGoalDialogBox(
    openEditGoalDialog: MutableState<Boolean>,
    goalItem: Goals,
    viewModel: TortoiseViewModel
) {
    if (openEditGoalDialog.value) {
        val inputvalue1 = remember { mutableStateOf(TextFieldValue(goalItem.goalName)) }
        val inputvalue2 =
            remember { mutableStateOf(TextFieldValue(goalItem.targetedSteps.toString())) }
        AlertDialog(
            onDismissRequest = {
                openEditGoalDialog.value = false
            },
            title = {
                Text(text = "Update Goal")
            },
            text = {
                Column(
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = 260.dp,
                                height = 120.dp,
                            )
                    ) {
                        OutlinedTextField(

                            value = inputvalue1.value,
                            onValueChange = { newGoalNameValue ->
                                inputvalue1.value = newGoalNameValue
                            },
                            maxLines = 1,
                            label = { Text(text = "Goal Name") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                imeAction = ImeAction.Next
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(
                                width = 260.dp,
                                height = 120.dp,
                            )
                    ) {

                        OutlinedTextField(
                            isError = false,
                            value = inputvalue2.value,
                            onValueChange = {
                                inputvalue2.value = it
                            },
                            placeholder = { Text(text = "Target Steps") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.AddTask,
                                    contentDescription = "Walk Icon"
                                )

                            },
                            maxLines = 1,
                            label = { Text(text = "Target steps") },
                            singleLine = true
                        )

                    }
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        goalItem.goalName = inputvalue1.value?.text
                        goalItem.targetedSteps = inputvalue2.value?.text?.toInt()
                        viewModel.updateGoalDetails(goalItem)
                        openEditGoalDialog.value = false
                    }) {
                    Text("Update")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openEditGoalDialog.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun deleteGoalDialogBox(
    openDeleteDialog: MutableState<Boolean>,
    viewModel: TortoiseViewModel,
    goalItem: Goals
) {
    if (openDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDeleteDialog.value = false
            },
            title = {
                Text(text = "Deleting Goal 1")
            },
            text = {
                Text("Are you sure you want to delete this goal ?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteGoals(goalItem)
                        openDeleteDialog.value = false
                    }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDeleteDialog.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun addGoalDialogBox(openAddGoalDialog: MutableState<Boolean>, viewModel: TortoiseViewModel) {
    val inputvalue1 = remember { mutableStateOf(TextFieldValue()) }
    val inputvalue2 = remember { mutableStateOf(TextFieldValue()) }
    if (openAddGoalDialog.value) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        AlertDialog(
            onDismissRequest = {
                openAddGoalDialog.value = false
            },
            title = {
                Text(text = "Add a new Goal")
            },
            text = {
                Column() {
                    Box(
                        modifier = Modifier
                            .size(
                                width = 260.dp,
                                height = 120.dp,
                            )
                    ) {
                        OutlinedTextField(
                            value = inputvalue1.value,
                            onValueChange = {
                                inputvalue1.value = it
                            },
                            placeholder = { Text(text = "Enter Goal Name") },
                            maxLines = 1,
                            label = { Text(text = "Goal Title") },
                            singleLine = true
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(
                                width = 260.dp,
                                height = 120.dp,
                            )
                    ) {
                        OutlinedTextField(
                            value = inputvalue2.value,
                            onValueChange = {
                                inputvalue2.value = it
                            },
                            placeholder = { Text(text = "Enter Target Steps") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.AddTask,
                                    contentDescription = "Walk Icon"
                                )

                            },
                            maxLines = 1,
                            label = { Text(text = "Target Steps") },
                            singleLine = true
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openAddGoalDialog.value = false
                        viewModel.addGoals(
                            Goals(
                                goalName = inputvalue1.value.text,
                                goalDescription = "",
                                statusFlag = false,
                                stepsCount = 0,
                                targetedSteps = inputvalue2.value.text.toInt(),
                                updatedDate = "",
                                dateCreated = ""
                            )
                        )
                    }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openAddGoalDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

private fun validateInput(
    title: String,
    steps: String,
): Pair<Boolean, String> {
    var message = ""
    if (title.isEmpty()) {
        message = "Please enter a name for the Goal"
        return Pair(false, message)
    }
    if (steps.isEmpty()) {
        message = "Please enter goals steps"
        return Pair(false, message)
    }
    return Pair(true, message)
}