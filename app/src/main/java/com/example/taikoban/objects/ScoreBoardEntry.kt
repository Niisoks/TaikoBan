package com.example.taikoban.objects

import kotlin.random.Random


private fun generateRandomForTest() {
    val songs = listOf(
        Song("Song1", "歌曲1", Difficulty(1, 2, 3, 4, 5)),
        Song("Song2", "歌曲2", Difficulty(2, 3, 4, 5, 6)),
        Song("Song3", "歌曲3", Difficulty(3, 4, 5, 6, 7)),
        Song("Song4", "歌曲4", Difficulty(4, 5, 6, 7, 8)),
        Song("Song5", "歌曲5", Difficulty(5, 6, 7, 8, 9))
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
)

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

enum class PassStatus{
    DONDER_FULL_COMBO,
    FULL_COMBO,
    PASS,
    FAIL
}

enum class DifficultyLevel{
    EASY,
    MEDIUM,
    HARD,
    EXTREME,
    EXTRA_EXTREME
}

data class Difficulty(
    val easy: Int, // Kantan
    val medium: Int, // Futsuu
    val hard: Int, // Muzukashii
    val extreme: Int, // Oni
    val extraExtreme: Int? // Oni+?
)
