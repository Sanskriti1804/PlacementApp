package com.example.placementprojectmp.data.mapper

import com.example.placementprojectmp.R
import com.example.placementprojectmp.data.remote.dto.CompanyResponse
import com.example.placementprojectmp.data.remote.dto.DriveResponse
import com.example.placementprojectmp.data.remote.dto.JobApplicationResponse
import com.example.placementprojectmp.data.remote.dto.JobResponse
import com.example.placementprojectmp.data.remote.dto.StudentProfileResponse
import com.example.placementprojectmp.ui.components.ApplicationStatusStage
import com.example.placementprojectmp.ui.components.ApplicationStatusScreenItem
import com.example.placementprojectmp.ui.components.DriveItem
import com.example.placementprojectmp.ui.screens.shared.component.ApplicationItem
import com.example.placementprojectmp.ui.screens.shared.component.JobItem
import com.example.placementprojectmp.viewmodel.CompanyUiModel
import com.example.placementprojectmp.viewmodel.DriveUiModel
import com.example.placementprojectmp.viewmodel.Industry
import com.example.placementprojectmp.viewmodel.JobDepartment
import com.example.placementprojectmp.viewmodel.JobType
import com.example.placementprojectmp.viewmodel.JobUiModel
import com.example.placementprojectmp.viewmodel.Status
import com.example.placementprojectmp.viewmodel.WorkMode
import com.example.placementprojectmp.viewmodel.WorkspaceStudent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val displayDateFmt = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

private fun parseLocalDate(raw: String?): LocalDate {
    if (raw.isNullOrBlank()) return LocalDate.now()
    val datePart = raw.take(10)
    return runCatching { LocalDate.parse(datePart) }.getOrElse { LocalDate.now() }
}

private fun formatDisplayDate(raw: String?): String {
    if (raw.isNullOrBlank()) return LocalDate.now().format(displayDateFmt)
    return runCatching {
        val ld = if (raw.length >= 10) LocalDate.parse(raw.take(10)) else LocalDate.now()
        ld.format(displayDateFmt)
    }.getOrElse { LocalDate.now().format(displayDateFmt) }
}

private fun relativeTimeLabel(raw: String?): String {
    if (raw.isNullOrBlank()) return "Recently"
    val posted = parseLocalDate(raw)
    val days = java.time.temporal.ChronoUnit.DAYS.between(posted, LocalDate.now())
    return when {
        days <= 0L -> "Today"
        days == 1L -> "1 day ago"
        days < 7L -> "$days days ago"
        days < 30L -> "${days / 7} weeks ago"
        else -> "${days / 30} months ago"
    }
}

private fun sectorToIndustry(sector: String?): Industry {
    val s = sector?.uppercase(Locale.ROOT).orEmpty()
    return when {
        s.contains("FIN") || s.contains("BANK") -> Industry.FINANCE
        s.contains("HEALTH") || s.contains("MED") -> Industry.HEALTHCARE
        s.contains("EDU") -> Industry.EDUCATION
        else -> Industry.TECH
    }
}

private fun parseJobType(raw: String?): JobType = when (raw?.uppercase(Locale.ROOT)) {
    "INTERNSHIP" -> JobType.INTERNSHIP
    "CONTRACT" -> JobType.CONTRACT
    "PART_TIME" -> JobType.CONTRACT
    "APPRENTICESHIP" -> JobType.INTERNSHIP
    else -> JobType.FULL_TIME
}

private fun parseWorkMode(raw: String?): WorkMode = when (raw?.uppercase(Locale.ROOT)) {
    "REMOTE" -> WorkMode.REMOTE
    "HYBRID" -> WorkMode.HYBRID
    "ON_SITE" -> WorkMode.ONSITE
    else -> WorkMode.HYBRID
}

private fun deriveStatusFromJob(lastApply: LocalDate): Status =
    if (lastApply.isBefore(LocalDate.now())) Status.CLOSED else Status.OPEN

