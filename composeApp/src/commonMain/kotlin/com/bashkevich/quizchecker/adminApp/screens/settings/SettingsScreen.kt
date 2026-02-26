package com.bashkevich.quizchecker.adminApp.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bashkevich.quizchecker.adminApp.LocalNavHostController
import com.bashkevich.quizchecker.components.icons.IconGroup
import com.bashkevich.quizchecker.components.icons.default_icons.ArrowBack
import com.bashkevich.quizchecker.components.icons.default_icons.ArrowDropDown
import com.bashkevich.quizchecker.settings.LocalLocalization
import com.bashkevich.quizchecker.settings.domain.LOCALES
import com.bashkevich.quizchecker.settings.domain.SettingsLocale
import org.jetbrains.compose.resources.stringResource
import quizcheckerapp.composeapp.generated.resources.*

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navController = LocalNavHostController.current

    SettingsContent(
        modifier = modifier,
        state = state,
        onEvent = { viewModel.onEvent(it) },
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    state: SettingsScreenState,
    onEvent: (SettingsScreenUiEvent) -> Unit,
    onBack: () -> Unit
) {
    val locale = LocalLocalization.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = IconGroup.Default.ArrowBack, contentDescription = stringResource(Res.string.back_button))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LanguageChooser(
                modifier = Modifier.fillMaxWidth(),
                currentLocale = locale,
                onLocaleChange = { locale ->
                    onEvent(SettingsScreenUiEvent.OnLocaleChange(locale))
                }
            )
        }
    }
}

@Composable
fun LanguageChooser(
    modifier: Modifier = Modifier,
    currentLocale: SettingsLocale,
    onLocaleChange: (SettingsLocale) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.then(modifier)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(Res.string.language_label)}: ",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                currentLocale.label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = IconGroup.Default.ArrowDropDown,
                    contentDescription = stringResource(Res.string.choose_language),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                LOCALES.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language.label) },
                        onClick = {
                            onLocaleChange(language)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
