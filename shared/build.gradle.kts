import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.valkyrie)
}

kotlin {

    android {
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        namespace = "com.bashkevich.quizchecker.composeApp"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.room.runtime.android)
            implementation(libs.slf4j.android)
            implementation(libs.multiplatform.settings.datastore)
            implementation(libs.androidx.datastore.preferences)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation)

            implementation(libs.kotlinx.datetime)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.room.runtime.jvm)
            implementation(libs.logback.classic)
            implementation(libs.multiplatform.settings.datastore)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspJvm", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

compose.resources {
    publicResClass = false
    packageOfResClass = "com.bashkevich.quizchecker.resources"
    generateResClass = auto
}

valkyrie {
    packageName = "com.bashkevich.quizchecker.components.icons"
    iconPack {
        name = "IconGroup"
        targetSourceSet = "commonMain"
        nested {
            name = "Default"
            sourceFolder = "default_icons"
        }
        nested {
            name = "Filled"
            sourceFolder = "filled_icons"
        }
    }
}

// Иконки версионируются как .kt в src/ (см. AGENTS.md): generate → copy → clean build-каталог.
// Копирование адаптирует вывод Valkyrie под историческую раскладку проекта:
// default/Add.kt (package ...icons.default) → default_icons/AddDefault.kt (package ...icons.default_icons)
val copyValkyrieIcons by tasks.registering(Copy::class) {
    dependsOn(tasks.named("generateValkyrieImageVector"))
    val genIcons = layout.buildDirectory.dir("generated/sources/valkyrie/commonMain/kotlin/com/bashkevich/quizchecker/components/icons")
    val srcIcons = layout.projectDirectory.dir("src/commonMain/kotlin/com/bashkevich/quizchecker/components/icons")
    destinationDir = srcIcons.asFile

    from(genIcons) { include("IconGroup.kt") }
    from(genIcons.map { it.dir("default") }) {
        include("*.kt")
        into("default_icons")
        rename { it.removeSuffix(".kt") + "Default.kt" }
        filter { line: String ->
            if (line == "package com.bashkevich.quizchecker.components.icons.default") {
                "package com.bashkevich.quizchecker.components.icons.default_icons"
            } else {
                line
            }
        }
    }
    from(genIcons.map { it.dir("filled") }) {
        include("*.kt")
        into("filled_icons")
        rename { it.removeSuffix(".kt") + "Filled.kt" }
        filter { line: String ->
            if (line == "package com.bashkevich.quizchecker.components.icons.filled") {
                "package com.bashkevich.quizchecker.components.icons.filled_icons"
            } else {
                line
            }
        }
    }
}

tasks.register<Delete>("cleanValkyrieGenerated") {
    delete(layout.buildDirectory.dir("generated/sources/valkyrie"))
}

tasks.register("updateValkyrieIcons") {
    group = "valkyrie"
    description = "Regenerate icon .kt files from valkyrieResources SVGs into src/"
    dependsOn(copyValkyrieIcons)
    finalizedBy("cleanValkyrieGenerated")
}

// Гейт генерации: Valkyrie регистрирует build/generated/sources/valkyrie как srcDir (source of truth
// для компиляции), что конфликтует с версионируемыми .kt-копиями в src/ (redeclaration IconGroup).
// Поэтому генерация выполняется только при явном запросе обновления иконок; обычные сборки её скипают,
// каталог не создаётся, и компилируются только .kt из src/.
tasks.matching { it.name.startsWith("generateValkyrieImageVector") }.configureEach {
    val requested = gradle.startParameter.taskNames.any { request ->
        val taskName = request.substringAfterLast(':')
        taskName == "updateValkyrieIcons" || taskName == "copyValkyrieIcons" || taskName.startsWith("generateValkyrieImageVector")
    }
    onlyIf { requested }
}

// KSP-таски потребляют srcDirs (включая зарегистрированный Valkyrie каталог) без зависимости
// на генерацию — Gradle валидация требует явной зависимости
tasks.matching { it.name.startsWith("ksp") }.configureEach {
    dependsOn(tasks.matching { it.name.startsWith("generateValkyrieImageVector") })
}
