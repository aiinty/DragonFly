package com.aiinty.dragonfly.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aiinty.dragonfly.R
import com.aiinty.dragonfly.core.entity.User
import com.aiinty.dragonfly.ui.components.AnimateInvisibility
import com.aiinty.dragonfly.ui.components.BaseButton
import com.aiinty.dragonfly.ui.components.DefaultHeader
import com.aiinty.dragonfly.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

@Serializable
object Onboarding

enum class OnboardingScreenState{
    INIT,
    START,
    MEDIUM,
    END
}

private data class OnboardingItem(
    val titleId: Int?,
    val descId: Int?,
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    OnboardingScreen(
        user = User(),
        startState = OnboardingScreenState.START,
        onNavigateToNext = { }
    )
}

@Composable
fun OnboardingScreen(
    user: User,
    startState: OnboardingScreenState = OnboardingScreenState.INIT,
    onNavigateToNext: (User) -> Unit,
) {
    val items = mapOf(
        OnboardingScreenState.INIT to OnboardingItem(null, null),
        OnboardingScreenState.START to OnboardingItem(R.string.onboarding_first_title, R.string.onboarding_first_desc),
        OnboardingScreenState.MEDIUM to OnboardingItem(R.string.onboarding_second_title, R.string.onboarding_second_desc),
        OnboardingScreenState.END to OnboardingItem(R.string.onboarding_third_title, R.string.onboarding_second_desc),
    )
    val screenState = remember { mutableStateOf(startState) }
    val offsetY = remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 16.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        offsetY.floatValue += dragAmount.y
                    },
                    onDragEnd = {
                        if (offsetY.floatValue.absoluteValue >= 100) {
                            when {
                                //SWIPE DOWN
                                offsetY.floatValue < 0 -> {
                                    if (screenState.value != OnboardingScreenState.END)
                                        screenState.value =
                                            OnboardingScreenState.entries[screenState.value.ordinal + 1]
                                }
                                //SWIPE UP
                                offsetY.floatValue > 0 -> {
                                    if (screenState.value != OnboardingScreenState.START)
                                        screenState.value =
                                            OnboardingScreenState.entries[screenState.value.ordinal - 1]
                                }
                            }
                        }
                        offsetY.floatValue = 0f
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DefaultHeader()

        //ANIMATION
        val transition = updateTransition(targetState = screenState, label = "transition")
        val cardAngle = transition.animateFloat(transitionSpec = {
            tween(500)
        }, label = "cardAngle") { state ->
            when(state.value) {
                OnboardingScreenState.INIT -> 0f
                OnboardingScreenState.START -> 0f
                OnboardingScreenState.MEDIUM -> 12.83f
                else -> -90f + 12.83f
            }
        }
        val cardSize = transition.animateDp(transitionSpec = {
            tween(500)
        }, label = "cardSize") { animated ->
            when(animated.value) {
                OnboardingScreenState.INIT -> 250.dp
                OnboardingScreenState.START -> 250.dp
                OnboardingScreenState.MEDIUM -> 145.dp
                else -> 200.dp
            }
        }

        //CARDS ANIMATION BOX
        Box(
            modifier = Modifier.weight(5f),
            contentAlignment = Alignment.Center
        ) {
            AnimatedCard(
                imageId = R.drawable.credit_card4,
                size = cardSize,
                offset = transition.animateOffset(transitionSpec = {
                        tween(1000)
                    }, label = "offset1") { state ->
                    when(state.value) {
                        OnboardingScreenState.INIT -> Offset(-360f, 120f)
                        OnboardingScreenState.START -> Offset(0f, 0f)
                        OnboardingScreenState.MEDIUM -> Offset(55f, 100f)
                        else -> Offset(76f, 0f)
                    }
                },
                angle = cardAngle
            )

            //MEDIUM STATE
            this@Column.AnimatedVisibility(
                visible = screenState.value == OnboardingScreenState.MEDIUM,
                enter = scaleIn(tween(500)),
                exit = scaleOut() + fadeOut()
            ) {
                Image(
                    modifier = Modifier.offset(110.dp, (-30).dp),
                    imageVector = ImageVector.vectorResource(R.drawable.circular_line),
                    contentDescription = stringResource(R.string.line)
                )
                Image(
                    modifier = Modifier
                        .offset((-110).dp, 70.dp)
                        .rotate(-180f),
                    imageVector = ImageVector.vectorResource(R.drawable.circular_line),
                    contentDescription = stringResource(R.string.line)
                )

            }

            //END STATE
            this@Column.AnimatedVisibility(
                visible = screenState.value == OnboardingScreenState.END,
                enter = scaleIn(tween(500)),
                exit = scaleOut() + fadeOut()
            ) {
                AnimatedCard(
                    imageId = R.drawable.credit_card3,
                    size = cardSize,
                    offset = transition.animateOffset(transitionSpec = {
                            tween(1000)
                        }, label = "offset3") { animated ->
                        when(animated.value) {
                            OnboardingScreenState.MEDIUM -> Offset(-55f, -60f)
                            else -> Offset(-76f, 0f)
                        }
                    },
                    angle = cardAngle
                )
            }

            AnimatedCard(
                imageId = R.drawable.credit_card2,
                size = cardSize,
                offset = transition.animateOffset(transitionSpec = {
                    tween(1000)
                }, label = "offset2") { animated ->
                    when(animated.value) {
                        OnboardingScreenState.INIT -> Offset(-450f, 60f)
                        OnboardingScreenState.START -> Offset(-90f, -60f)
                        OnboardingScreenState.MEDIUM -> Offset(-55f, -60f)
                        else -> Offset(0f, 0f)
                    }
                },
                angle = cardAngle
            )
        }

        //ITEM DETAILS
        AnimatedContent(
            modifier = Modifier
                .padding(16.dp),
            targetState = screenState.value,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() togetherWith
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            },
            label = "itemsAnimation"
        ) { state ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                val item = items[state]
                item?.let {
                    ItemDetails(item.titleId, item.descId)
                }

                //START STATE HINT
                AnimateInvisibility(
                    visible = screenState.value == OnboardingScreenState.START,
                ) { modifier ->
                    Column(
                        modifier = modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.onboarding_swipe),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow),
                            contentDescription = stringResource(R.string.arrow)
                        )
                    }
                }
            }
        }

        BaseButton(
            onClick = { onNavigateToNext(user) }
        ) {
            Text(
                text = stringResource(R.string.onboarding_get_started),
                style = MaterialTheme.typography.labelSmall,
                color = Primary
            )
        }

        LaunchedEffect(Unit) {
            if (screenState.value == OnboardingScreenState.INIT) {
                delay(100)
                screenState.value = OnboardingScreenState.START
            }
        }
    }
}

@Composable
private fun AnimatedCard(
    imageId: Int,
    size: State<Dp>,
    angle: State<Float>,
    offset: State<Offset>,
    descId: Int? = null
    ) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .size(size.value)
            .offset(offset.value.x.dp, offset.value.y.dp)
            .rotate(angle.value),
        bitmap = ImageBitmap.imageResource(imageId),
        contentDescription = descId?.let { stringResource(descId) } ?: "Credit card"
    )
}

@Composable
private fun ItemDetails(
    titleId: Int?,
    descId: Int?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = titleId?.let { stringResource(titleId) } ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = descId?.let { stringResource(descId) } ?: "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
