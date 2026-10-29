package com.enonymous1.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enonymous1.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {

    private val greetingsViewModel: GreetingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        val state by greetingsViewModel.uiState.collectAsStateWithLifecycle()
                        MyApp(
                            state = state,
                            onContinueClicked = greetingsViewModel::onContinueClicked,
                            onToggleExpanded = greetingsViewModel::onToggleExpanded,
                            modifier = Modifier.fillMaxSize(),
                            names = greetingsViewModel.names,
                        )
                    }
                }
            }
        }
    }
}
