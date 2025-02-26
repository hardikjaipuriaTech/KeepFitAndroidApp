package com.material.tortoise.view.screens

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.material.tortoise.R
import com.material.tortoise.data.GoalHistory
import com.material.tortoise.data.Goals
import com.material.tortoise.view.components.FeaturedCircularProgressIndicator
import com.material.tortoise.view.theme.elevation
import com.material.tortoise.view.theme.margin_center
import com.material.tortoise.view.theme.margin_half
import com.material.tortoise.view.theme.margin_standard
import com.material.tortoise.viewmodel.TortoiseViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenBody(
    onLaunchClicked: (String) -> Unit,
    viewModel: TortoiseViewModel,
    context: Context,
) {

    val foundActiveGoal by viewModel.findActiveGoal().observeAsState()
    val history by viewModel.findGoalHistoryByDate(dateFormat(Date()))?.observeAsState()
    val activeGoal by viewModel.activeGoal.observeAsState()

    if (foundActiveGoal == null) {
        viewModel.addGoals(
            Goals(
                goalName = "Basic Goal",
                goalDescription = "Default Goal",
                stepsCount = 0,
                targetedSteps = 5000,
                statusFlag = true,
                dateCreated = dateFormat(Date()),
                updatedDate = dateFormat(Date())
            )
        )
    }
    var progressStepPercentage: Int? = null


    Surface() {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start,
                       verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Steps",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = margin_standard)
                    )
                    Spacer(Modifier.height(margin_standard))

                    Text(
                        text = activeGoal?.stepsCount.toString(),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(top = margin_standard),
                        textAlign = TextAlign.Center
                    )

                }
                Column(horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Target",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = margin_standard)
                    )
                    foundActiveGoal?.let {
                        Text(
                            text = it.targetedSteps.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(top = margin_standard)
                        )
                    }
                }
            }
            Spacer(Modifier.height(margin_half))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(margin_standard)
                    .fillMaxWidth()
            ) {
                foundActiveGoal?.let {
                    Text(
                        text = it.goalName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                foundActiveGoal?.let {
                    var progressSteps by remember {
                        mutableStateOf(0f) // initially 0f
                    }
                    progressSteps =
                        activeGoal?.stepsCount?.toFloat()?.div(it.targetedSteps?.toFloat()) ?: 1f
                    var progressStepscal =
                        (activeGoal?.stepsCount?.toFloat() ?: 0f) / it.targetedSteps

                    val progressAnimate by animateFloatAsState(
                        targetValue = progressSteps,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                    )
                    progressStepPercentage = (progressStepscal * 100).toInt()

                    BoxWithConstraints(
                        Modifier
                            .padding(start = margin_center)
                    ) {

                        val boxWidth = this.maxWidth
                        Box(
                            modifier = Modifier
                                .width(boxWidth - 10.dp)
                                .background(Color.Transparent)
                        ) {
                            FeaturedCircularProgressIndicator(progressAnimate)

                        }
                        Column() {
                            Spacer(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(10.dp)
                            )
                            Row() {
                                Spacer(modifier = Modifier.width(50.dp))
                                Box(
                                    modifier = Modifier
                                        .width(boxWidth)
                                        .zIndex(2f)
                                ) {
                                    Text(
                                        text = progressStepPercentage.toString() + "%",
                                        style = MaterialTheme.typography.displayMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(top = 45.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

            }

            Spacer(Modifier.height(margin_half))
            Column() {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = elevation
                    )
                ) {

                    var text by remember { mutableStateOf("") }
                    val focusManager = LocalFocusManager.current
                    val keyboardController = LocalSoftwareKeyboardController.current


                    OutlinedTextField(
                        value = text,
                        singleLine = true,
                        onValueChange = { text = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            storeStepCount(text, foundActiveGoal, viewModel, history)
                        }),
                        label = { Text(text = "Step Count") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.DirectionsWalk,
                                contentDescription = "Walk Icon"
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_steps),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.surfaceTint
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(margin_standard)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


fun storeStepCount(
    s: String,
    foundActiveGoal: Goals?,
    viewModel: TortoiseViewModel,
    history: GoalHistory?
) {
    foundActiveGoal.let {
        if (it != null) {
            it.stepsCount = it.stepsCount + s.toInt()
            viewModel.updateGoalDetails(it)
            var addHistory: Boolean = true

            history?.let { historyItem ->
                if (historyItem != null) {
                    historyItem.stepsCount = it.stepsCount
                    historyItem.goalName = it.goalName
                    historyItem.targetedSteps = it.targetedSteps
                    viewModel.updateGoalHistory(historyItem)
                    addHistory = false
                }
            }
            if (addHistory) {
                viewModel.addGoalHistory(
                    GoalHistory(
                        goalName = it.goalName,
                        stepsCount = it.stepsCount,
                        targetedSteps = it.targetedSteps,
                        statusFlag = true,
                        dateCreated = Date(),
                        updatedDate = Date(),
                        dateInString = dateFormat(Date())
                    )
                )
            }
        }
    }
}