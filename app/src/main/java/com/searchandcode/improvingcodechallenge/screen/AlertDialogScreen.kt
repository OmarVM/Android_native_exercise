package com.searchandcode.improvingcodechallenge.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AlertDialogScreen(
    onDismissRequest: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    onContentTypeSelected: (String) -> Unit,
    applyingFilters: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Settings")},
        text = {
            Column {
            SetOptions(
                onLanguageSelected = onLanguageSelected,
                onContentTypeSelected = onContentTypeSelected)
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    applyingFilters()
                    onDismissRequest()},
            ) { Text("Apply")}
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
            ) { Text("Cancel")}
        }
    )
}

@Composable
fun SetOptions(onLanguageSelected: (String) -> Unit,
               onContentTypeSelected: (String) -> Unit) {
    var selectedIndexLang by remember { mutableIntStateOf(0) }
    val languages = listOf("English", "Español")

    SingleChoiceSegmentedButtonRow {
        languages.forEachIndexed { index, string ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = languages.size
                ),
                onClick = { selectedIndexLang = index },
                selected = index == selectedIndexLang,
                label = { Text(string) }
            )
        }
    }

    onLanguageSelected(languages[selectedIndexLang])

    var selectedIndexContent by remember { mutableIntStateOf(0) }
    val content = listOf("Popular", "Top Rated")

    SingleChoiceSegmentedButtonRow {
        content.forEachIndexed { index, string ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = languages.size
                ),
                onClick = { selectedIndexContent = index },
                selected = index == selectedIndexContent,
                label = { Text(string) }
            )
        }
    }
    onContentTypeSelected(content[selectedIndexContent])
}