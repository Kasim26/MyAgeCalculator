package com.example.agecalculator.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.agecalculator.presentation.calculator.AgeStats
import com.example.agecalculator.presentation.theme.spacing

@Composable
fun StatisticsCard(
    modifier: Modifier = Modifier,
    ageStats: AgeStats
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Age Statistics",
                style = MaterialTheme.typography.titleMedium,
                textDecoration = TextDecoration.Underline
            )
            TotalTimeRow(label = "Total Years: ", value = ageStats.years)
            TotalTimeRow(label = "Total Months: ", value = ageStats.months)
            TotalTimeRow(label = "Total Weeks: ", value = ageStats.weeks)
            TotalTimeRow(label = "Total Days: ", value = ageStats.days)
            TotalTimeRow(label = "Total Hours: ", value = ageStats.hours)
            TotalTimeRow(label = "Total Minutes: ", value = ageStats.minutes)
            TotalTimeRow(label = "Total Seconds: ", value = ageStats.seconds)
        }
    }
}

@Composable
private fun TotalTimeRow(
    modifier: Modifier = Modifier,
    label: String,
    value: Int
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier.weight(1f),
            text = value.toString(),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun PreviewStatisticsCard() {
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