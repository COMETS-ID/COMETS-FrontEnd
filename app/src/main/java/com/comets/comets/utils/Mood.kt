package com.comets.comets.utils

import androidx.compose.ui.graphics.Color
import com.comets.comets.R

enum class Mood {
    HAPPY, NEUTRAL, SAD, ANGRY, DISGUST, FEARFUL, SURPRISED;

    val moodName: String
        get() {
            return when (this) {
                HAPPY -> "Happy"
                NEUTRAL -> "Neutral"
                SAD -> "Sad"
                DISGUST -> "Disgust"
                ANGRY -> "Angry"
                FEARFUL -> "Fearful"
                SURPRISED -> "Surprised"
            }
        }

    val moodDescription: String
        get() {
            return when (this) {
                HAPPY -> "It seems that you are happy right now. Good for you!"
                NEUTRAL -> "You seem to be in a neutral mood."
                SAD -> "It looks like you're feeling sad. Take care!"
                DISGUST -> "You appear to be disgusted by something."
                ANGRY -> "You seem to be angry. Take a deep breath and stay calm."
                FEARFUL -> "You seem to be feeling fearful. Everything will be okay."
                SURPRISED -> "You seem surprised! What happened?"
            }
        }
    val moodSelectedIcon: Int
        get() {
            return when (this) {
                HAPPY -> R.drawable.happy_fill
                NEUTRAL -> R.drawable.sick_fill
                SAD -> R.drawable.sad_fill
                DISGUST -> R.drawable.terror_fill
                ANGRY -> R.drawable.angry_fill
                FEARFUL -> R.drawable.terror_fill
                SURPRISED -> R.drawable.surprise_fill
            }
        }

    val moodUnselectedIcon: Int
        get() {
            return when (this) {
                HAPPY -> R.drawable.happy_line
                NEUTRAL -> R.drawable.sick_line
                SAD -> R.drawable.sad_line
                DISGUST -> R.drawable.terror_line
                ANGRY -> R.drawable.angry_line
                FEARFUL -> R.drawable.terror_line
                SURPRISED -> R.drawable.surprise_line
            }
        }

    val moodColor: Color
        get() {
            return when (this) {
                HAPPY -> Color(0xFFFFD94E)
                NEUTRAL -> Color(0xFFFFB946)
                SAD -> Color(0xFF4A6BD8)
                DISGUST -> Color(0xFFA1D88F)
                ANGRY -> Color(0xFFFA7573)
                FEARFUL -> Color(0xFF6D5C9E)
                SURPRISED -> Color(0xFF60C97E)
            }
        }
}