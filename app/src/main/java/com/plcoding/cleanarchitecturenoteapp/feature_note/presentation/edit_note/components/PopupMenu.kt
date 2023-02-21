package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note.components

import android.widget.PopupMenu
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PopupMenu(
    menuItems:List<String>,
    onClickCallbacks: List<() -> Unit>,
    showMenu: Boolean,
    onDismiss: () -> Unit,
    toggle: @Composable () -> Unit,
){
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismiss() },
    ) {
        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                onDismiss()
                onClickCallbacks[index]
            }) {
                Text(text=item)
            }
        }
    }
}