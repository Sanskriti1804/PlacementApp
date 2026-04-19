package com.example.placementprojectmp.data.local

object DummyJobRoleMapping {
    val domainJobRoleMap: Map<String, List<String>> = mapOf(
        "SOFTWARE DEVELOPMENT" to listOf("Android Developer", "Backend Developer", "Frontend Developer", "Full Stack Developer"),
        "DATA SCIENCE" to listOf("Data Analyst", "Data Scientist", "Machine Learning Engineer", "Business Intelligence Analyst"),
        "ARTIFICIAL INTELLIGENCE" to listOf("AI Engineer", "Machine Learning Engineer", "Deep Learning Engineer", "NLP Engineer"),
        "CYBER SECURITY" to listOf("Security Analyst", "Penetration Tester", "Cyber Security Engineer", "Security Consultant"),
        "CLOUD COMPUTING" to listOf("Cloud Engineer", "Cloud Architect", "AWS Developer", "Cloud Security Engineer"),
        "DEVOPS" to listOf("DevOps Engineer", "Site Reliability Engineer", "Build Engineer", "Release Engineer"),
        "EMBEDDED SYSTEMS" to listOf("Embedded Engineer", "Firmware Developer", "Hardware Engineer", "IoT Embedded Developer"),
        "NETWORK ENGINEERING" to listOf("Network Engineer", "Network Administrator", "System Engineer", "Network Security Engineer"),
        "BLOCKCHAIN" to listOf("Blockchain Developer", "Smart Contract Developer", "Crypto Analyst", "Blockchain Architect"),
        "IOT" to listOf("IoT Developer", "IoT Solutions Architect", "Embedded IoT Engineer", "IoT Analyst"),

        "MARKETING" to listOf("Digital Marketing Executive", "SEO Specialist", "Brand Manager", "Content Strategist"),
        "FINANCE" to listOf("Financial Analyst", "Investment Analyst", "Risk Analyst", "Finance Manager"),
        "HUMAN RESOURCES" to listOf("HR Executive", "Talent Acquisition Specialist", "HR Manager", "Training Coordinator"),
        "OPERATIONS" to listOf("Operations Manager", "Supply Chain Analyst", "Logistics Manager", "Process Analyst"),
        "BUSINESS ANALYTICS" to listOf("Business Analyst", "Data Analyst", "Product Analyst", "Strategy Analyst"),
        "PRODUCT MANAGEMENT" to listOf("Product Manager", "Associate Product Manager", "Product Owner", "Product Analyst"),
        "SALES" to listOf("Sales Executive", "Business Development Executive", "Account Manager", "Sales Manager"),
        "ENTREPRENEURSHIP" to listOf("Startup Founder", "Business Consultant", "Venture Analyst", "Innovation Manager"),

        "WEB DEVELOPMENT" to listOf("Frontend Developer", "Backend Developer", "Full Stack Developer", "Web Designer"),
        "MOBILE DEVELOPMENT" to listOf("Android Developer", "iOS Developer", "Flutter Developer", "React Native Developer"),
        "DATABASE MANAGEMENT" to listOf("Database Administrator", "SQL Developer", "Data Engineer", "Database Architect"),
        "SYSTEM DESIGN" to listOf("System Architect", "Backend Engineer", "Solutions Architect", "Software Engineer"),
        "SOFTWARE TESTING" to listOf("QA Engineer", "Test Engineer", "Automation Tester", "Performance Tester"),
        "UI-UX DESIGN" to listOf("UI Designer", "UX Designer", "Product Designer", "Interaction Designer"),

        "CORPORATE LAW" to listOf("Corporate Lawyer", "Legal Advisor", "Compliance Officer", "Contract Specialist"),
        "CRIMINAL LAW" to listOf("Criminal Lawyer", "Public Prosecutor", "Legal Consultant", "Defense Lawyer"),
        "INTELLECTUAL PROPERTY LAW" to listOf("IP Lawyer", "Patent Analyst", "Trademark Attorney", "IP Consultant"),
        "CYBER LAW" to listOf("Cyber Lawyer", "Data Privacy Consultant", "Legal Analyst", "Compliance Specialist"),
        "INTERNATIONAL LAW" to listOf("International Lawyer", "Legal Advisor", "Policy Analyst", "Diplomatic Consultant"),
        "CONSTITUTIONAL LAW" to listOf("Constitutional Lawyer", "Legal Scholar", "Policy Advisor", "Advocate"),

        "PHYSICS RESEARCH" to listOf("Research Scientist", "Lab Technician", "Physics Analyst", "Academic Researcher"),
        "CHEMISTRY RESEARCH" to listOf("Chemist", "Lab Analyst", "Research Scientist", "Quality Control Analyst"),
        "MATHEMATICS" to listOf("Mathematician", "Data Analyst", "Statistician", "Quantitative Analyst"),
        "ENVIRONMENTAL SCIENCE" to listOf("Environmental Scientist", "Sustainability Analyst", "Climate Researcher", "Environmental Consultant"),
        "BIOTECHNOLOGY" to listOf("Biotech Engineer", "Research Scientist", "Lab Technician", "Clinical Research Associate"),
        "MICROBIOLOGY" to listOf("Microbiologist", "Lab Scientist", "Research Associate", "Quality Analyst"),

        "ARCHITECTURE DESIGN" to listOf("Architect", "Architectural Designer", "Landscape Architect", "Design Consultant"),
        "URBAN PLANNING" to listOf("Urban Planner", "City Planner", "GIS Analyst", "Planning Consultant"),

        "INTERIOR DESIGN" to listOf("Interior Designer", "Space Planner", "Design Consultant", "Visual Merchandiser"),
        "INDUSTRIAL DESIGN" to listOf("Industrial Designer", "Product Designer", "CAD Designer", "Design Engineer"),
        "GRAPHIC DESIGN" to listOf("Graphic Designer", "Visual Designer", "Brand Designer", "Illustrator"),
        "PRODUCT DESIGN" to listOf("Product Designer", "UX Designer", "Design Strategist", "Innovation Designer"),

        "RESEARCH" to listOf("Research Analyst", "Research Associate", "Scientist", "Policy Researcher"),
        "CONSULTING" to listOf("Business Consultant", "Strategy Consultant", "Management Consultant", "Operations Consultant"),
        "EDUCATION" to listOf("Teacher", "Lecturer", "Academic Coordinator", "Curriculum Designer"),
        "PUBLIC POLICY" to listOf("Policy Analyst", "Policy Advisor", "Government Consultant", "Research Fellow"),
        "NGO DEVELOPMENT" to listOf("Program Manager", "NGO Coordinator", "Social Worker", "Development Consultant"),
        "FREELANCING" to listOf("Freelance Developer", "Freelance Designer", "Content Writer", "Consultant"),

        "CONTENT WRITING" to listOf("Content Writer", "Copywriter", "Technical Writer", "Blogger"),
        "JOURNALISM" to listOf("Journalist", "News Reporter", "Editor", "Media Analyst"),
        "PSYCHOLOGY" to listOf("Psychologist", "Counselor", "Therapist", "Behavioral Analyst"),
        "SOCIOLOGY" to listOf("Sociologist", "Social Researcher", "Policy Analyst", "Field Researcher"),
        "POLITICAL SCIENCE" to listOf("Political Analyst", "Policy Advisor", "Researcher", "Public Affairs Specialist"),
        "LITERATURE" to listOf("Writer", "Editor", "Literary Critic", "Publisher"),

        "ACCOUNTING" to listOf("Accountant", "Financial Accountant", "Cost Accountant", "Accounts Executive"),
        "TAXATION" to listOf("Tax Consultant", "Tax Analyst", "GST Practitioner", "Tax Advisor"),
        "AUDITING" to listOf("Auditor", "Internal Auditor", "Compliance Auditor", "Audit Associate"),
        "INVESTMENT BANKING" to listOf("Investment Banker", "Equity Analyst", "Financial Analyst", "Portfolio Manager"),
        "ECONOMICS" to listOf("Economist", "Economic Analyst", "Research Analyst", "Policy Economist"),
        "BUSINESS LAW" to listOf("Business Lawyer", "Legal Advisor", "Compliance Officer", "Contract Manager")
    )

    fun getJobRolesForDomain(domain: String): List<String> {
        if (domain.isBlank()) return emptyList()
        val normalized = domain.trim().uppercase()
        // Replace underscore with space for fallback matching if needed, though exact match should work.
        return domainJobRoleMap[normalized] ?: domainJobRoleMap[normalized.replace("_", " ")].orEmpty()
    }
}
