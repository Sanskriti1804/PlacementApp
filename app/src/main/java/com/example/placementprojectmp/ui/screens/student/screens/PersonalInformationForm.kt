package com.example.placementprojectmp.ui.screens.student.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.ui.components.ConnectProfileRow
import com.example.placementprojectmp.ui.components.DateOfBirthSelector
import com.example.placementprojectmp.ui.components.FormField
import com.example.placementprojectmp.ui.components.LocationGrid
import com.example.placementprojectmp.ui.components.PhoneInputRow
import com.example.placementprojectmp.ui.components.ProfilePhotoCard
import com.example.placementprojectmp.ui.components.ProfilePlatform
import com.example.placementprojectmp.viewmodel.StudentPersonalDraftViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

/**
 * Personal Information Form: profile row (name + photo), connect profile, phone, address, location, DOB.
 * Shown when Personal tab is selected; lives inside the form container in LazyColumn.
 */
@Composable
fun PersonalInformationForm(
    modifier: Modifier = Modifier
) {
    val draftViewModel: StudentPersonalDraftViewModel = koinViewModel()
    val draft by draftViewModel.draft.collectAsState()
    var fullName by remember(draft.fullName) { mutableStateOf(draft.fullName) }
    var username by remember(draft.username) { mutableStateOf(draft.username) }
    var expandedPlatform by remember { mutableStateOf<ProfilePlatform?>(null) }
    var urlValues by remember { mutableStateOf<Map<ProfilePlatform, String>>(emptyMap()) }
    var countryCode by remember { mutableStateOf("+91") }
    var phone by remember(draft.phone) { mutableStateOf(draft.phone) }
    var address by remember(draft.address) { mutableStateOf(draft.address) }
    var city by remember(draft.city) { mutableStateOf(draft.city) }
    var pinCode by remember(draft.pinCode) { mutableStateOf(draft.pinCode) }
    var state by remember(draft.state) { mutableStateOf(draft.state) }
    var country by remember(draft.country) { mutableStateOf(draft.country) }
    val cal = remember { Calendar.getInstance() }
    var day by remember(draft.day) { mutableStateOf(draft.day.ifBlank { cal.get(Calendar.DAY_OF_MONTH).toString() }) }
    var month by remember(draft.month) { mutableStateOf(draft.month.ifBlank { (cal.get(Calendar.MONTH) + 1).toString() }) }
    var year by remember(draft.year) { mutableStateOf(draft.year.ifBlank { cal.get(Calendar.YEAR).toString() }) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // First row: Left 70% (Full Name, Username) | Right 30% (Profile photo card)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        draftViewModel.updateFullName(it)
                    },
                    placeholder = "Enter full name"
                )
                FormField(
                    label = "Username",
                    value = username,
                    onValueChange = {
                        username = it
                        draftViewModel.updateUsername(it)
                    },
                    placeholder = "Enter username"
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                ProfilePhotoCard(modifier = Modifier.fillMaxHeight())
            }
        }

        // Connect Your Profile: full width; LazyRow scrolls horizontally with outlined cards
        ConnectProfileRow(
            expandedPlatform = expandedPlatform,
            urlValues = urlValues,
            onUrlChange = { p, v -> urlValues = urlValues + (p to v) },
            onExpandToggle = { p ->
                expandedPlatform = if (expandedPlatform == p) null else p
            },
            modifier = Modifier.fillMaxWidth()
        )

        PhoneInputRow(
            countryCode = countryCode,
            phoneValue = phone,
            onPhoneChange = {
                phone = it
                draftViewModel.updatePhone(it)
            }
        )

        FormField(
            label = "Address",
            value = address,
            onValueChange = {
                address = it
                draftViewModel.updateAddress(it)
            },
            placeholder = "Enter address",
            singleLine = false
        )

        LocationGrid(
            city = city,
            onCityChange = {
                city = it
                draftViewModel.updateCity(it)
            },
            pinCode = pinCode,
            onPinCodeChange = {
                pinCode = it
                draftViewModel.updatePinCode(it)
            },
            state = state,
            onStateChange = {
                state = it
                draftViewModel.updateState(it)
            },
            country = country,
            onCountryChange = {
                country = it
                draftViewModel.updateCountry(it)
            }
        )

        DateOfBirthSelector(
            day = day,
            month = month,
            year = year,
            onDateChange = { d, m, y ->
                day = d.toString()
                month = m.toString()
                year = y.toString()
                draftViewModel.updateDob(day, month, year)
            }
        )
    }
}
