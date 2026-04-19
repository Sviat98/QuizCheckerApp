# QC-1: Bottom Navigation на экране деталей квиза

Status: DRAFT

## Контекст / идея

На экране деталей викторины (`QuizDetailsScreen`) необходимо добавить нижнюю навигацию (Bottom Navigation Bar) с тремя вкладками: **Home**, **Blanks**, **Standings**.

Текущий контент экрана (информация о квизе, кнопки Start/Finish/Revert, кнопка Settings) перемещается во вкладку **Home**. Вкладки **Blanks** и **Standings** пока содержат пустой экран с текстовой меткой по центру.

Иконки создаются вручную как кастомные `ImageVector` по аналогии с существующими в `components/icons/default_icons/` (viewport 960x960, расширение `IconGroup.Default`):
- Home → `IconGroup.Default.Home`
- Blanks → `IconGroup.Default.Blanks`
- Standings → `IconGroup.Default.Standings`

## Цели

1. **Добавить Bottom Navigation Bar** на экран деталей квиза с тремя вкладками
2. **Переместить текущий контент** экрана во вкладку Home
3. **Создать заглушки** для вкладок Blanks и Standings с пустым экраном и подписью по центру
4. **Реализовать переключение** между вкладками без изменения навигационного стека (внутренние табы экрана)

## User stories

1. **Как администратор**, я хочу видеть нижнюю навигацию на экране деталей квиза, чтобы быстро переключаться между разделами
2. **Как администратор**, я хочу видеть основную информацию о квизе во вкладке Home (как сейчас)
3. **Как администратор**, я хочу видеть заглушку с подписью "Blanks" при переходе на соответствующую вкладку
4. **Как администратор**, я хочу видеть заглушку с подписью "Standings" при переходе на соответствующую вкладку

## Основные сценарии

### UC-1: Отображение Bottom Navigation Bar
**Основной сценарий:**
1. Пользователь открывает экран деталей квиза (`QuizDetailsScreen`)
2. Под TopAppBar и над нижней границей экрана отображается Bottom Navigation Bar
3. Bottom Navigation Bar содержит 3 пункта: Home (выбран по умолчанию), Blanks, Standings
4. Каждый пункт содержит иконку и текстовую метку

### UC-2: Переключение вкладок
**Основной сценарий:**
1. Пользователь находится на экране деталей квиза
2. Вкладка Home активна по умолчанию, отображается текущий контент квиза
3. Пользователь нажимает на вкладку Blanks
4. Экран переключается на пустой экран с подписью "Blanks" по центру
5. Пользователь нажимает на вкладку Standings
6. Экран переключается на пустой экран с подписью "Standings" по центру
7. Пользователь нажимает на вкладку Home
8. Отображается контент квиза

### UC-3: Сохранение состояния при переключении
**Основной сценарий:**
1. Пользователь находится на вкладке Home
2. Переключается на Blanks, затем обратно на Home
3. Состояние квиза сохраняется (не перезагружается)

## Технические требования

### Изменения в существующих файлах

#### 1. `QuizDetailsScreen.kt`
- Обернуть текущий контент в структуру с переключением вкладок
- Добавить `NavigationBar` в `Scaffold.bottomBar`
- Использовать `remember` / `mutableIntStateOf` для хранения выбранной вкладки
- Текущий `QuizDetailsContent` становится содержимым вкладки Home (index 0)
- Добавить `when` по выбранной вкладке для отображения соответствующего контента

#### 2. `QuizDetailsState.kt` — без изменений
Состояние вкладки является локальным UI-состоянием экрана, не требует добавления в `QuizDetailsScreenState`.

### Новые файлы

#### 1. `adminApp/screens/quizdetails/QuizDetailsBlanksScreen.kt`
```kotlin
@Composable
fun QuizDetailsBlanksScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(Res.string.blanks_tab_title))
    }
}
```

#### 2. `adminApp/screens/quizdetails/QuizDetailsStandingsScreen.kt`
```kotlin
@Composable
fun QuizDetailsStandingsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(Res.string.standings_tab_title))
    }
}
```

#### 3. Строковые ресурсы (все три локали)

