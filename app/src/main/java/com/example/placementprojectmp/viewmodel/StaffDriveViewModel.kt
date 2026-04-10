package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.placementprojectmp.R
import java.time.LocalDate
import kotlin.random.Random

enum class ApplicationTab { COMPANY, DRIVE, JOBS }
enum class Status { OPEN, CLOSED, UPCOMING }
enum class Industry { TECH, FINANCE, HEALTHCARE, EDUCATION }
enum class WorkMode(val label: String) { REMOTE("Remote"), ONSITE("Onsite"), HYBRID("Hybrid") }
enum class JobDepartment { TECH, MANAGEMENT, CORE }
enum class JobType(val label: String) { FULL_TIME("Full-time"), INTERNSHIP("Internship"), CONTRACT("Contract") }

@Immutable
data class FilterState(
    val status: Set<Status> = emptySet(),
    val industry: Set<Industry> = emptySet(),
    val jobType: Set<JobType> = emptySet(),
    val companyQuery: String = "",
    val location: String = "",
    val workMode: Set<WorkMode> = emptySet(),
    val salaryRange: ClosedFloatingPointRange<Float> = 3f..35f
)

@Immutable
data class CompanyUiModel(
    val id: String,
    val name: String,
    val location: String,
    val industry: Industry,
    val companyType: String,
    val website: String,
    val description: String,
    val logoResId: Int,
    val candidateCount: Int,
    val status: Status = Status.OPEN,
    val companyEmail: String = "",
    val hrName: String = "",
    val hrEmail: String = "",
    val hrPhone: String = ""
)

@Immutable
data class DriveUiModel(
    val id: String,
    val companyLogoResId: Int,
    val companyName: String,
    val driveName: String,
    val startDate: LocalDate,
    val lastDateToRegister: LocalDate,
    val status: Status,
    val candidateCount: Int
)

@Immutable
data class JobUiModel(
    val id: String,
    val companyLogoResId: Int,
    val companyName: String,
    val location: String,
    val jobRole: String,
    val department: JobDepartment,
    val jobType: JobType,
    val industry: Industry,
    val workMode: WorkMode,
    val salaryLpa: Float,
    val status: Status,
    val lastDate: LocalDate,
    val appliedCount: Int
)

@Immutable
data class StaffDriveUiState(
    val searchQuery: String = "",
    val isSearchExpanded: Boolean = false,
    val selectedTab: ApplicationTab = ApplicationTab.COMPANY,
    val filterState: FilterState = FilterState(),
    val showFilterSheet: Boolean = false,
    val showFabSheet: Boolean = false,
    val filteredCompanies: List<CompanyUiModel> = emptyList(),
    val filteredDrives: List<DriveUiModel> = emptyList(),
    val filteredJobs: List<JobUiModel> = emptyList()
)

class StaffDriveViewModel : ViewModel() {
    private val companies = mockCompanies()
    private val drives = mockDrives(companies)
    private val jobs = mockJobs(companies)
    private val _uiState = mutableStateOf(
        StaffDriveUiState(
            filteredCompanies = companies,
            filteredDrives = drives,
            filteredJobs = jobs
        )
    )
    val uiState: State<StaffDriveUiState> = _uiState

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        recomputeLists()
    }

    fun onTabSelected(tab: ApplicationTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun expandSearch() {
        _uiState.value = _uiState.value.copy(isSearchExpanded = true)
    }

    fun collapseSearch() {
        _uiState.value = _uiState.value.copy(isSearchExpanded = false, searchQuery = "")
        recomputeLists()
    }

    fun showFilterSheet() {
        _uiState.value = _uiState.value.copy(showFilterSheet = true)
    }

    fun hideFilterSheet() {
        _uiState.value = _uiState.value.copy(showFilterSheet = false)
    }

    fun showFabSheet() {
        _uiState.value = _uiState.value.copy(showFabSheet = true)
    }

    fun hideFabSheet() {
        _uiState.value = _uiState.value.copy(showFabSheet = false)
    }

    fun toggleStatus(status: Status) {
        val selected = _uiState.value.filterState.status
        val updated = if (status in selected) selected - status else selected + status
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(status = updated))
    }

    fun toggleIndustry(industry: Industry) {
        val selected = _uiState.value.filterState.industry
        val updated = if (industry in selected) selected - industry else selected + industry
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(industry = updated))
    }

    fun toggleJobType(type: JobType) {
        val selected = _uiState.value.filterState.jobType
        val updated = if (type in selected) selected - type else selected + type
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(jobType = updated))
    }

    fun toggleWorkMode(mode: WorkMode) {
        val selected = _uiState.value.filterState.workMode
        val updated = if (mode in selected) selected - mode else selected + mode
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(workMode = updated))
    }

    fun onCompanyFilterQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(companyQuery = query))
    }

    fun onLocationFilterChanged(location: String) {
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(location = location))
    }

    fun onSalaryRangeChanged(range: ClosedFloatingPointRange<Float>) {
        _uiState.value = _uiState.value.copy(filterState = _uiState.value.filterState.copy(salaryRange = range))
    }

    fun resetFilters() {
        _uiState.value = _uiState.value.copy(filterState = FilterState())
        recomputeLists()
    }

    fun applyFilters() {
        recomputeLists()
    }

    private fun recomputeLists() {
        val state = _uiState.value
        val query = state.searchQuery.trim()
        val filters = state.filterState

        val companyFiltered = companies.filter { company ->
            val queryMatch = query.isBlank() ||
                company.name.contains(query, true) ||
                company.location.contains(query, true)
            val companyQueryMatch = filters.companyQuery.isBlank() ||
                company.name.contains(filters.companyQuery, true)
            val industryMatch = filters.industry.isEmpty() || company.industry in filters.industry
            val locationMatch = filters.location.isBlank() || company.location.contains(filters.location, true)
            queryMatch && companyQueryMatch && industryMatch && locationMatch
        }
        val driveFiltered = drives.filter { drive ->
            val queryMatch = query.isBlank() ||
                drive.companyName.contains(query, true) ||
                drive.driveName.contains(query, true)
            val statusMatch = filters.status.isEmpty() || drive.status in filters.status
            val companyMatch = filters.companyQuery.isBlank() || drive.companyName.contains(filters.companyQuery, true)
            queryMatch && statusMatch && companyMatch
        }
        val jobFiltered = jobs.filter { job ->
            val queryMatch = query.isBlank() ||
                job.companyName.contains(query, true) ||
                job.jobRole.contains(query, true)
            val statusMatch = filters.status.isEmpty() || job.status in filters.status
            val industryMatch = filters.industry.isEmpty() || job.industry in filters.industry
            val typeMatch = filters.jobType.isEmpty() || job.jobType in filters.jobType
            val companyMatch = filters.companyQuery.isBlank() || job.companyName.contains(filters.companyQuery, true)
            val modeMatch = filters.workMode.isEmpty() || job.workMode in filters.workMode
            val locationMatch = filters.location.isBlank() || job.location.contains(filters.location, true)
            val salaryMatch = job.salaryLpa in filters.salaryRange.start..filters.salaryRange.endInclusive
            queryMatch && statusMatch && industryMatch && typeMatch &&
                companyMatch && modeMatch && locationMatch && salaryMatch
        }

        _uiState.value = state.copy(
            filteredCompanies = companyFiltered,
            filteredDrives = driveFiltered,
            filteredJobs = jobFiltered
        )
    }
}

