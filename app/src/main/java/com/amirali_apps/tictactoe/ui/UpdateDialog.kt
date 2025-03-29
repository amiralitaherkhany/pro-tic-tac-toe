package com.amirali_apps.tictactoe.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun UpdateDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDownloadClick: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            title = {
                Text(
                    text = "New Update is Released",
                    style = MaterialTheme.typography.bodyLarge.plus(
                        TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                )
            },
            text = {
                Text(
                    text = "Update to Get New Features!",
                    style = MaterialTheme.typography.bodyMedium.plus(
                        TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                )
            },
            confirmButton = {
                Button(onClick = onDownloadClick) {
                    Text(
                        "Update",
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(
                        "Later",
                        style = MaterialTheme.typography.bodyMedium.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                }
            }
        )
    }
}