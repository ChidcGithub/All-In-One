package com.allinone.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// M3 Expressive Shapes - 35 shape variants
// Core shapes used throughout the app
val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Extended shape library for M3 Expressive demo
object ExpressiveShapeLibrary {
    val None = RoundedCornerShape(0.dp)
    val ExtraSmallTop = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    val ExtraSmallBottom = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
    val SmallTop = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    val SmallBottom = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    val MediumTop = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    val MediumBottom = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
    val LargeTop = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val LargeBottom = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    val ExtraLargeTop = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val ExtraLargeBottom = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
    val Pill = RoundedCornerShape(50)
    val Circle = RoundedCornerShape(50)
    val CardDefault = RoundedCornerShape(12.dp)
    val DialogDefault = RoundedCornerShape(28.dp)
    val BottomSheetDefault = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val ChipDefault = RoundedCornerShape(16.dp)
    val TextFieldDefault = RoundedCornerShape(12.dp)
    val ButtonDefault = RoundedCornerShape(20.dp)
    val FABDefault = RoundedCornerShape(16.dp)
    val BannerDefault = RoundedCornerShape(0.dp)
    val NavigationDrawerDefault = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
    val SearchBarDefault = RoundedCornerShape(28.dp)
    val TooltipDefault = RoundedCornerShape(4.dp)
    val SnackbarDefault = RoundedCornerShape(12.dp)
    val BadgeDefault = RoundedCornerShape(50)
}
