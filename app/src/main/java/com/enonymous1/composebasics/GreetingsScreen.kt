package com.enonymous1.composebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enonymous1.composebasics.ui.theme.ComposeBasicsTheme

/** The main application: onboarding first, then the greetings list. */
@Composable
fun MyApp(
    state: GreetingsUiState,
    onContinueClicked: () -> Unit,
    onToggleExpanded: (String) -> Unit,
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" },
) {
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (state.shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = onContinueClicked)
        } else {
            Greetings(
                names = names,
                expandedNames = state.expandedNames,
                onToggleExpanded = onToggleExpanded,
            )
        }
    }
}

/** The onboarding screen: a welcome message and a Continue button. */
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("onboarding_screen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.welcome_text))
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .testTag("continue_button"),
            onClick = onContinueClicked,
        ) {
            Text(stringResource(R.string.continue_button))
        }
    }
}

/** The scrolling list of greeting cards. Only visible items are composed. */
@Composable
fun Greetings(
    names: List<String>,
    expandedNames: Set<String>,
    onToggleExpanded: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
            .testTag("greetings_list"),
    ) {
        items(items = names, key = { it }) { name ->
            Greeting(
                name = name,
                expanded = name in expandedNames,
                onToggleExpanded = { onToggleExpanded(name) },
            )
        }
    }
}

/** One greeting card, with an expand button for its extra text. */
@Composable
fun Greeting(
    name: String,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .testTag("greeting_card_$name"),
    ) {
        CardContent(
            name = name,
            expanded = expanded,
            onToggleExpanded = onToggleExpanded,
        )
    }
}

/** The content of one greeting card. */
@Composable
fun CardContent(
    name: String,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
        ) {
            Text(text = stringResource(R.string.initial_greeting))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                modifier = Modifier.testTag("greeting_name_$name"),
            )
            if (expanded) {
                Text(
                    text = stringResource(R.string.expanded_text).repeat(2),
                    modifier = Modifier.testTag("expanded_text_$name"),
                )
            }
        }
        IconButton(
            onClick = onToggleExpanded,
            modifier = Modifier.testTag("expand_button_$name"),
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                },
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark",
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    ComposeBasicsTheme {
        Greetings(
            names = listOf("0", "1", "2"),
            expandedNames = setOf("0"),
            onToggleExpanded = {},
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
)
@Composable
fun OnboardingPreview() {
    ComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    ComposeBasicsTheme {
        MyApp(
            state = GreetingsUiState(),
            onContinueClicked = {},
            onToggleExpanded = {},
            modifier = Modifier.fillMaxSize(),
            names = listOf("0", "1", "2"),
        )
    }
}
