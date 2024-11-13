package com.aiinty.dragonfly.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aiinty.dragonfly.R

@OptIn(ExperimentalTextApi::class)
private val default = FontFamily(
    Font(
        R.font.montserrat,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400),
        )
    ),
    Font(
        R.font.montserrat,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500),
        )
    ),
)


val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp,
        color = Secondary
    ),
    titleSmall = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = Secondary
    ),
    bodySmall = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = Tertiary
    ),
    //BUTTONS, ETC
    labelLarge = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp,
        color = Secondary
    ),
    labelMedium = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Secondary
    ),
    labelSmall = TextStyle(
        fontFamily = default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp,
        color = Secondary
    ),
)