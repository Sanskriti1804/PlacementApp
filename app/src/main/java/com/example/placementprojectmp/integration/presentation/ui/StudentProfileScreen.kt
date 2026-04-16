package com.example.placementprojectmp.integration.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.placementprojectmp.integration.presentation.viewmodel.StudentProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentProfileScreen(
    userId: Long,
    modifier: Modifier = Modifier
) {
    val vm: StudentProfileViewModel = koinViewModel()
    val state by vm.ui.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Field("Name", state.name) { vm.patch { copy(name = it) } }
        Field("Domain Role", state.domainRole) { vm.patch { copy(domainRole = it) } }
        Field("Bio", state.bio) { vm.patch { copy(bio = it) } }
        Field("DOB (yyyy-mm-dd)", state.dob) { vm.patch { copy(dob = it) } }
        Field("Phone Number", state.phoneNumber) { vm.patch { copy(phoneNumber = it) } }
        Field("Photo URL", state.photoUrl) { vm.patch { copy(photoUrl = it) } }
        Field("Address Line", state.addressLine) { vm.patch { copy(addressLine = it) } }
        Field("City", state.city) { vm.patch { copy(city = it) } }
        Field("State", state.state) { vm.patch { copy(state = it) } }

        Text("Skills (comma separated)")
        Field("Languages", state.languages) { vm.patch { copy(languages = it) } }
        Field("Frameworks", state.frameworks) { vm.patch { copy(frameworks = it) } }
        Field("Tools", state.tools) { vm.patch { copy(tools = it) } }
        Field("Platforms", state.platforms) { vm.patch { copy(platforms = it) } }

        state.error?.let { Text(it) }
        Button(onClick = { vm.submit(userId) }, enabled = !state.loading, modifier = Modifier.fillMaxWidth()) {
            Text(if (state.loading) "Saving..." else "Save Profile")
        }
    }
}

@Composable
private fun Field(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