private fun mockCompanies(): List<CompanyUiModel> {
    val names = listOf("Nexora Systems", "Veltrix Labs", "Aureon Tech", "Zyntric Solutions", "Orvion Dynamics")
    val locations = listOf("Bengaluru", "Pune", "Hyderabad", "Chennai", "Noida")
    val industries = listOf(Industry.TECH, Industry.FINANCE, Industry.HEALTHCARE, Industry.EDUCATION, Industry.TECH)
    val logos = listOf(R.drawable.comp_1, R.drawable.comp_2, R.drawable.comp_3, R.drawable.comp_1, R.drawable.comp_2)
    val hrNames = listOf("Sarah Parker", "Alex Kumar", "Priya Nair", "Rohan Mehta", "Neha Singh")
    return names.mapIndexed { index, name ->
        val slug = name.lowercase().replace(" ", "")
        CompanyUiModel(
            id = "c_$index",
            name = name,
            location = "${locations[index]}, India",
            industry = industries[index],
            companyType = if (index % 2 == 0) "Product" else "Service",
            website = "https://www.$slug.com",
            description = "$name builds campus-focused hiring pipelines and industry-ready technical roles.",
            logoResId = logos[index],
            candidateCount = 34 + index * 9,
            status = when (index % 3) {
                0 -> Status.OPEN
                1 -> Status.UPCOMING
                else -> Status.CLOSED
            },
            companyEmail = "careers@$slug.com",
            hrName = hrNames[index],
            hrEmail = "hr@$slug.com",
            hrPhone = "+91 98765 ${43210 + index}"
        )
    }
}

private fun mockDrives(companies: List<CompanyUiModel>): List<DriveUiModel> {
    val random = Random(90)
    return List(5) { index ->
        val company = companies[index % companies.size]
        val start = LocalDate.now().plusDays((index + 1) * 3L)
        DriveUiModel(
            id = "d_$index",
            companyLogoResId = company.logoResId,
            companyName = company.name,
            driveName = listOf("Campus Accelerator Drive", "Graduate Talent Drive", "Early Career Sprint", "Future Engineers Drive", "Role Fit Drive")[index],
            startDate = start,
            lastDateToRegister = start.minusDays((random.nextInt(2, 6)).toLong()),
            status = if (index % 3 == 0) Status.UPCOMING else Status.OPEN,
            candidateCount = 18 + index * 6
        )
    }
}

private fun mockJobs(companies: List<CompanyUiModel>): List<JobUiModel> {
    val roles = listOf("Associate Android Engineer", "Business Analyst", "Mechanical Design Trainee", "Software QA Engineer", "Data Platform Intern")
    return List(5) { index ->
        val company = companies[index % companies.size]
        JobUiModel(
            id = "j_$index",
            companyLogoResId = company.logoResId,
            companyName = company.name,
            location = company.location,
            jobRole = roles[index],
            department = when (index % 3) {
                0 -> JobDepartment.TECH
                1 -> JobDepartment.MANAGEMENT
                else -> JobDepartment.CORE
            },
            jobType = when (index % 3) {
                0 -> JobType.FULL_TIME
                1 -> JobType.INTERNSHIP
                else -> JobType.CONTRACT
            },
            industry = company.industry,
            workMode = when (index % 3) {
                0 -> WorkMode.HYBRID
                1 -> WorkMode.ONSITE
                else -> WorkMode.REMOTE
            },
            salaryLpa = (5 + index * 2).toFloat(),
            status = if (index % 4 == 0) Status.CLOSED else Status.OPEN,
            lastDate = LocalDate.now().plusDays((index + 3) * 2L),
            appliedCount = 12 + index * 5
        )
    }
}
