package com.example.taikoban.objects

import androidx.compose.ui.graphics.Color
import com.example.taikoban.R
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import kotlin.random.Random


data class ScoreBoardSong(
    val song: Song,
    val scoreBoardEntries: MutableList<ScoreBoardEntry>
)

data class User(
    val uid: String,
    val name: String,
    val profileIcon: Int?,
)

data class ScoreBoardEntry(
    val score: Score,
    val user: User
)

data class Song(
    val enName: String,
    val jpName: String, // Not using a string resource, because it is probably going to come from a server instead of being saved in app?
    val difficultyRating: Difficulty,
    val artist: String,
    val fromSeries: String,
    val genre: Genre
)

enum class Genre {
    POP,
    ANIME,
    VOCALOID_MUSIC,
    VARIETY,
    CLASSICAL,
    GAME_MUSIC,
    NAMCO_ORIGINAL;

    companion object{
        fun Genre.getColor(): Color{
            return when(this){
                POP -> Color(0xff2c9bb4)
                ANIME -> Color(0xffef951a)
                VOCALOID_MUSIC -> Color(0xffc4c9db)
                VARIETY -> Color(0xff8cc832)
                CLASSICAL -> Color(0xffc69e29)
                GAME_MUSIC -> Color(0xff9a76b4)
                NAMCO_ORIGINAL -> Color(0xffee5e26)
            }
        }

        fun Genre.getName(): Int {
            return when(this){
                POP -> R.string.pop
                ANIME -> R.string.anime
                VOCALOID_MUSIC -> R.string.vocaloid
                VARIETY -> R.string.variety
                CLASSICAL -> R.string.classical
                GAME_MUSIC -> R.string.gameMusic
                NAMCO_ORIGINAL -> R.string.namcoOriginal
            }
        }
    }
}

data class Score(
    val good: Int,
    val ok: Int,
    val bad: Int,
    val combo: Int,
    val drumRoll: Int,
    val tamaashiGauge: Float, // 1 being 100%, 0 being 0%
    val passStatus: PassStatus,
    val difficultyLevel: DifficultyLevel
)

data class SongDifficultyStatus(
    val easy: PassStatus = PassStatus.FAIL,
    val medium: PassStatus = PassStatus.FAIL,
    val hard: PassStatus = PassStatus.FAIL,
    val extreme: PassStatus = PassStatus.FAIL,
    val extraExtreme: PassStatus = PassStatus.FAIL
)

enum class PassStatus{
    DONDER_FULL_COMBO,
    FULL_COMBO,
    PASS,
    FAIL;
    companion object{
        fun PassStatus.icon(): Int{
            return when(this){
                DONDER_FULL_COMBO -> R.drawable.crown_rainbow
                FULL_COMBO -> R.drawable.crown_gold
                PASS -> R.drawable.crown_silver
                FAIL -> R.drawable.crown_empty
            }
        }
    }
}

enum class DifficultyLevel{
    EASY,
    MEDIUM,
    HARD,
    EXTREME,
    EXTRA_EXTREME;

    companion object{

        fun DifficultyLevel.color(): Color{
            return when(this){
                EASY -> Color(0xFFd13116)
                MEDIUM -> Color(0xFF7a9b23)
                HARD -> Color(0xFF307699)
                EXTREME -> Color(0xFFb22a81)
                EXTRA_EXTREME -> Color(0xFF4d377d)
            }
        }
        fun DifficultyLevel.icon(): Int{
            return when(this){
                EASY -> R.drawable.easy
                MEDIUM -> R.drawable.normal
                HARD -> R.drawable.hard
                EXTREME -> R.drawable.extreme
                EXTRA_EXTREME -> R.drawable.extra_extreme
            }
        }

        fun DifficultyLevel.name(): Int {
            return when(this){
                EASY -> R.string.easy
                MEDIUM -> R.string.medium
                HARD -> R.string.hard
                EXTREME -> R.string.extreme
                EXTRA_EXTREME -> R.string.extraExtreme
            }
        }
    }
}

data class Difficulty(
    val easy: Int, // Kantan
    val medium: Int, // Futsuu
    val hard: Int, // Muzukashii
    val extreme: Int, // Oni
    val extraExtreme: Int? // Oni+?
)
