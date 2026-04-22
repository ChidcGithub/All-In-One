package com.allinone.core.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// M3 Expressive Shapes - Core shapes used throughout the app
val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Extended shape library - 35+ shape variants for M3 Expressive
object ExpressiveShapeLibrary {
    // Basic shapes
    val None = RoundedCornerShape(0.dp)
    val Pill = RoundedCornerShape(50)
    val Circle = RoundedCornerShape(50)

    // Corner size variants (5 sizes x 2 styles = 10)
    val ExtraSmall = RoundedCornerShape(4.dp)
    val Small = RoundedCornerShape(8.dp)
    val Medium = RoundedCornerShape(12.dp)
    val Large = RoundedCornerShape(16.dp)
    val ExtraLarge = RoundedCornerShape(28.dp)

    // Cut corner variants (5)
    val CutExtraSmall = CutCornerShape(4.dp)
    val CutSmall = CutCornerShape(8.dp)
    val CutMedium = CutCornerShape(12.dp)
    val CutLarge = CutCornerShape(16.dp)
    val CutExtraLarge = CutCornerShape(28.dp)

    // Top-only rounded (5)
    val ExtraSmallTop = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    val SmallTop = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    val MediumTop = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    val LargeTop = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val ExtraLargeTop = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

    // Bottom-only rounded (5)
    val ExtraSmallBottom = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
    val SmallBottom = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
    val MediumBottom = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
    val LargeBottom = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    val ExtraLargeBottom = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)

    // Component-specific shapes (10)
    val CardDefault = RoundedCornerShape(16.dp)
    val DialogDefault = RoundedCornerShape(28.dp)
    val BottomSheetDefault = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val ChipDefault = RoundedCornerShape(16.dp)
    val TextFieldDefault = RoundedCornerShape(12.dp)
    val ButtonDefault = RoundedCornerShape(20.dp)
    val FABDefault = RoundedCornerShape(16.dp)
    val BannerDefault = RoundedCornerShape(0.dp)
    val SearchBarDefault = RoundedCornerShape(28.dp)
    val TooltipDefault = RoundedCornerShape(4.dp)

    // Navigation and overlay shapes (5)
    val NavigationDrawer = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
    val Snackbar = RoundedCornerShape(12.dp)
    val Badge = RoundedCornerShape(50)
    val AsymmetricBanner = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 28.dp, bottomEnd = 28.dp)
    val ModalBottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}
