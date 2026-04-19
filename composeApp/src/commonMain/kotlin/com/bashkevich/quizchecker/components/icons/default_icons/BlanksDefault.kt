package com.bashkevich.quizchecker.components.icons.default_icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.components.icons.IconGroup

val IconGroup.Default.Blanks: ImageVector
    get() {
        if (_Blanks != null) {
            return _Blanks!!
        }
        _Blanks = ImageVector.Builder(
            name = "Blanks",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(240f, 880f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(160f, 800f)
                verticalLineToRelative(-640f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(240f, 80f)
                horizontalLineToRelative(480f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(800f, 160f)
                verticalLineToRelative(640f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(720f, 880f)
                lineTo(240f, 880f)
                close()
                moveTo(240f, 800f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(-640f)
                lineTo(240f, 160f)
                verticalLineToRelative(640f)
                close()
                moveTo(320f, 680f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(-80f)
                lineTo(320f, 600f)
                verticalLineToRelative(80f)
                close()
                moveTo(320f, 520f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(-80f)
                lineTo(320f, 440f)
                verticalLineToRelative(80f)
                close()
                moveTo(320f, 360f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(-80f)
                lineTo(320f, 280f)
                verticalLineToRelative(80f)
                close()
            }
        }.build()

        return _Blanks!!
    }

@Suppress("ObjectPropertyName")
private var _Blanks: ImageVector? = null
