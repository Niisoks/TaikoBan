package com.example.taikoban.objects

import androidx.compose.ui.graphics.Color
import com.example.taikoban.R
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import kotlin.random.Random


fun generateRandomForTest(): MutableList<ScoreBoardSong> {
    val songs = listOf(
        Song("Rolling Star", "ローリンスター", Difficulty(2, 3, 4, 4, 5), "YUI", "", Genre.POP),
        Song("Gurenge", "紅蓮華", Difficulty(1, 3, 4, 5, 5), "LiSA", "", Genre.ANIME),
        Song("Senbonzakura", "千本桜", Difficulty(2, 3, 4, 4, 5), "Kurousa-P ft. Hatsune Miku", "", Genre.VOCALOID_MUSIC),
        Song("Koi Dance", "恋ダンス", Difficulty(1, 2, 3, 4, 5), "Dream5", "", Genre.VARIETY),
        Song("Canon in D", "カノン", Difficulty(1, 2, 3, 4, 4), "Johann Pachelbel", "", Genre.CLASSICAL),
        Song("Snake Eater", "スネークイーター", Difficulty(3, 4, 4, 5, 5), "Cynthia Harrell", "Metal Gear Solid", Genre.GAME_MUSIC),
        Song("Tales of the Abyss Medley", "テイルズオブジアビスメドレー", Difficulty(3, 4, 4, 5, 5), "Motoi Sakuraba", "Tales of the Abyss", Genre.GAME_MUSIC),
        Song("Wai Wai World", "ワイワイワールド", Difficulty(2, 3, 4, 5, 5), "Unknown", "Wai Wai World", Genre.NAMCO_ORIGINAL),
        Song("Butterfly", "バタフライ", Difficulty(1, 2, 3, 4, 5), "Koji Wada", "Digimon Adventure", Genre.ANIME),
        Song("Tetris Theme", "テトリス", Difficulty(1, 2, 3, 4, 5), "Unknown", "Tetris", Genre.GAME_MUSIC)
    )

    val users = mutableListOf<User>()

    // Generate up to 5 users
    repeat(Random.nextInt(1, 6)) {
        users.add(User("uid$it", "User${Random.nextInt(100)}", null)) // Assigning a random name
    }

    val scoreBoardSongs = mutableListOf<ScoreBoardSong>()

    // Generate up to 10 ScoreBoardSongs
    repeat(Random.nextInt(1, 11)) {
        val song = songs.random()
        val scoreBoardEntries = mutableListOf<ScoreBoardEntry>()

        // Randomly assign scores to users
        users.forEach { user ->
            val score = Score(
                Random.nextInt(0, 101),
                Random.nextInt(0, 101),
                Random.nextInt(0, 101),
                Random.nextInt(0, 1001),
                Random.nextInt(0, 1001),
                Random.nextFloat(),
                PassStatus.values().random(),
                DifficultyLevel.values().random()
            )
            scoreBoardEntries.add(ScoreBoardEntry(score, user))
        }

        scoreBoardSongs.add(ScoreBoardSong(song, scoreBoardEntries))
    }

    // Printing generated data for verification
    scoreBoardSongs.forEachIndexed { index, scoreBoardSong ->
        println("Song ${index + 1}: ${scoreBoardSong.song.enName} (${scoreBoardSong.song.jpName})")
        scoreBoardSong.scoreBoardEntries.forEach { entry ->
            println("   User: ${entry.user.name}, Score: ${entry.score}")
        }
    }
    return scoreBoardSongs
}

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
){}

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
