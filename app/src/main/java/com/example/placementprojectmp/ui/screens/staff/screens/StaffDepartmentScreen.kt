package com.example.placementprojectmp.ui.screens.staff.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.placementprojectmp.R
import com.example.placementprojectmp.ui.screens.shared.component.AppScreenHeader
import com.example.placementprojectmp.ui.screens.student.component.CourseDomainMappingFilter
import com.example.placementprojectmp.ui.theme.NeonBlue
import com.example.placementprojectmp.ui.theme.colormap.ColorMapper
import com.example.placementprojectmp.ui.theme.colormap.Department

@Composable
fun StaffDepartmentScreen(
    modifier: Modifier = Modifier
) {
    var selectedDomains by remember { mutableStateOf(setOf<String>()) }
    
    val courses = remember { listOf("BTech", "MTech", "MBA") }
    val courseDomains = remember {
        listOf("Computer Science", "Information Technology", "Electronics", "AI & Data Science", "VLSI Design", "Marketing", "Finance", "HR")
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AppScreenHeader(
                title = "Department",
                subtitle = "Explore department and their placement areas"
            )
        }
        CourseDomainMappingFilter(
            courses = courses,
            courseDomains = courseDomains,
            selectedDomains = selectedDomains,
            isLoading = false,
            onCourseClick = { },
            onDomainToggle = { domain ->
                selectedDomains = if (domain in selectedDomains) {
                    selectedDomains - domain
                } else {
                    selectedDomains + domain
                }
            }
        )
        
        if (selectedDomains.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Department Faculty",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    
                    val teacherNames = listOf("Dr. Ramesh Kumar", "Dr. Sunita Verma")
                    val teacherRoles = listOf("Professor", "Associate Professor")
                    val teacherImages = listOf(R.drawable.pfp_staff, R.drawable.pfp_staff)
                    
                    teacherNames.forEachIndexed { index, name ->
                        TeacherIdCardsSection(
                            modifier = Modifier.fillMaxWidth(),
                            name = name,
                            roleText = "${teacherRoles[index]} at ${selectedDomains.first()}",
                            imageResId = teacherImages[index],
                            idText = "FAC_00${index + 1}"
                        )
                    }
                }
            }
            
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Placement Statistics",
                        style = MaterialTheme.typography.titleLarge,
                        color = NeonBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                    androidx.compose.material3.HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    
                    val companiesImages = remember { listOf(R.drawable.comp_1, R.drawable.comp_2, R.drawable.comp_3) }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ExpandableCounterCard(
                            modifier = Modifier.weight(1f),
                            title = "Assigned Departments",
                            countText = "+${selectedDomains.size}",
                            colorDots = listOf(
                                ColorMapper.getColor(Department.CSE),
                                ColorMapper.getColor(Department.IT)
                            ),
                            items = selectedDomains.toList()
                        )
                        ExpandableCounterCard(
                            modifier = Modifier.weight(1f),
                            title = "Assigned Companies",
                            countText = "+12",
                            imageDots = companiesImages,
                            items = listOf("Google", "Microsoft", "Amazon")
                        )
                    }
                }
            }
        }
    }
}
