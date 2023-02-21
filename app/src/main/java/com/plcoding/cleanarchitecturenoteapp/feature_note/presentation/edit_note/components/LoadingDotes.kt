package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.edit_note.components

import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingDots(
    color: Color,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember{
        mutableStateOf(false)
    }
    LaunchedEffect(Unit ){
        while(true){
            isVisible = !isVisible
            delay(200)
        }
    }
    AnimatedVisibility(visible = isVisible, color = color)
}





@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    label: String = "AnimatedVisibility",
    color:Color
){
    repeat(3){
        Box(
            modifier = modifier
                .clip(shape = CircleShape)
                .background(color = color)
                .width(4.dp)
        )
    }

}




@Composable
fun LoadingDot(){
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
        }
    }
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically, ){
            repeat(3) {
                Box(
                    modifier = Modifier.clip(shape = CircleShape).width(5.dp)
                        .background(Color.Black.copy(alpha = alpha)),
                )
            }

    }


}