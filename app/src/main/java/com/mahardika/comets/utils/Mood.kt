package com.mahardika.comets.utils

import com.mahardika.comets.R

enum class Mood {
    HAPPY,
    NEUTRAL,
    SAD,
    ANGER,
    FEARFUL,
    SURPRISED;

    val moodName: String
        get() {
            return when(this) {
                HAPPY -> "Happy"
                NEUTRAL -> "Neutral"
                SAD -> "Sad"
                ANGER -> "Anger"
                FEARFUL -> "Fearful"
                SURPRISED -> "Surprised"
            }
        }
    val moodSelectedIcon: Int
        get()  {
            return when(this) {
                HAPPY -> R.drawable.happy_fill
                NEUTRAL -> R.drawable.sick_fill
                SAD -> R.drawable.sad_fill
                ANGER -> R.drawable.angry_fill
                FEARFUL -> R.drawable.terror_fill
                SURPRISED -> R.drawable.surprise_fill
            }
        }

    val moodUnselectedIcon: Int
        get()  {
            return when(this) {
                HAPPY -> R.drawable.happy_line
                NEUTRAL -> R.drawable.sick_line
                SAD -> R.drawable.sad_line
                ANGER -> R.drawable.angry_line
                FEARFUL -> R.drawable.terror_line
                SURPRISED -> R.drawable.surprise_line
            }
        }
}