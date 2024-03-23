package com.example.taikoban.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taikoban.objects.Difficulty
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.Genre
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.Score
import com.example.taikoban.objects.ScoreBoardEntry
import com.example.taikoban.objects.ScoreBoardSong
import com.example.taikoban.objects.Song
import com.example.taikoban.objects.User
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class LocalScoreBoardViewModel : ViewModel(){

    val scoreBoard: MutableState<List<ScoreBoardSong>> = mutableStateOf(listOf())
    val filteredScoreBoard: MutableState<List<ScoreBoardSong>> = mutableStateOf(listOf())

    val currentGenre: MutableState<Genre> = mutableStateOf(Genre.POP)
    val selectedGenre: MutableState<Genre?> = mutableStateOf(Genre.POP)

    val cardWidth: MutableState<Dp?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            scoreBoard.value = generateTestScoreBoard()
            filteredScoreBoard.value = scoreBoard.value
        }
    }

//    fun filterScoreBoard(genre: Genre){
//        viewModelScope.launch {
////            filteredScoreBoard.value = scoreBoard.value.filter { it.song.genre == genre }
//            currentFilter.value = genre
//        }
//    }

    fun generateTestScoreBoard(): List<ScoreBoardSong> {
        val songs = listOf(
            Song("${UUID.randomUUID()}","Rolling Star", "ローリンスター", Difficulty(2, 3, 4, 4, 5), "YUI", "", Genre.POP),
            Song("${UUID.randomUUID()}","Gurenge", "紅蓮華", Difficulty(1, 3, 4, 5, 5), "LiSA", "", Genre.ANIME),
            Song("${UUID.randomUUID()}","Senbonzakura", "千本桜", Difficulty(2, 3, 4, 4, 5), "Kurousa-P ft. Hatsune Miku", "", Genre.VOCALOID_MUSIC),
            Song("${UUID.randomUUID()}","Koi Dance", "恋ダンス", Difficulty(1, 2, 3, 4, 5), "Dream5", "", Genre.VARIETY),
            Song("${UUID.randomUUID()}","Canon in D", "カノン", Difficulty(1, 2, 3, 4, 4), "Johann Pachelbel", "", Genre.CLASSICAL),
            Song("${UUID.randomUUID()}","Snake Eater", "スネークイーター", Difficulty(3, 4, 4, 5, 5), "Cynthia Harrell", "Metal Gear Solid", Genre.GAME_MUSIC),
            Song("${UUID.randomUUID()}","Tales of the Abyss Medley", "テイルズオブジアビスメドレー", Difficulty(3, 4, 4, 5, 5), "Motoi Sakuraba", "Tales of the Abyss", Genre.GAME_MUSIC),
            Song("${UUID.randomUUID()}","Wai Wai World", "ワイワイワールド", Difficulty(2, 3, 4, 5, 5), "Unknown", "Wai Wai World", Genre.NAMCO_ORIGINAL),
            Song("${UUID.randomUUID()}","Butterfly", "バタフライ", Difficulty(1, 2, 3, 4, 5), "Koji Wada", "Digimon Adventure", Genre.ANIME),
            Song("${UUID.randomUUID()}","Tetris Theme", "テトリス", Difficulty(1, 2, 3, 4, 5), "Unknown", "Tetris", Genre.GAME_MUSIC),
            Song("${UUID.randomUUID()}","Carmen Prelude", "カルメン前奏曲", Difficulty(3, 5, 7, 8, 9), "Georges Bizet", "", Genre.CLASSICAL),
            Song("${UUID.randomUUID()}","Cruel Angel's Thesis", "残酷な天使のテーゼ", Difficulty(2, 4, 6, 8, 9), "Yoko Takahashi", "Neon Genesis Evangelion", Genre.ANIME),
            Song("${UUID.randomUUID()}","Doraemon Theme", "ドラえもんのうた", Difficulty(3, 5, 6, 7, 8), "Hiroshi Sekiguchi", "Doraemon", Genre.ANIME),
            Song("${UUID.randomUUID()}","Dragon Ball Z Opening", "ドラゴンボールZ オープニング", Difficulty(3, 5, 6, 8, 9), "Hironobu Kageyama", "Dragon Ball Z", Genre.ANIME),
            Song("${UUID.randomUUID()}","El Condor Pasa", "エル・コンドル・パサ", Difficulty(3, 4, 6, 7, 8), "Daniel Alomía Robles", "", Genre.VARIETY),
            Song("${UUID.randomUUID()}","Gimme Chocolate!!", "ギミチョコ！！", Difficulty(2, 4, 6, 8, 9), "BABYMETAL", "", Genre.VARIETY),
            Song("${UUID.randomUUID()}","Go Go Mario", "ゴーゴーマリオ", Difficulty(3, 5, 7, 8, 9), "Koji Kondo", "Super Mario", Genre.GAME_MUSIC),
            Song("${UUID.randomUUID()}","God Knows", "ゴッド・ノウズ", Difficulty(3, 5, 6, 8, 9), "Aya Hirano", "The Melancholy of Haruhi Suzumiya", Genre.ANIME),
            Song("${UUID.randomUUID()}","Hatsune Miku no Shoushitsu", "初音ミクの消失", Difficulty(4, 6, 7, 9, 10), "cosMo", "", Genre.VOCALOID_MUSIC),
            Song("${UUID.randomUUID()}","Himawari no Yakusoku", "ひまわりの約束", Difficulty(2, 4, 6, 7, 8), "Motohiro Hata", "Stand by Me Doraemon", Genre.ANIME),
            Song("${UUID.randomUUID()}","Hyadain no Kakakata☆Kataomoi-C", "ヒャダインのカカカタ☆カタオモイ-C", Difficulty(3, 5, 6, 7, 8), "Kenichi Maeyamada", "", Genre.VARIETY),
            Song("${UUID.randomUUID()}","Jupiter", "ジュピター", Difficulty(4, 6, 7, 8, 9), "Gustav Holst", "", Genre.CLASSICAL),
            Song("${UUID.randomUUID()}","Kimi no Shiranai Monogatari", "君の知らない物語", Difficulty(3, 5, 6, 7, 8), "Supercell", "Bakemonogatari", Genre.ANIME),
            Song("${UUID.randomUUID()}","Marisa Stole the Precious Thing", "マリサ・スティール・ザ・プレシャス・シング", Difficulty(3, 5, 6, 8, 9), "IOSYS", "", Genre.VARIETY),
            Song("${UUID.randomUUID()}","Melancholic", "メランコリック", Difficulty(4, 6, 7, 8, 9), "Junky", "", Genre.VOCALOID_MUSIC),
            Song("${UUID.randomUUID()}","Miku Miku ni Shite Ageru♪", "ミクミクにしてあげる♪", Difficulty(3, 5, 6, 7, 8), "ika", "", Genre.VOCALOID_MUSIC),
            Song("${UUID.randomUUID()}","Ren'ai Circulation", "恋愛サーキュレーション", Difficulty(2, 4, 6, 7, 8), "Kana Hanazawa", "Bakemonogatari", Genre.ANIME),
            Song("${UUID.randomUUID()}","Snow Halation", "スノーハルシネーション", Difficulty(3, 5, 6, 8, 9), "μ's", "Love Live!", Genre.ANIME),
            Song("${UUID.randomUUID()}","Sousei no Aquarion", "創聖のアクエリオン", Difficulty(3, 5, 6, 8, 9), "AKINO", "Genesis of Aquarion", Genre.ANIME),
            Song("${UUID.randomUUID()}","Yuzurenai Negai", "ゆずれない願い", Difficulty(3, 5, 6, 7, 8), "Naomi Tamura", "Magic Knight Rayearth", Genre.ANIME)
        )

        val users = mutableListOf<User>()

        // Generate up to 5 users
        repeat(Random.nextInt(1, 6)) {
            users.add(User("uid$it", "User${Random.nextInt(100)}", null)) // Assigning a random name
        }

        val scoreBoardSongs = mutableListOf<ScoreBoardSong>()

        // Generate up to 10 ScoreBoardSongs
        for (song in songs) {
            val scoreBoardEntries = mutableListOf<ScoreBoardEntry>()

            // Randomly assign scores to users
            users.forEach { user ->
                val score = Score(
                    Random.nextInt(0, 101),
                    Random.nextInt(0, 101),
                    Random.nextInt(0, 101),
                    Random.nextInt(0, 9999999),
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

        return scoreBoardSongs.sortedBy { it.song.genre }
    }
}