package com.example.agecalculator.presentation.calculator

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.agecalculator.R
import com.example.agecalculator.presentation.component.AgeStats
import com.example.agecalculator.presentation.component.EmojiPickerDialog
import com.example.agecalculator.presentation.component.StatisticsCard
import com.example.agecalculator.presentation.component.StylizedAgeText
import com.example.agecalculator.ui.theme.AgeCalculatorTheme
import com.example.agecalculator.ui.theme.CustomBlue
import com.example.agecalculator.ui.theme.CustomPink

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier
) {

    var isEmojiPickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    var emoji by remember { mutableStateOf("🎂") }

    EmojiPickerDialog(
        isOpen = isEmojiPickerDialogOpen,
        onEmojiSelected = { selectedEmoji ->
            emoji = selectedEmoji
            isEmojiPickerDialogOpen = false
        },
        onDismissRequest = {
            isEmojiPickerDialogOpen = false
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalculatorTopBar(
            isDeleteIconVisible = true,
            onBackClick = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(8.dp),
                emoji = emoji,
                onEmojiBoxClick = { isEmojiPickerDialogOpen = true }
            )
            StatisticsSection(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorTopBar(
    modifier: Modifier = Modifier,
    isDeleteIconVisible: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        title = { Text(text = "Age Calculator") },
        actions = {
            if (isDeleteIconVisible) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            IconButton(onClick = onSaveClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = "Save"
                )
            }
        }
    )
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    emoji: String,
    onEmojiBoxClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    brush = Brush.linearGradient(listOf(CustomBlue, CustomPink))
                )
                .clickable { onEmojiBoxClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displayLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = { Text(text = "Title") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateSection(
            title = "From",
            date = "20 Dec, 2025",
            onDateIconClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateSection(
            title = "To",
            date = "28 Jan, 2026",
            onDateIconClick = {}
        )
    }
}

@Composable
private fun StatisticsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StylizedAgeText(
            years = 25,
            months = 8,
            days = 14
        )
        Spacer(modifier = Modifier.height(16.dp))
        StatisticsCard(
            ageStats = AgeStats(
                years = 10,
                months = 105,
                weeks = 1053,
                days = 46254,
                hours = 15648,
                minutes = 658325,
                seconds = 65214565
            )
        )
    }
}

@Composable
private fun DateSection(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    onDateIconClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = date)
        IconButton(onClick = onDateIconClick) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calender"
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewCalculatorScreen() {
    AgeCalculatorTheme {
        CalculatorScreen()
    }
}