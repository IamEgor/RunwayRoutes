package com.runway.routes.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(textValue: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = textValue,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            val hasText = textValue != TextFieldValue("")
            val icon = if (hasText) Icons.Default.Close else Icons.Default.Search
            IconButton(
                onClick = {
                    if (hasText) {
                        onValueChange(TextFieldValue(""))
                    }
                },
                content = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                })
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState) { textState = it }
}