private fun deriveDriveStatus(lastReg: LocalDate, resultStatus: String?): Status {
    if (resultStatus?.equals("ANNOUNCED", ignoreCase = true) == true) return Status.CLOSED
    return if (lastReg.isBefore(LocalDate.now())) Status.CLOSED else Status.OPEN
}

private val companyLogoCycle = listOf(R.drawable.comp_1, R.drawable.comp_2, R.drawable.comp_3)

fun companyLogoForIndex(index: Int): Int = companyLogoCycle[index.mod(companyLogoCycle.size)]

object PlacementUiMappers {

    fun jobToUiModel(job: JobResponse, appliedCount: Int = 0): JobUiModel {
        val idNum = job.id ?: 0L
        val company = job.company
        val companyName = company?.name.orEmpty().ifBlank { "Company #$idNum" }
        val location = company?.location.orEmpty().ifBlank { job.venue.orEmpty().ifBlank { "India" } }
        val roleTitle = job.jobDescription
            ?.lineSequence()
            ?.firstOrNull { it.isNotBlank() }
            ?.take(80)
            ?: job.jobType?.replace('_', ' ')?.lowercase(Locale.ROOT)?.replaceFirstChar { it.titlecase(Locale.ROOT) }
            ?: "Open role"
        val last = parseLocalDate(job.lastDateToApply)
        val industry = sectorToIndustry(company?.sector)
        return JobUiModel(
            id = idNum.toString(),
            companyLogoResId = companyLogoForIndex(idNum.toInt()),
            companyName = companyName,
            location = location,
            jobRole = roleTitle,
            department = JobDepartment.TECH,
            jobType = parseJobType(job.jobType),
            industry = industry,
            workMode = parseWorkMode(job.workMode),
            salaryLpa = job.ctcLpa?.toFloat() ?: 0f,
            status = deriveStatusFromJob(last),
            lastDate = last,
            appliedCount = appliedCount
        )
    }

    fun driveToUiModel(drive: DriveResponse, candidateCount: Int = 0): DriveUiModel {
        val idNum = drive.id ?: 0L
        val company = drive.company
        val companyName = company?.name.orEmpty().ifBlank { "Company #$idNum" }
        val lastReg = parseLocalDate(drive.lastDateToApply)
        val start = parseLocalDate(drive.driveDateTime ?: drive.lastDateToApply)
        return DriveUiModel(
            id = idNum.toString(),
            companyLogoResId = companyLogoForIndex(idNum.toInt()),
            companyName = companyName,
            driveName = drive.driveName.orEmpty().ifBlank { "Campus drive" },
            startDate = start,
            lastDateToRegister = lastReg,
            status = deriveDriveStatus(lastReg, drive.resultStatus),
            candidateCount = candidateCount
        )
    }

    fun companyToUiModel(company: CompanyResponse, index: Int = 0): CompanyUiModel {
        val idNum = company.id ?: index.toLong()
        val contacts = company.contactSupports.orEmpty()
        val primary = contacts.firstOrNull()
        val sector = company.sector
        return CompanyUiModel(
            id = idNum.toString(),
            name = company.name.orEmpty().ifBlank { "Company" },
            location = company.location.orEmpty().ifBlank { "India" },
            industry = sectorToIndustry(sector),
            companyType = if (sectorToIndustry(sector) == Industry.TECH) "Product" else "Service",
            website = company.websiteUrl.orEmpty().ifBlank { "https://example.com" },
            description = company.description.orEmpty().ifBlank { company.overview.orEmpty() },
            logoResId = companyLogoForIndex(idNum.toInt()),
            candidateCount = (idNum * 7 % 100).toInt() + 20,
            status = Status.OPEN,
            companyEmail = company.email.orEmpty(),
            hrName = primary?.name.orEmpty(),
            hrEmail = primary?.email.orEmpty(),
            hrPhone = primary?.phone.orEmpty()
        )
    }

