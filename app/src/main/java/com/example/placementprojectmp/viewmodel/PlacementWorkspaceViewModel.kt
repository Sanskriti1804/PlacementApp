package com.example.placementprojectmp.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.placementprojectmp.R
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

enum class PlacementTab { RESOURCES, STUDENTS, NOTES }

enum class ResourceType { EXCEL, PDF, DOC }

@Immutable
data class Resource(
    val id: String,
    val fileName: String,
    val fileType: ResourceType,
    val uploadedBy: String,
    val uploadedAt: LocalDateTime,
    val lastOpenedAt: LocalDateTime?,
)

@Immutable
data class StudentTag(
    val id: String,
    val label: String
)

@Immutable
data class WorkspaceStudent(
    val id: String,
    val name: String,
    val rollNumber: String,
    val profileResId: Int,
    val tags: Set<StudentTag> = emptySet(),
    val note: String = ""
)

@Immutable
data class Note(
    val id: String,
    val title: String,
    val description: String,
    val fileLink: String?,
    val createdAt: LocalDateTime
)

@Immutable
data class PlacementWorkspaceState(
    val selectedTab: PlacementTab = PlacementTab.RESOURCES,
    val searchQuery: String = "",
    val isSearchExpanded: Boolean = false,
    val resources: List<Resource> = emptyList(),
    val students: List<WorkspaceStudent> = emptyList(),
    val notes: List<Note> = emptyList(),
    val bulkSelectedStudentIds: Set<String> = emptySet(),
    val isBulkMode: Boolean = false,
    val showResourceActionsForId: String? = null,
    val showNoteActionsForId: String? = null,
    val studentFilterEligible: Boolean? = null,
    val studentFilterApplied: Boolean? = null,
    val studentFilterTaggedOnly: Boolean = false
)

class PlacementWorkspaceViewModel : ViewModel() {
    private val favoriteTag = StudentTag("favorite", "Favorite")
    private val priorityTag = StudentTag("priority", "Priority")
    private val taggedTag = StudentTag("tagged", "Tagged")

    private val allResources = seededResources()
    private val allStudents = seededStudents()
    private val allNotes = seededNotes()

    private val _state = mutableStateOf(
        PlacementWorkspaceState(
            resources = allResources,
            students = allStudents,
            notes = allNotes
        )
    )
    val state: State<PlacementWorkspaceState> = _state

    fun selectTab(tab: PlacementTab) {
        _state.value = _state.value.copy(selectedTab = tab, showResourceActionsForId = null, showNoteActionsForId = null)
    }

    fun expandSearch() {
        _state.value = _state.value.copy(isSearchExpanded = true)
    }

    fun collapseSearch() {
        _state.value = _state.value.copy(isSearchExpanded = false, searchQuery = "")
        recompute()
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        recompute()
    }

    fun setStudentEligibleFilter(value: Boolean?) {
        _state.value = _state.value.copy(studentFilterEligible = value)
        recompute()
    }

    fun setStudentAppliedFilter(value: Boolean?) {
        _state.value = _state.value.copy(studentFilterApplied = value)
        recompute()
    }

    fun toggleTaggedOnly() {
        _state.value = _state.value.copy(studentFilterTaggedOnly = !_state.value.studentFilterTaggedOnly)
        recompute()
    }

    fun showResourceActions(resourceId: String) {
        _state.value = _state.value.copy(showResourceActionsForId = resourceId)
    }

    fun hideResourceActions() {
        _state.value = _state.value.copy(showResourceActionsForId = null)
    }

    fun showNoteActions(noteId: String) {
        _state.value = _state.value.copy(showNoteActionsForId = noteId)
    }

    fun hideNoteActions() {
        _state.value = _state.value.copy(showNoteActionsForId = null)
    }

    fun enterBulkMode(studentId: String) {
        _state.value = _state.value.copy(isBulkMode = true, bulkSelectedStudentIds = setOf(studentId))
    }

    fun exitBulkMode() {
        _state.value = _state.value.copy(isBulkMode = false, bulkSelectedStudentIds = emptySet())
    }

    fun toggleStudentSelected(studentId: String) {
        val selected = _state.value.bulkSelectedStudentIds
        val updated = if (studentId in selected) selected - studentId else selected + studentId
        _state.value = _state.value.copy(
            bulkSelectedStudentIds = updated,
            isBulkMode = updated.isNotEmpty()
        )
    }

    fun addTagToSelected(tag: StudentTag) {
        val selectedIds = _state.value.bulkSelectedStudentIds
        if (selectedIds.isEmpty()) return
        val updatedStudents = allStudents.map { s ->
            if (s.id in selectedIds) s.copy(tags = s.tags + tag) else s
        }
        updateStudents(updatedStudents)
    }

    fun removeTagFromSelected(tag: StudentTag) {
        val selectedIds = _state.value.bulkSelectedStudentIds
        if (selectedIds.isEmpty()) return
        val updatedStudents = allStudents.map { s ->
            if (s.id in selectedIds) s.copy(tags = s.tags - tag) else s
        }
        updateStudents(updatedStudents)
    }

