package com.bashkevich.quizchecker.components.icons.default_icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.components.icons.IconGroup

val IconGroup.Default.Standings: ImageVector
    get() {
        if (_Standings != null) {
            return _Standings!!
        }
        _Standings = ImageVector.Builder(
            name = "Standings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(240f, 880f)
                lineTo(240f, 520f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(320f, 440f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(400f, 520f)
                lineTo(400f, 880f)
                lineTo(240f, 880f)
                close()
                moveTo(560f, 880f)
                lineTo(560f, 320f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(640f, 240f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(720f, 320f)
                lineTo(720f, 880f)
                lineTo(560f, 880f)
                close()
                moveTo(480f, 920f)
                lineTo(200f, 920f)
                lineTo(200f, 840f)
                lineTo(480f, 840f)
                lineTo(480f, 920f)
                close()
                moveTo(480f, 880f)
                lineTo(480f, 200f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(560f, 120f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(640f, 200f)
                lineTo(640f, 880f)
                lineTo(480f, 880f)
                close()
                moveTo(200f, 920f)
                lineTo(200f, 600f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(280f, 520f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(360f, 600f)
                lineTo(360f, 920f)
                lineTo(200f, 920f)
                close()
            }
        }.build()

        return _Standings!!
    }

@Suppress("ObjectPropertyName")
private var _Standings: ImageVector? = null
