package com.material.tortoise.view.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.material.tortoise.view.theme.margin_standard
import com.material.tortoise.viewmodel.TortoiseViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDatePicker(viewModel: TortoiseViewModel, context: Context) {

    val mContext = LocalContext.current
    val year: Int
    val month: Int
    val day: Int
    val mCalendar = Calendar.getInstance()
    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    val date = remember { mutableStateOf("") }
    //mDate.value = "$mDay/${mMonth + 1}/$mYear"

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${month + 1}/$year"
        }, year, month, day
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(margin_standard).fillMaxWidth()) {
           // viewModel.historyDatePick = date.value
            /*OutlinedTextField(
                value = "${mDate.value}",
                onValueChange = { },
                maxLines = 1,
                readOnly = true
            )*/
            Button(
                onClick = {
                    mDatePickerDialog.show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Outlined.EditCalendar,
                    contentDescription = "Date Picker"
                )
            }
        }

        // Displaying the mDate value in the Text
        Text(
            text = "Showing History for ${date.value}",
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )
    }
}

