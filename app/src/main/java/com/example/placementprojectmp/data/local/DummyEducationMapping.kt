package com.example.placementprojectmp.data.local

/**
 * Temporary dummy provider for Course -> Domain mapping.
 * Remove this file when backend meta mapping is fully restored.
 */
object DummyEducationMapping {

    private val engineeringDomains = listOf(
        "SOFTWARE DEVELOPMENT",
        "DATA SCIENCE",
        "ARTIFICIAL INTELLIGENCE",
        "CYBER SECURITY",
        "CLOUD COMPUTING",
        "DEVOPS",
        "EMBEDDED SYSTEMS",
        "NETWORK ENGINEERING",
        "BLOCKCHAIN",
        "IOT"
    )

    private val managementDomains = listOf(
        "MARKETING",
        "FINANCE",
        "HUMAN RESOURCES",
        "OPERATIONS",
        "BUSINESS ANALYTICS",
        "PRODUCT MANAGEMENT",
        "SALES",
        "ENTREPRENEURSHIP"
    )

    private val computerApplicationsDomains = listOf(
        "WEB DEVELOPMENT",
        "MOBILE DEVELOPMENT",
        "DATABASE MANAGEMENT",
        "SYSTEM DESIGN",
        "SOFTWARE TESTING",
        "UI-UX DESIGN"
    )

    private val lawDomains = listOf(
        "CORPORATE LAW",
        "CRIMINAL LAW",
        "INTELLECTUAL PROPERTY LAW",
        "CYBER LAW",
        "INTERNATIONAL LAW",
        "CONSTITUTIONAL LAW"
    )

    private val scienceDomains = listOf(
        "PHYSICS RESEARCH",
        "CHEMISTRY RESEARCH",
        "MATHEMATICS",
        "ENVIRONMENTAL SCIENCE",
        "BIOTECHNOLOGY",
        "MICROBIOLOGY"
    )

    private val architectureDomains = listOf(
        "ARCHITECTURE DESIGN",
        "URBAN PLANNING"
    )

    private val designDomains = listOf(
        "INTERIOR DESIGN",
        "INDUSTRIAL DESIGN",
        "GRAPHIC DESIGN",
        "PRODUCT DESIGN"
    )

    private val professionalDomains = listOf(
        "RESEARCH",
        "CONSULTING",
        "EDUCATION",
        "PUBLIC_POLICY",
        "NGO_DEVELOPMENT",
        "FREELANCING"
    )

    private val artsDomains = listOf(
        "CONTENT WRITING",
        "JOURNALISM",
        "PSYCHOLOGY",
        "SOCIOLOGY",
        "POLITICAL SCIENCE",
        "LITERATURE"
    )

    private val commerceDomains = listOf(
        "ACCOUNTING",
        "TAXATION",
        "AUDITING",
        "INVESTMENT BANKING",
        "ECONOMICS",
        "BUSINESS LAW"
    )

    private val courseDomainMap: Map<String, List<String>> = mapOf(
        "BTECH" to engineeringDomains,
        "BE" to engineeringDomains,
        "MTECH" to engineeringDomains,
        "ME" to engineeringDomains,

        "BBA" to managementDomains,
        "MBA" to managementDomains,
        "BMS" to managementDomains,
        "PGDM" to managementDomains,

        "BCA" to computerApplicationsDomains,
        "MCA" to computerApplicationsDomains,

        "LLB" to lawDomains,
        "BA_LLB" to lawDomains,
        "BBA_LLB" to lawDomains,
        "LLM" to lawDomains,

        "BSC" to scienceDomains,
        "MSC" to scienceDomains,
        "BSC_IT" to listOf("SOFTWARE DEVELOPMENT", "DATABASE MANAGEMENT"),
        "BSC_CS" to listOf("SOFTWARE DEVELOPMENT", "DATABASE MANAGEMENT"),
        "BSC_BIOTECH" to listOf("BIOTECHNOLOGY", "MICROBIOLOGY"),

        "BARCH" to architectureDomains,
        "MARCH" to architectureDomains,
        "BDES" to designDomains,
        "MDES" to designDomains,

        "PG_DIPLOMA" to professionalDomains,
        "INTEGRATED_COURSE" to professionalDomains,

        "BA" to artsDomains,
        "MA" to artsDomains,

        "BCOM" to commerceDomains,
        "MCOM" to commerceDomains
    )

    fun getAllCourses(): List<String> = courseDomainMap.keys.toList()

    fun getDomainsForCourse(course: String): List<String> {
        if (course.isBlank()) return emptyList()
        val normalized = course.trim().uppercase()
        return courseDomainMap[normalized].orEmpty()
    }
}
