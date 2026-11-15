# AGENTS.md

Общие инструкции для агентов по проекту QuizCheckerApp.

## Icons (Valkyrie)

- Модуль: :shared
- Плагин: io.github.composegears.valkyrie 0.5.2 — подключён (gradle/libs.versions.toml + shared/build.gradle.kts, блок `valkyrie { ... iconPack { nested Default ← default_icons, nested Filled ← filled_icons } }`)
- **JVM 21+**: плагин применяется к :shared, поэтому ЛЮБОЙ таск :shared конфигурируется только на JVM 21+ (например `JAVA_HOME=C:/Users/User/.jdks/ms-21.0.8`); на дефолтной Java 17 сборка падает на resolution плагина
- SVG (source of truth): shared/src/commonMain/valkyrieResources/default_icons/<имя>.svg и shared/src/commonMain/valkyrieResources/filled_icons/<имя>.svg
- Сгенерированные .kt (версионируются в src): shared/src/commonMain/kotlin/com/bashkevich/quizchecker/components/icons/default_icons/<Имя>Default.kt и .../filled_icons/<Имя>Filled.kt, пак-объект IconGroup в icons/IconGroup.kt
- Package: com.bashkevich.quizchecker.components.icons.default_icons / ....filled_icons (исторические имена `default_icons`/`filled_icons`)
- **Обновление иконок**: `./gradlew :shared:updateValkyrieIcons` — полный цикл generate → copyValkyrieIcons (адаптация раскладки Valkyrie `default/Add.kt`, package `...icons.default` → `default_icons/AddDefault.kt`, package `...icons.default_icons`) → cleanValkyrieGenerated (удаляет build/generated). Ручного копирования файлов нет.
- **Особенности интеграции** (важно не сломать):
  - Valkyrie регистрирует build/generated/sources/valkyrie как компилируемый srcDir → конфликт (redeclaration IconGroup) с .kt-копиями в src/. Поэтому в shared/build.gradle.kts стоит гейт: таски generateValkyrieImageVector* выполняются только при явном запросе updateValkyrieIcons/copyValkyrieIcons/generateValkyrieImageVector; обычные сборки их скипают, build-каталог не создаётся.
  - ksp*-таски имеют явный dependsOn на generateValkyrieImageVector* — иначе Gradle-валидация «implicit dependency» роняет сборку.
  - Valkyrie 0.5.2 генерирует: `IconGroup.kt` + плоские папки `default/`/`filled/` с файлами `<Имя>.kt` и пакетами `...icons.default`/`...icons.filled`; копирование в src переименовывает и правит package.
- Кастомная иконка: `blanks` — не стандартный символ Material Symbols; SVG собран вручную из path-геометрии (rounded rect + 3 строки). filled-вариант = копия default (официального FILL=1 нет).
- Использование (extension property ⇒ обязательны ОБА импорта):

```kotlin
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.Home
import com.bashkevich.quizchecker.components.icons.filled_icons.Home

Icon(IconGroup.Default.Home, contentDescription = null)
Icon(IconGroup.Filled.Home, contentDescription = null)
```
