package com.guilhermealbm.sbcratingcalculator.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Green500 = Color(0xff4caf50)
val Green500Light = Color(0xff80e27e)
val Green500Dark = Color(0xff087f23)
val Green300 = Color(0xff81c784)
val Green300Light = Color(0xffb2fab4)
val Green300Dark = Color(0xff519657)
val Red800 = Color(0xffd00036)


val LightColors = lightColors(
    primary = Green500,
    primaryVariant = Green500Light,
    onPrimary = Color.White,
    secondary = Green300,
    secondaryVariant = Green300Light,
    onSecondary = Color.White,
    error = Red800
)

val Red200 = Color(0xfff297a2)

val DarkColors = darkColors(
    primary = Green500,
    primaryVariant = Green500Dark,
    onPrimary = Color.Black,
    secondary = Green300,
    secondaryVariant = Green300Dark,
    onSecondary = Color.Black,
    error = Red200
)
