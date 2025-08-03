@file:OptIn(ExperimentalMaterial3Api::class)

package dev.aa55h.horaria.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.aa55h.horaria.R
import dev.aa55h.horaria.utils.localDate
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDate = datePickerState.selectedDateMillis
                if (selectedDate != null)
                    onConfirm(localDate(selectedDate))
                else onDismiss()
            }) {
                Text(stringResource(R.string.dialog_date_done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialog_date_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}