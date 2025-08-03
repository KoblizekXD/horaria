package dev.aa55h.horaria.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.aa55h.horaria.R
import dev.aa55h.horaria.utils.formatted
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimeChip(onConfirm: (LocalTime) -> Unit, time: LocalTime?) {
    val showTime = remember { mutableStateOf(false) }
    AssistChip(
        onClick = {
            showTime.value = true
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_schedule),
                contentDescription = "Clock Icon"
            )
        },
        label = {
            Text(time?.toString() ?: stringResource(R.string.chip_time_label))
        }
    )
    if (showTime.value) {
        TimePickerModal(
            onDismiss = {
                showTime.value = false
            },
            onConfirm = { time ->
                onConfirm(time)
                showTime.value = false
            }
        )
    }
}

@Composable
fun DateChip(onConfirm: (LocalDate) -> Unit, date: LocalDate?) {
    val showDate = remember { mutableStateOf(false) }
    AssistChip(
        onClick = {
            showDate.value = true
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_event),
                contentDescription = "Event Icon"
            )
        },
        label = {
            Text(if (date == null) stringResource(R.string.chip_date_label) else date.formatted()!!)
        }
    )
    if (showDate.value) {
        DatePickerModal(
            onDismiss = {
                showDate.value = false
            },
            onConfirm = { date ->
                onConfirm(date)
                showDate.value = false
            }
        )
    }
}