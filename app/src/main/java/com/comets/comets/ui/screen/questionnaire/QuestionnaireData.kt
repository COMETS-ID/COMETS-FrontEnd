package com.comets.comets.ui.screen.questionnaire

data class QuestionnaireQuestion(
    val question: String,
    val answers: List<QuestionnaireAnswer>,
)

data class QuestionnaireAnswer(
    val score: Int,
    val text: String,
)

val questionnaireList = listOf(
    QuestionnaireQuestion(
        question = "How did you wake up feeling this morning?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Very tired and sluggish"
            ),
            QuestionnaireAnswer(
                2,
                "Tired"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Refreshed"
            ),
            QuestionnaireAnswer(
                5,
                "Energetic"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "How would you describe your general outlook on the day ahead?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Extremely pessimistic"
            ),
            QuestionnaireAnswer(
                2,
                "Pessimistic"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Optimistic"
            ),
            QuestionnaireAnswer(
                5,
                "Extremely optimistic"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "Are there any specific stressors or challenges you are anticipating today?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Many significant stressors"
            ),
            QuestionnaireAnswer(
                2,
                "Some stressors"
            ),
            QuestionnaireAnswer(
                3,
                "A few minor stressors"
            ),
            QuestionnaireAnswer(
                4,
                "Minimal stressors"
            ),
            QuestionnaireAnswer(
                5,
                "No anticipated stressors"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "How well-rested do you feel right now?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Very fatigued"
            ),
            QuestionnaireAnswer(
                2,
                "Fatigued"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Well-rested"
            ),
            QuestionnaireAnswer(
                5,
                "Extremely well-rested"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "How satisfied are you with your morning routine so far?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Very dissatisfied"
            ),
            QuestionnaireAnswer(
                2,
                "Dissatisfied"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Satisfied"
            ),
            QuestionnaireAnswer(
                5,
                "Very satisfied"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "Do you feel a sense of accomplishment from any tasks completed today?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Not at all"
            ),
            QuestionnaireAnswer(
                2,
                "A little"
            ),
            QuestionnaireAnswer(
                3,
                "Moderately"
            ),
            QuestionnaireAnswer(
                4,
                "Quite a bit"
            ),
            QuestionnaireAnswer(
                5,
                "A great deal"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "How connected do you feel with the people around you today?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Very isolated"
            ),
            QuestionnaireAnswer(
                2,
                "Somewhat isolated"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Connected"
            ),
            QuestionnaireAnswer(
                5,
                "Very connected"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "Have you experienced any enjoyable moments or positive surprises today?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "None at all"
            ),
            QuestionnaireAnswer(
                2,
                "A few"
            ),
            QuestionnaireAnswer(
                3,
                "Some"
            ),
            QuestionnaireAnswer(
                4,
                "Many"
            ),
            QuestionnaireAnswer(
                5,
                "A continuous stream"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "To what extent have you been able to manage and cope with your emotions today?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Poorly"
            ),
            QuestionnaireAnswer(
                2,
                "Somewhat poorly"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Well"
            ),
            QuestionnaireAnswer(
                5,
                "Extremely well"
            )
        )
    ),
    QuestionnaireQuestion(
        question = "As the day is progressing, how would you rate your overall mood right now?",
        answers = listOf(
            QuestionnaireAnswer(
                1,
                "Very negative"
            ),
            QuestionnaireAnswer(
                2,
                "Negative"
            ),
            QuestionnaireAnswer(
                3,
                "Neutral"
            ),
            QuestionnaireAnswer(
                4,
                "Positive"
            ),
            QuestionnaireAnswer(
                5,
                "Very positive"
            )
        )
    )
)