package com.wespot.staff.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.wespot.staff.designsystem.util.textDp
import org.jetbrains.compose.resources.Font
import wespotstaff.designsystem.generated.resources.Res
import wespotstaff.designsystem.generated.resources.pretendard_bold
import wespotstaff.designsystem.generated.resources.pretendard_m
import wespotstaff.designsystem.generated.resources.pretendard_r
import wespotstaff.designsystem.generated.resources.pretendard_semibold

@Composable
internal fun PlainTextFont() = FontFamily(
    Font(Res.font.pretendard_r),
    Font(Res.font.pretendard_m),
    Font(Res.font.pretendard_bold, weight = FontWeight.Bold),
    Font(Res.font.pretendard_semibold, weight = FontWeight.SemiBold),
)

private const val LINE_HEIGHT_PERCENT = 1.5f

@Composable
fun StaticTypography(): TypeScale {
    val fontFamily: FontFamily = PlainTextFont()
    val dp20 = 20.textDp
    val dp18 = 18.textDp
    val dp16 = 16.textDp
    val dp14 = 14.textDp
    val dp13 = 13.textDp
    val dp12 = 12.textDp
    val dp10 = 10.textDp

    return remember(fontFamily) {
        TypeScale(
            _header1 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = dp20,
                lineHeight = dp20 * LINE_HEIGHT_PERCENT,
            ),
            _header2 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp18,
                lineHeight = dp18 * LINE_HEIGHT_PERCENT,
            ),
            _header3 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp14,
                lineHeight = dp14 * LINE_HEIGHT_PERCENT,
            ),
            _body1 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = dp18,
                lineHeight = dp18 * LINE_HEIGHT_PERCENT,
            ),
            _body2 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp18,
                lineHeight = dp18 * LINE_HEIGHT_PERCENT,
            ),
            _body3 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp14,
                lineHeight = dp14 * LINE_HEIGHT_PERCENT,
            ),
            _body4 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dp16,
                lineHeight = dp16 * LINE_HEIGHT_PERCENT,
            ),
            _body5 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp14,
                lineHeight = dp14 * LINE_HEIGHT_PERCENT,
            ),
            _body6 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dp14,
                lineHeight = dp14 * LINE_HEIGHT_PERCENT,
            ),
            _body7 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dp13,
                lineHeight = dp13 * LINE_HEIGHT_PERCENT,
            ),
            _body8 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = dp13,
                lineHeight = dp13 * LINE_HEIGHT_PERCENT,
            ),
            _body9 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dp12,
                lineHeight = dp12 * LINE_HEIGHT_PERCENT,
            ),
            _body10 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp10,
                lineHeight = dp10 * LINE_HEIGHT_PERCENT,
            ),
            _body11 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = dp10,
                lineHeight = dp10 * LINE_HEIGHT_PERCENT,
            ),
            _badge = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = dp12,
                lineHeight = dp12 * LINE_HEIGHT_PERCENT,
            ),
        )
    }
}

@Immutable
data class TypeScale constructor(
    private val _header1: TextStyle,
    private val _header2: TextStyle,
    private val _header3: TextStyle,
    private val _body1: TextStyle,
    private val _body2: TextStyle,
    private val _body3: TextStyle,
    private val _body4: TextStyle,
    private val _body5: TextStyle,
    private val _body6: TextStyle,
    private val _body7: TextStyle,
    private val _body8: TextStyle,
    private val _body9: TextStyle,
    private val _body10: TextStyle,
    private val _body11: TextStyle,
    private val _badge: TextStyle,
) {
    val header1: TextStyle @Composable get() = _header1
    val header2: TextStyle @Composable get() = _header2
    val header3: TextStyle @Composable get() = _header3

    val body1: TextStyle @Composable get() = _body1
    val body2: TextStyle @Composable get() = _body2
    val body3: TextStyle @Composable get() = _body3
    val body4: TextStyle @Composable get() = _body4
    val body5: TextStyle @Composable get() = _body5
    val body6: TextStyle @Composable get() = _body6
    val body7: TextStyle @Composable get() = _body7
    val body8: TextStyle @Composable get() = _body8
    val body9: TextStyle @Composable get() = _body9
    val body10: TextStyle @Composable get() = _body10
    val body11: TextStyle @Composable get() = _body11

    val badge: TextStyle @Composable get() = _badge

    @Composable
    fun Copy() = Typography(
        displayLarge = header1,
        displayMedium = header2,
        displaySmall = header3,
        headlineLarge = header1,
        headlineMedium = header2,
        headlineSmall = header3,
        titleLarge = header1,
        titleMedium = header2,
        titleSmall = header3,
        bodyLarge = body1,
        bodyMedium = body2,
        bodySmall = body3,
        labelLarge = body4,
        labelMedium = body4,
        labelSmall = body5,
    )
}
