package com.example.agecalculator.presentation.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.ShowEmojiPicker -> {
                _uiState.update { it.copy(isEmojiDialogOpen = true) }
            }

            CalculatorAction.DismissEmojiPicker -> {
                _uiState.update { it.copy(isEmojiDialogOpen = false) }
            }

            is CalculatorAction.EmojiSelected -> {
                _uiState.update {
                    it.copy(
                        isEmojiDialogOpen = false,
                        emoji = action.emoji
                    )
                }
            }

            is CalculatorAction.ShowDatePicker -> {
                _uiState.update {
                    it.copy(
                        isDatePickerDialogOpen = true,
                        activeDateField = action.dateField
                    )
                }
            }
            CalculatorAction.DismissDatePicker -> {
                _uiState.update { it.copy(isDatePickerDialogOpen = false) }
            }
            is CalculatorAction.DateSelected -> {
                _uiState.update { it.copy(isDatePickerDialogOpen = false) }
                when(uiState.value.activeDateField) {
                    DateField.FROM -> _uiState.update { it.copy(fromDateMillis = action.millis) }
                    DateField.TO -> _uiState.update { it.copy(toDateMillis = action.millis) }
                }
            }
        }
    }
}