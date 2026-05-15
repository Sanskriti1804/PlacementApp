package com.example.placementprojectmp.data.local

import com.example.placementprojectmp.data.local.StudentOpportunitiesFallbackData
import com.example.placementprojectmp.viewmodel.DriveUiModel
import com.example.placementprojectmp.viewmodel.JobUiModel

/**
 * Last known jobs/drives for student opportunities flows (Apply, Nav titles, submission store).
 * Populated from API via [com.example.placementprojectmp.viewmodel.StudentOpportunitiesViewModel]; seeded with dummy lists.
 */
object OpportunitiesCatalogHolder {
    @Volatile
    var jobs: List<JobUiModel> = StudentOpportunitiesFallbackData.jobs
        private set

    @Volatile
    var drives: List<DriveUiModel> = StudentOpportunitiesFallbackData.drives
        private set

    fun update(jobs: List<JobUiModel>, drives: List<DriveUiModel>) {
        if (jobs.isNotEmpty()) this.jobs = jobs
        if (drives.isNotEmpty()) this.drives = drives
    }
}
