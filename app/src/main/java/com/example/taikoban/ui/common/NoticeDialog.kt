package com.example.taikoban.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun NoticeDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    dialogTitle: String,
    dialogText: String,
    icon: Int,
    iconDescription: String = "Alert Icon",
    confirmButton: String = "Confirm",
    dismissButton: String = "Dismiss"
){
    AlertDialog(
        icon = {
            Image(
                painterResource(id = icon),
                contentDescription = (iconDescription),
                modifier = Modifier.height(64.dp)
            )
        },
        title = {
            Text(text= dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmButton)
            }
        },
        dismissButton = {
            if(!dismissButton.isNullOrBlank()) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(dismissButton)
                }
            }
        }

    )
}