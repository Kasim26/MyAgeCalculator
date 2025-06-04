package com.example.agecalculator.presentation.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agecalculator.data.local.OccasionDao
import com.example.agecalculator.data.local.OccasionDatabase
import com.example.agecalculator.data.local.OccasionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.until

class CalculatorViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val occasionDao: OccasionDao =
        OccasionDatabase.getDatabase(application).occasionDao()

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        getOccasion()
    }

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
                when (uiState.value.activeDateField) {
                    DateField.FROM -> _uiState.update { it.copy(fromDateMillis = action.millis) }
                    DateField.TO -> _uiState.update { it.copy(toDateMillis = action.millis) }
                }
                calculateStats()
            }

            is CalculatorAction.SetTitle -> {
                _uiState.update { it.copy(title = action.title) }
            }

            CalculatorAction.SaveOccasion -> {
                saveOccasion()
            }
        }
    }

    private fun saveOccasion() {
        viewModelScope.launch {
            val occasion = OccasionEntity(
                id = 1,
                dateMillis = uiState.value.fromDateMillis,
                emoji = uiState.value.emoji,
                title = uiState.value.title
            )
            occasionDao.upsertOccasion(occasion)
        }
    }

    private fun getOccasion() {
        viewModelScope.launch {
            occasionDao.getOccasionById(1)?.let { occasion ->
                _uiState.update {
                    it.copy(
                        fromDateMillis = occasion.dateMillis,
                        emoji = occasion.emoji,
                        title = occasion.title
                    )
                }
            }
            calculateStats()
        }
    }

    private fun calculateStats() {
        val timeZone = TimeZone.currentSystemDefault()
        val fromMillis = _uiState.value.fromDateMillis ?: System.currentTimeMillis()
        val toMillis = _uiState.value.toDateMillis ?: System.currentTimeMillis()

        val fromInstant = Instant.fromEpochMilliseconds(fromMillis)
        val toInstant = Instant.fromEpochMilliseconds(toMillis)

        val period = fromInstant.periodUntil(toInstant, timeZone)
        val diffInMonths = fromInstant.until(toInstant, DateTimeUnit.MONTH, timeZone)
        val diffInWeeks = fromInstant.until(toInstant, DateTimeUnit.WEEK, timeZone)
        val diffInDays = fromInstant.until(toInstant, DateTimeUnit.DAY, timeZone)
        val diffInHours = fromInstant.until(toInstant, DateTimeUnit.HOUR, timeZone)
        val diffInMinutes = fromInstant.until(toInstant, DateTimeUnit.MINUTE, timeZone)
        val diffInSeconds = fromInstant.until(toInstant, DateTimeUnit.SECOND, timeZone)

        _uiState.update {
            it.copy(
                period = period,
                ageStats = AgeStats(
                    years = period.years,
                    months = diffInMonths.toInt(),
                    weeks = diffInWeeks.toInt(),
                    days = diffInDays.toInt(),
                    hours = diffInHours.toInt(),
                    minutes = diffInMinutes.toInt(),
                    seconds = diffInSeconds.toInt()
                )
            )
        }
    }
}









