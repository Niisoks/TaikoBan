package com.example.taikoban.ui.common


import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taikoban.R

import com.example.taikoban.objects.Difficulty
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.DifficultyLevel.Companion.color
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import com.example.taikoban.objects.DifficultyLevel.Companion.name

import com.example.taikoban.objects.Genre
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.PassStatus.Companion.icon
import com.example.taikoban.objects.Score
import com.example.taikoban.objects.ScoreBoardEntry
import com.example.taikoban.objects.ScoreBoardSong
import com.example.taikoban.objects.Song
import com.example.taikoban.objects.User

@Preview
@Composable
fun ScoreBoardSongPreview() {
    val song = Song("Furi-furi♪Nori-nori♪", "フリフリ♪ノリノリ♪", Difficulty(1, 2, 3, 4, 5), "", "Session de Dodon ga Don", Genre.NAMCO_ORIGINAL)
    val user = User("uid1", "User1", null)
    val score = Score(
        good = 80,
        ok = 10,
        bad = 5,
        combo = 500,
        drumRoll = 800,
        tamaashiGauge = 0.75f,
        passStatus = PassStatus.FULL_COMBO,
        difficultyLevel = DifficultyLevel.HARD
    )
    val scoreBoardEntry = ScoreBoardEntry(score, user)
    val scoreBoardSong = ScoreBoardSong(song, mutableListOf(scoreBoardEntry))
    TaikoCard(scoreBoardSong = scoreBoardSong)
}

@Composable
fun ScoreBoardSongView(scoreBoardSong: ScoreBoardSong) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaikoCard(
    modifier: Modifier = Modifier,
    scoreBoardSong: ScoreBoardSong
    ){
    val border = 3.dp
    Card(modifier = modifier,
        border = BorderStroke(border, Color(0xff131114)),
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier.padding(border),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("")
            Text(scoreBoardSong.song.enName)
            Text("")
            Row{

            }
        }
    }
}

@Preview(widthDp = 1000)
@Composable
fun DifficultyCardRow(
    modifier: Modifier = Modifier,
    difficulty: Difficulty = Difficulty(2, 4,  8, 9, 10)
){
    Row(modifier = modifier){
        DifficultyButton(difficulty = difficulty.easy, difficultyLevel = DifficultyLevel.EASY, passStatus = PassStatus.FULL_COMBO)
        DifficultyButton(difficulty = difficulty.medium, difficultyLevel = DifficultyLevel.MEDIUM, passStatus = PassStatus.DONDER_FULL_COMBO)
        DifficultyButton(difficulty = difficulty.hard, difficultyLevel = DifficultyLevel.HARD, passStatus = PassStatus.FULL_COMBO)
        DifficultyButton(difficulty = difficulty.extreme, difficultyLevel = DifficultyLevel.EXTREME, passStatus = PassStatus.FULL_COMBO)
        difficulty.extraExtreme?.let { DifficultyButton(difficulty = it, difficultyLevel = DifficultyLevel.EXTRA_EXTREME, passStatus = PassStatus.FULL_COMBO) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DifficultyButton(
    modifier: Modifier = Modifier,
    difficulty: Int = 3,
    difficultyLevel: DifficultyLevel = DifficultyLevel.EASY,
    passStatus: PassStatus = PassStatus.FAIL
){
    Card(onClick = {}) {
        Column(
            modifier = modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            difficultyLevel.color().copy(alpha = 0.3f),
                            difficultyLevel.color()
                        )
                    )
                )
                .width(intrinsicSize = IntrinsicSize.Min)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(
                (-11).dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                ) {
                    Spacer(modifier = Modifier.padding(3.dp))
                    Image(
                        painter = painterResource(id = difficultyLevel.icon()),
                        contentDescription = "",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Image(
                        painter = painterResource(id = passStatus.icon()),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontSize = 12.sp,
                    text = stringResource(difficultyLevel.name()),
                    color = Color.White,
                    style = TextStyle.Default.copy(
                        shadow = Shadow(offset = Offset(1f, 1f))
                    )
                )
            }
            StarBar(
                stars = difficulty
            )
        }
    }
}

@Composable
fun StarBar(
    modifier: Modifier = Modifier,
    stars: Int = 4
){
    Row(modifier = modifier, verticalAlignment = Alignment.Bottom){
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            fontSize = if(stars >= 10) 10.sp else 14.sp,
            modifier = Modifier
                .padding(top = 1.dp, start = 1.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = this.size.minDimension
                    )
                },
            text = stars.toString(),
            color = Color.White,

        )
        Row(
            modifier = Modifier
                .padding(bottom = 3.dp)
                .background(color = Color(0x26000000), shape = RoundedCornerShape(4.dp))
        ) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            for (i in 0..9) {
                if (i < stars) {
                    Icon(
                        painterResource(id = R.drawable.star),
                        contentDescription = "",
                        modifier = Modifier
                            .width(10.dp),
                        tint = Color.White
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.star),
                        contentDescription = "",
                        modifier = Modifier
                            .width(10.dp),
                        tint = Color(0x26000000)
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreButton(
    modifier: Modifier = Modifier,
    scoreEntry: ScoreBoardEntry
){

}