package com.example.mytasks.view.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePickerDialog(
    initialSelectedDateMillis: Long?,
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis ?: System.currentTimeMillis()
    )
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(datePickerState.selectedDateMillis)
            }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
