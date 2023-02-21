package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentHintTextFiend(
    modifier: Modifier = Modifier,
    text:String,
    hint:String,
    isHintVisible:Boolean = true,
    onValueChange:(String)->Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine:Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    isTextArea:Boolean = false
){
    Box(
       modifier = modifier
    ) {
        if(isTextArea){
            BasicTextField(
                value = text,
                onValueChange = {onValueChange(it)},
                singleLine = singleLine,
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .border(width = 1.dp, color = Color.Gray, shape =  RectangleShape,)
                    .onFocusChanged {
                        onFocusChange(it)
                    }
            )
            if(isHintVisible){
                Text(text = hint, style = textStyle, color = Color.DarkGray)
            }
        }else{
            BasicTextField(
                value = text,
                onValueChange = {onValueChange(it)},
                singleLine = singleLine,
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it)
                    }
            )
            if(isHintVisible){
                Text(text = hint, style = textStyle, color = Color.DarkGray)
            }
        }

    }
}