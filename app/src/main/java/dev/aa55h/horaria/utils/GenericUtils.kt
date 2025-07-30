package dev.aa55h.horaria.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun <T> rememberDebouncedState(
    value: T,
    debounceTimeMs: Long = 500L
): State<T> {
    var debouncedValue by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        delay(debounceTimeMs)
        debouncedValue = value
    }

    return rememberUpdatedState(debouncedValue)
}

@Composable
fun <T> rememberDebouncedState(
    value: T,
    debounceTimeMs: Long = 500L,
    onDebouncedValueChange: (T) -> Unit
): State<T> {
    val debouncedState = rememberDebouncedState(value, debounceTimeMs)
    LaunchedEffect(debouncedState.value) {
        onDebouncedValueChange(debouncedState.value)
    }
    return debouncedState
}