package dev.aa55h.horaria.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.aa55h.horaria.R
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_time_dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(LocalTime.of(
                timePickerState.hour,
                timePickerState.minute
            )) }) {
                Text(stringResource(R.string.dialog_time_done))
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
            )
        }
    )
}