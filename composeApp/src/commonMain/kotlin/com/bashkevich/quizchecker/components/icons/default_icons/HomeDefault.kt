package com.bashkevich.quizchecker.components.icons.default_icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.components.icons.IconGroup

val IconGroup.Default.Home: ImageVector
    get() {
        if (_Home != null) {
            return _Home!!
        }
        _Home = ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(480f, 880f)
                lineTo(120f, 520f)
                lineTo(120f, 440f)
                lineTo(200f, 440f)
                lineTo(200f, 880f)
                lineTo(360f, 880f)
                lineTo(360f, 640f)
                lineTo(600f, 640f)
                lineTo(600f, 880f)
                lineTo(760f, 880f)
                lineTo(760f, 440f)
                lineTo(840f, 440f)
                lineTo(840f, 520f)
                lineTo(480f, 880f)
                close()
                moveTo(480f, 160f)
                lineTo(80f, 480f)
                lineTo(120f, 520f)
                lineTo(480f, 200f)
                lineTo(840f, 520f)
                lineTo(880f, 480f)
                lineTo(480f, 160f)
                close()
            }
        }.build()

        return _Home!!
    }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
