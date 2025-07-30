package dev.aa55h.horaria.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.aa55h.horaria.R
import dev.aa55h.horaria.utils.formatted
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimeChip(setTime: MutableState<LocalTime?>) {
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
            Text(if (setTime.value == null) "Select Time" else setTime.value.toString())
        }
    )
    if (showTime.value) {
        TimePickerModal(
            onDismiss = {
                showTime.value = false
            },
            onConfirm = { date ->
                setTime.value = date
                showTime.value = false
            }
        )
    }
}

@Composable
fun DateChip(setDate: MutableState<LocalDate?>) {
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
            Text(if (setDate.value == null) "Select Date" else setDate.value?.formatted()!!)
        }
    )
    if (showDate.value) {
        DatePickerModal(
            onDismiss = {
                showDate.value = false
            },
            onConfirm = { date ->
                setDate.value = date
                showDate.value = false
            }
        )
    }
}