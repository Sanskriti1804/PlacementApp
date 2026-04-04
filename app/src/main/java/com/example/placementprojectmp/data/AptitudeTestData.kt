package com.example.placementprojectmp.data

/**
 * Dummy data for aptitude tests: 6 tests, each with 20 questions.
 * Used by Aptitude Test screens; no ViewModel.
 */
data class AptitudeTestMeta(
    val id: String,
    val title: String,
    val totalQuestions: Int = 20,
    val timeMinutes: Int = 20,
    val sections: List<String> = listOf("Quantitative Aptitude", "Logical Reasoning", "Verbal Ability")
)

data class AptitudeQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

/** Holder for current test session so Result screen can read answers without ViewModel. */
object AptitudeTestSessionHolder {
    var testId: String? = null
    var selectedAnswers: Map<Int, Int> = emptyMap()
}

object AptitudeTestRepository {
    private val testsMeta = listOf(
        AptitudeTestMeta("1", "Aptitude Test 1"),
        AptitudeTestMeta("2", "Aptitude Test 2"),
        AptitudeTestMeta("3", "Aptitude Test 3"),
        AptitudeTestMeta("4", "Aptitude Test 4"),
        AptitudeTestMeta("5", "Aptitude Test 5"),
        AptitudeTestMeta("6", "Aptitude Test 6")
    )

    fun getAllTests(): List<AptitudeTestMeta> = testsMeta

    fun getTest(testId: String): AptitudeTestMeta? = testsMeta.find { it.id == testId }

    fun getQuestions(testId: String): List<AptitudeQuestion> = dummyQuestions

    private val dummyQuestions: List<AptitudeQuestion> = listOf(
        AptitudeQuestion("What is the next number in the series? 2, 6, 12, 20, ?", listOf("28", "30", "32", "34"), 1, "The pattern increases by +4, +6, +8, +10. So next is 20+10=30."),
        AptitudeQuestion("If 15% of a number is 45, what is the number?", listOf("250", "300", "350", "400"), 1, "Number = 45 / 0.15 = 300."),
        AptitudeQuestion("The ratio of boys to girls in a class is 3:2. If there are 30 students, how many are girls?", listOf("10", "12", "15", "18"), 1, "Total parts = 5. Girls = (2/5) * 30 = 12."),
        AptitudeQuestion("All cats are animals. Some animals are black. Conclusion?", listOf("All cats are black", "Some cats may be black", "No cat is black", "None"), 1, "Some animals are black; cats are a subset of animals. So some cats may be black."),
        AptitudeQuestion("Choose the synonym of BRIEF:", listOf("Short", "Long", "Detailed", "Complex"), 0, "Brief means short in duration."),
        AptitudeQuestion("Find the next number: 1, 4, 9, 16, ?", listOf("20", "24", "25", "30"), 2, "Perfect squares: 1², 2², 3², 4², 5²=25."),
        AptitudeQuestion("A shirt costs Rs 400 after 20% discount. Original price?", listOf("480", "500", "520", "550"), 1, "Original = 400 / 0.8 = 500."),
        AptitudeQuestion("In a 2:3:4 ratio, the smallest share is 40. Total amount?", listOf("160", "180", "200", "220"), 1, "2 parts = 40, so 1 part = 20. Total 9 parts = 180."),
        AptitudeQuestion("If A is north of B and B is east of C, C is ___ of A?", listOf("North", "South", "South-west", "North-east"), 2, "B is east of C; A is north of B. So C is south-west of A."),
        AptitudeQuestion("Antonym of ANCIENT:", listOf("Old", "Modern", "Historic", "Classic"), 1, "Ancient means very old; modern is the opposite."),
        AptitudeQuestion("Series: 3, 6, 11, 18, ?", listOf("25", "27", "29", "31"), 1, "Differences: +3, +5, +7, +9. So 18+9=27."),
        AptitudeQuestion("25% of 80 is what percent of 100?", listOf("15", "20", "25", "30"), 1, "25% of 80 = 20. 20/100 = 20%."),
        AptitudeQuestion("Divide 60 in ratio 1:2:3.", listOf("10,20,30", "12,24,24", "15,20,25", "5,15,40"), 0, "1+2+3=6 parts. 60/6=10. So 10, 20, 30."),
        AptitudeQuestion("No bird is reptile. Some reptiles are snakes. Which is true?", listOf("All snakes are birds", "Some snakes are not birds", "No snake is reptile", "None"), 1, "Some reptiles are snakes; no bird is reptile. So some snakes are not birds."),
        AptitudeQuestion("Correct spelling:", listOf("Occurrence", "Occurence", "Ocurrence", "Occurrance"), 0, "Occurrence is the correct spelling."),
        AptitudeQuestion("2, 5, 10, 17, ?", listOf("24", "26", "28", "30"), 1, "Differences: +3, +5, +7, +9. 17+9=26."),
        AptitudeQuestion("A number increased by 20% gives 72. The number is?", listOf("58", "60", "62", "65"), 1, "1.2x = 72 => x = 60."),
        AptitudeQuestion("Speed ratio A:B = 2:3. Same distance. Time ratio?", listOf("2:3", "3:2", "4:9", "1:1"), 1, "Time = distance/speed. So time ratio is inverse: 3:2."),
        AptitudeQuestion("Some doctors are teachers. All teachers are scholars. Conclusion?", listOf("All doctors are scholars", "Some doctors are scholars", "No doctor is scholar", "None"), 1, "Some doctors are teachers; all teachers are scholars. So some doctors are scholars."),
        AptitudeQuestion("Fill in: She ___ to the market yesterday.", listOf("go", "went", "gone", "going"), 1, "Past tense of go is went.")
    ).let { base ->
        // Return 20 questions per test (repeat/expand if needed)
        (1..20).map { i -> base[(i - 1) % base.size] }
    }
}