    fun updateStudentNote(studentId: String, note: String) {
        val updatedStudents = allStudents.map { s ->
            if (s.id == studentId) s.copy(note = note) else s
        }
        updateStudents(updatedStudents)
    }

    fun favoriteTag(): StudentTag = favoriteTag
    fun priorityTag(): StudentTag = priorityTag
    fun taggedTag(): StudentTag = taggedTag

    private fun updateStudents(newAllStudents: List<WorkspaceStudent>) {
        val state = _state.value
        val keepSelected = state.bulkSelectedStudentIds
        val selectedStillExists = keepSelected.filter { id -> newAllStudents.any { it.id == id } }.toSet()
        _state.value = state.copy(
            students = newAllStudents,
            bulkSelectedStudentIds = selectedStillExists,
            isBulkMode = selectedStillExists.isNotEmpty()
        )
        recompute()
    }

    private fun recompute() {
        val state = _state.value
        val q = state.searchQuery.trim()

        val resources = if (q.isBlank()) allResources else allResources.filter {
            it.fileName.contains(q, ignoreCase = true) || it.uploadedBy.contains(q, ignoreCase = true)
        }

        val students = run {
            var list = allStudents
            if (q.isNotBlank()) {
                list = list.filter { it.name.contains(q, true) || it.rollNumber.contains(q, true) }
            }
            if (state.studentFilterTaggedOnly) list = list.filter { it.tags.isNotEmpty() }
            state.studentFilterEligible?.let { eligible ->
                list = list.filter { seededEligibility(it.id) == eligible }
            }
            state.studentFilterApplied?.let { applied ->
                list = list.filter { seededApplied(it.id) == applied }
            }
            list
        }

        val notes = if (q.isBlank()) allNotes else allNotes.filter {
            it.title.contains(q, true) || it.description.contains(q, true)
        }

        _state.value = state.copy(resources = resources, students = students, notes = notes)
    }

    private fun seededEligibility(id: String): Boolean = (id.hashCode() % 2 == 0)
    private fun seededApplied(id: String): Boolean = (id.hashCode() % 3 == 0)
}

private fun seededResources(): List<Resource> {
    val now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
    val uploads = listOf("Placement Cell", "TPO Office", "Faculty Mentor")
    val names = listOf(
        "Drive Results Sheet – Round 1.xlsx" to ResourceType.EXCEL,
        "Recruiter Guidelines.pdf" to ResourceType.PDF,
        "Student Consent Form.doc" to ResourceType.DOC,
        "Eligibility Matrix.xlsx" to ResourceType.EXCEL,
        "Offer Acceptance Process.pdf" to ResourceType.PDF,
        "Campus Drive Checklist.doc" to ResourceType.DOC
    )
    val random = Random(2026)
    return names.mapIndexed { idx, (name, type) ->
        val uploadedAt = now.minusDays((idx + 3).toLong()).minusHours((idx * 2).toLong())
        val lastOpenedAt = if (idx < 4) now.minusHours((idx + 1).toLong()) else null
        Resource(
            id = "res_$idx",
            fileName = name,
            fileType = type,
            uploadedBy = uploads[random.nextInt(uploads.size)],
            uploadedAt = uploadedAt,
            lastOpenedAt = lastOpenedAt
        )
    }
}

private fun seededStudents(): List<WorkspaceStudent> {
    val portraits = listOf(R.drawable.std_1, R.drawable.std_2, R.drawable.std_3, R.drawable.std_4)
    val names = listOf("Aarav Singh", "Meera Nair", "Rohan Das", "Kavya Patel", "Ishaan Rao", "Anaya Verma")
    val rolls = listOf("CSE-2201", "IT-2212", "ECE-2122", "ME-2018", "CE-2119", "EE-2210")
    val random = Random(91)
    return names.mapIndexed { idx, n ->
        WorkspaceStudent(
            id = "stu_$idx",
            name = n,
            rollNumber = rolls[idx],
            profileResId = portraits[random.nextInt(portraits.size)]
        )
    }
}

private fun seededNotes(): List<Note> {
    val now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
    return listOf(
        Note(
            id = "note_0",
            title = "Resume Checklist",
            description = "Quick checklist for formatting, links, and proofread rules.",
            fileLink = null,
            createdAt = now.minusDays(2)
        ),
        Note(
            id = "note_1",
            title = "Interview Prep – Core Concepts",
            description = "Shared revision points + topic buckets for fast review.",
            fileLink = "workspace://notes/interview-prep",
            createdAt = now.minusDays(6)
        ),
        Note(
            id = "note_2",
            title = "Drive Day Guidelines",
            description = "Arrival time, document checklist, and communication templates.",
            fileLink = "workspace://notes/drive-day",
            createdAt = now.minusDays(10)
        )
    )
}