    fun jobToJobItem(job: JobResponse): JobItem {
        val id = job.id?.toString().orEmpty().ifBlank { "0" }
        val companyName = job.company?.name.orEmpty().ifBlank { "Company" }
        val role = job.jobDescription
            ?.lineSequence()
            ?.firstOrNull { it.isNotBlank() }
            ?.take(60)
            ?: job.jobType?.replace('_', ' ') ?: "Role"
        return JobItem(
            id = id,
            companyName = companyName,
            roleTitle = role,
            timeAgo = relativeTimeLabel(job.updatedAt ?: job.createdAt),
            logoIconResId = 0
        )
    }

    fun driveToDriveItem(drive: DriveResponse): DriveItem {
        val companyName = drive.company?.name.orEmpty().ifBlank { "Company" }
        val timing = drive.driveDateTime?.substringAfter('T')?.take(5)?.ifBlank { "10:00" } ?: "10:00"
        val dateLabel = formatDisplayDate(drive.driveDateTime ?: drive.lastDateToApply)
        return DriveItem(
            companyName = companyName,
            driveTiming = timing,
            date = dateLabel
        )
    }

    fun applicationToApplicationItem(
        app: JobApplicationResponse,
        companyName: String,
        roleTitle: String
    ): ApplicationItem = ApplicationItem(
        companyName = companyName.ifBlank { "Company #${app.companyId}" },
        role = roleTitle.ifBlank { "Job #${app.jobId}" },
        status = app.status?.replace('_', ' ')?.lowercase(Locale.ROOT)?.replaceFirstChar { it.titlecase(Locale.ROOT) }
            ?: "Applied"
    )

    fun applicationToStatusScreenItem(
        app: JobApplicationResponse,
        companyName: String,
        location: String,
        roleTitle: String
    ): ApplicationStatusScreenItem {
        val stage = backendStatusToStage(app.status)
        return ApplicationStatusScreenItem(
            companyName = companyName.ifBlank { "Company" },
            location = location.ifBlank { "—" },
            role = roleTitle.ifBlank { "Role" },
            appliedDate = formatDisplayDate(app.appliedDate),
            currentStage = stage,
            interviewDate = app.interviewDate?.let { formatDisplayDate(it) },
            interviewMode = app.interviewMode?.replace('_', ' ')?.lowercase(Locale.ROOT)?.replaceFirstChar { it.titlecase(Locale.ROOT) }
                ?: "Offline"
        )
    }

    fun studentProfileToWorkspaceStudent(profile: StudentProfileResponse, portraitIdx: Int): WorkspaceStudent {
        val id = profile.id?.toString().orEmpty().ifBlank { "0" }
        val portraits = com.example.placementprojectmp.ui.screens.staff.StaffStudentPortraitIds.all
        val res = portraits[portraitIdx.mod(portraits.size)]
        return WorkspaceStudent(
            id = id,
            name = profile.name.orEmpty().ifBlank { profile.username.orEmpty().ifBlank { "Student" } },
            rollNumber = profile.username.orEmpty().ifBlank { profile.userEmail.orEmpty() },
            profileResId = res,
            tags = emptySet(),
            note = ""
        )
    }

    private fun backendStatusToStage(status: String?): ApplicationStatusStage = when (status?.uppercase(Locale.ROOT)) {
        "APPLICATION_REVIEWED" -> ApplicationStatusStage.ApplicationReviewed
        "SHORTLISTED" -> ApplicationStatusStage.Shortlisted
        "INTERVIEW_SCHEDULED" -> ApplicationStatusStage.InterviewScheduled
        "OFFER" -> ApplicationStatusStage.Offer
        "HIRED" -> ApplicationStatusStage.Hired
        "REJECTED", "WITHDRAWN" -> ApplicationStatusStage.Applied
        else -> ApplicationStatusStage.Applied
    }
}
