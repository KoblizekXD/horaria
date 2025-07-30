package dev.aa55h.horaria.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.aa55h.horaria.R
import dev.aa55h.horaria.data.model.SearchAutocompleteResult

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    results: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit) = {},
) {
    Column(modifier = modifier) {
        SearchInput(
            leadingIcon = leadingIcon,
            placeholder = "Search...",
            onValueChange = onValueChange,
        )
        results()
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit) = {},
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    trailingIcon: @Composable (() -> Unit) = {},
) {
    val text = remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .bottomBorder(1.dp, MaterialTheme.colorScheme.outline)
            .padding(4.dp)
    ) {
        leadingIcon()
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            value = text.value,
            onValueChange = {
                text.value = it
                onValueChange(it)
            },
            placeholder = {
                Text(
                    text = placeholder,
                )
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(0.dp),
        )
        trailingIcon()
    }
}

@Preview()
@Composable
fun SearchBarPreview() {
    SearchBar(onValueChange = {}, results = {

        ListItem(headlineContent = {
            Text(text = "Sample Result")
        }, modifier = Modifier
            .semantics { isTraversalGroup = true },
            leadingContent = {
                Icon(
                    painterResource(R.drawable.ic_location_city),
                    contentDescription = "Address"
                )
            }
        )
    }, leadingIcon = {
        IconButton(onClick = { Log.d("SearchBarPreview", "Back clicked") }) {
            Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Back")
        }
    })
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)