| Ключ | EN | DE | RU |
|------|----|----|-----|
| `home_tab_title` | Home | Startseite | Главная |
| `blanks_tab_title` | Blanks | Blanks | Бланки |
| `standings_tab_title` | Standings | Tabelle | Таблица |
| `home_tab_description` | Home | Startseite | Главная |
| `blanks_tab_description` | Blanks | Blanks | Бланки |
| `standings_tab_description` | Standings | Tabelle | Таблица |

### Структура QuizDetailsScreen (псевдокод)

```kotlin
@Composable
fun QuizDetailsScreen(...) {
    // existing loading/content logic
    when (state.isLoading) {
        true -> QuizDetailsLoading(...)
        false -> QuizDetailsContentWithTabs(...)
    }
}

@Composable
private fun QuizDetailsContentWithTabs(state, onEvent, onBack, onNavigateToSettings) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { /* existing TopAppBar */ },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(IconGroup.Default.Home, ...) },
                    label = { Text(stringResource(Res.string.home_tab_title)) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(IconGroup.Default.Blanks, ...) },
                    label = { Text(stringResource(Res.string.blanks_tab_title)) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(IconGroup.Default.Standings, ...) },
                    label = { Text(stringResource(Res.string.standings_tab_title)) }
                )
            }
        }
    ) { contentPadding ->
        when (selectedTab) {
            0 -> QuizDetailsInfo(...) // current content
            1 -> QuizDetailsBlanksScreen(...)
            2 -> QuizDetailsStandingsScreen(...)
        }
    }
}
```

### Иконки (кастомные, ручное создание)

Три новые иконки создаются вручную как `ImageVector` в директории `components/icons/default_icons/` по существующему паттерну (viewport 960x960, `IconGroup.Default.*` extension property, `SolidColor(Color(0xFF1F1F1F))`, lazy singleton).

#### `HomeDefault.kt`
- Иконка дома (крыша + стены)
- Путь: `composeApp/src/commonMain/kotlin/com/bashkevich/quizchecker/components/icons/default_icons/HomeDefault.kt`

#### `BlanksDefault.kt`
- Иконка документа (прямоугольник с линиями)
- Путь: `composeApp/src/commonMain/kotlin/com/bashkevich/quizchecker/components/icons/default_icons/BlanksDefault.kt`

#### `StandingsDefault.kt`
- Иконка кубка/трофея
- Путь: `composeApp/src/commonMain/kotlin/com/bashkevich/quizchecker/components/icons/default_icons/StandingsDefault.kt`

Пример структуры (по аналогии с `AddDefault.kt`):
```kotlin
package com.bashkevich.quizchecker.components.icons.default_icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bashkevich.quizchecker.components.icons.IconGroup

val IconGroup.Default.Home: ImageVector
    get() {
        if (_Home != null) return _Home!!
        _Home = ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                // SVG path data для иконки дома
            }
        }.build()
        return _Home!!
    }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
```

Использование в `NavigationBar`:
```kotlin
Icon(IconGroup.Default.Home, contentDescription = stringResource(Res.string.home_tab_description))
Icon(IconGroup.Default.Blanks, contentDescription = stringResource(Res.string.blanks_tab_description))
Icon(IconGroup.Default.Standings, contentDescription = stringResource(Res.string.standings_tab_description))
```

## Успех / метрики

- Bottom Navigation Bar отображается на экране деталей квиза
- Все три вкладки переключаются корректно
- Вкладка Home отображает текущий контент без изменений
- Вкладки Blanks и Standings показывают пустой экран с подписью по центру
- Строковые ресурсы доступны на всех трёх языках (en, de, ru)

## Ограничения и допущения

### Ограничения
1. Переключение вкладок — локальное UI-состояние, не затрагивает навигационный стек
2. Заглушки Blanks и Standings не содержат бизнес-логики

### Допущения
1. Вкладки являются внутренними для QuizDetailsScreen (не отдельные навигационные маршруты)
2. `material-icons-extended` НЕ используется — все иконки создаются вручную
3. TopAppBar с кнопкой назад остаётся общим для всех вкладок

## Риски

1. **Нарушение навигационного стека** — при нажатии Back из вложенных вкладок пользователь может ожидать возврата к предыдущему экрану, а не к предыдущей вкладке

## Открытые вопросы

Нет.
