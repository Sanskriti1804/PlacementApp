package com.example.placementprojectmp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Two-column grid: Row1 = City, Pin Code; Row2 = State, Country.
 * Consistent spacing between fields.
 */
@Composable
fun LocationGrid(
    city: String,
    onCityChange: (String) -> Unit,
    pinCode: String,
    onPinCodeChange: (String) -> Unit,
    state: String,
    onStateChange: (String) -> Unit,
    country: String,
    onCountryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormField(
                label = "City",
                value = city,
                onValueChange = onCityChange,
                modifier = Modifier.weight(1f)
            )
            FormField(
                label = "Pin Code",
                value = pinCode,
                onValueChange = onPinCodeChange,
                modifier = Modifier.weight(1f),
                keyboardType = KeyboardType.Number
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormField(
                label = "State",
                value = state,
                onValueChange = onStateChange,
                modifier = Modifier.weight(1f)
            )
            FormField(
                label = "Country",
                value = country,
                onValueChange = onCountryChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
