package com.funrisestudio.mvimessenger.ui

import androidx.ui.material.Typography
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.sp

// Set of Material typography styles to start with
val typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
        ),
        body2 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = textColor
        ),
        caption = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = textColor
        )
)