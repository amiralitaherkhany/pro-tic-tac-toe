package com.amirali_apps.tictactoe.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import com.amirali_apps.tictactoe.R

@Composable
fun UpdateDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDownloadClick: () -> Unit
) {
    if (showDialog) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            AlertDialog(
                onDismissRequest = onDismiss,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                title = {
                    Text(
                        text = stringResource(R.string.new_update_is_released),
                        style = MaterialTheme.typography.bodyLarge.plus(
                            TextStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.update_to_get_new_features),
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
                            stringResource(R.string.update),
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
                            stringResource(R.string.later),
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
}