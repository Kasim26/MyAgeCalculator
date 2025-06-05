package com.example.agecalculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.agecalculator.presentation.calculator.CalculatorScreen
import com.example.agecalculator.presentation.calculator.CalculatorViewModel
import com.example.agecalculator.presentation.theme.AgeCalculatorTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgeCalculatorTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize()
                ) { innerPadding ->
                    val viewModel: CalculatorViewModel = koinViewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    CalculatorScreen(
                        modifier = Modifier.Companion.padding(innerPadding),
                        state = state,
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}