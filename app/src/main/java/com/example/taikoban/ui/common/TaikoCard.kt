package com.example.taikoban.ui.common


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taikoban.R

import com.example.taikoban.objects.Difficulty
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.DifficultyLevel.Companion.color
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import com.example.taikoban.objects.DifficultyLevel.Companion.name
import com.example.taikoban.objects.Genre

import com.example.taikoban.objects.Genre.Companion.getColor
import com.example.taikoban.objects.Genre.Companion.getName
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.PassStatus.Companion.icon
import com.example.taikoban.objects.ScoreBoardEntry
import com.example.taikoban.objects.ScoreBoardSong
import com.example.taikoban.objects.SongDifficultyStatus
import com.example.taikoban.viewModels.LocalScoreBoardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun FilterList(viewModel: LocalScoreBoardViewModel = LocalScoreBoardViewModel()){
    val expanded = viewModel.currentFilter
    val state = rememberLazyListState()
    val coroutine = rememberCoroutineScope()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = state)
    LaunchedEffect(expanded.value) {
        coroutine.launch { state.animateScrollToItem(expanded.value.ordinal) }
    }
    LazyRow(
        state = state,
        flingBehavior = snapBehavior
    ) {
        item(){
            Spacer(modifier = Modifier.padding(horizontal = 24.dp))
        }
        items(Genre.values()){genre ->
            filterButton(
                expanded = expanded.value == genre,
                genre = genre,
                onClick = {
                    viewModel.filterScoreBoard(genre)
                }
            )
        }
        item(){
            Spacer(modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}
@Composable
fun filterButton(
    expanded: Boolean = true,
    genre: Genre = Genre.NAMCO_ORIGINAL,
    onClick: () -> Unit = {}
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = genre.getColor()
        ),
        border = BorderStroke(
            2.dp,
            Color.Black.copy(alpha = 0.2f)
        )
    ) {
        if(expanded){
            TaikoText(
                stringResource(genre.getName()),
                outlineSize = 10f
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ScoreBoardSongPreview(viewModel: LocalScoreBoardViewModel = LocalScoreBoardViewModel()) {
    val list = viewModel.filteredScoreBoard.value
    val currentGenre = viewModel.currentFilter.value
    val state = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = state)
    val firstVisibleGenre by remember {
        derivedStateOf {
            list[state.firstVisibleItemIndex].song.genre
        }
    }
    LaunchedEffect(key1 = currentGenre) {
        if(firstVisibleGenre != currentGenre){
            state.scrollToItem(list.indexOfFirst { it.song.genre == currentGenre })
        }
    }
    LaunchedEffect(key1 = firstVisibleGenre) {
        viewModel.currentFilter.value = firstVisibleGenre
    }
    LazyColumn(
        Modifier
            .fillMaxSize(),
        state = state,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        flingBehavior = snapBehavior
    ) {
        items(
            items = list,
            key = {it.song.uid}
        ){it ->
            TaikoCard(scoreBoardSong = it)
        }
    }
}

@Composable
fun TaikoText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    outlineSize: Float = 0f,
    outlineColor: Color = Color.Black
){
    Box {
        if (outlineSize > 0) {
            Text(
                text,
                modifier,
                outlineColor,
                fontSize,
                fontStyle,
                fontWeight,
                FontFamily(Font(R.font.taikonotatujinofficial)),
                letterSpacing,
                textDecoration,
                textAlign,
                lineHeight,
                overflow,
                softWrap,
                maxLines,
                onTextLayout,
                style = TextStyle.Default.copy(
                    fontSize = fontSize,
                    drawStyle = Stroke(
                        miter = 10f,
                        width = outlineSize,
                        join = StrokeJoin.Round
                    )
                )
            )
        }
        Text(
            text,
            modifier,
            if(color == Color.Unspecified) {
                if(outlineSize == 0f)
                    Color.Black
                else
                    Color.White
            } else color,
            fontSize,
            fontStyle,
            fontWeight,
            FontFamily(Font(R.font.taikonotatujinofficial)),
            letterSpacing,
            textDecoration,
            textAlign,
            lineHeight,
            overflow,
            softWrap,
            maxLines,
            onTextLayout,
            style
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaikoCard(
    modifier: Modifier = Modifier,
    scoreBoardSong: ScoreBoardSong
    ){
    Card(border = BorderStroke(3.dp, Color.Black.copy(alpha = 0.2f)),
        modifier = modifier,
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = scoreBoardSong.song.genre.getColor())
    ) {
        Card(
            modifier = modifier.padding(vertical = 4.dp, horizontal = 18.dp),
            colors = CardDefaults.cardColors()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaikoText(
                    text = scoreBoardSong.song.artist,
                    fontSize = 8.sp,
                )
                Spacer(modifier = Modifier.padding(2.dp))
                TaikoText(
                    scoreBoardSong.song.enName,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    outlineSize = 15f,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                TaikoText(
                    scoreBoardSong.song.fromSeries,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                )
                DifficultyCardRow(
                    Modifier.padding(8.dp),
                    difficulty = scoreBoardSong.song.difficultyRating,
                    songDifficultyStatus = getHighestScores(scoreBoardSong.scoreBoardEntries)
                )
            }
        }
    }
}


fun getHighestScores(scoreBoardEntries: MutableList<ScoreBoardEntry>): SongDifficultyStatus {
    val highestScores = mutableMapOf<DifficultyLevel, PassStatus>()

    for (entry in scoreBoardEntries) {
        val difficulty = entry.score.difficultyLevel
        val passStatus = entry.score.passStatus

        if (highestScores[difficulty] == null || passStatus > highestScores[difficulty]!!) {
            highestScores[difficulty] = passStatus
        }
    }

    return SongDifficultyStatus(
        easy = highestScores[DifficultyLevel.EASY] ?: PassStatus.FAIL,
        medium = highestScores[DifficultyLevel.MEDIUM] ?: PassStatus.FAIL,
        hard = highestScores[DifficultyLevel.HARD] ?: PassStatus.FAIL,
        extreme = highestScores[DifficultyLevel.EXTREME] ?: PassStatus.FAIL,
        extraExtreme = highestScores[DifficultyLevel.EXTRA_EXTREME] ?: PassStatus.FAIL
    )
}

// See if you can find a way to make this only display as many will fit on screen
// Google does this on the play store... surely it cant be that hard... right?
@Composable
fun DifficultyCardRow(
    modifier: Modifier = Modifier,
    difficulty: Difficulty = Difficulty(2, 4,  8, 9, 100),
    songDifficultyStatus: SongDifficultyStatus = SongDifficultyStatus(PassStatus.DONDER_FULL_COMBO, PassStatus.FULL_COMBO, PassStatus.PASS, PassStatus.DONDER_FULL_COMBO, PassStatus.FULL_COMBO),
    displayAll: Boolean = false
){
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.scrollTo(Int.MAX_VALUE) } // Surely a better way to start scroll at end... right?
    Row(modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceBetween){
        if(displayAll) {
            difficulty.easy?.let {
                DifficultyButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    difficulty = it,
                    difficultyLevel = DifficultyLevel.EASY,
                    passStatus = songDifficultyStatus.easy
                )
            }
            difficulty.medium?.let {
                DifficultyButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    difficulty = it,
                    difficultyLevel = DifficultyLevel.MEDIUM,
                    passStatus = songDifficultyStatus.medium
                )
            }
        }
        if(displayAll || difficulty.extraExtreme == null) {
            difficulty.hard?.let {
                DifficultyButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    difficulty = it,
                    difficultyLevel = DifficultyLevel.HARD,
                    passStatus = songDifficultyStatus.hard
                )
            }
        }
        difficulty.extreme?.let {
            DifficultyButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                difficulty = it,
                difficultyLevel = DifficultyLevel.EXTREME,
                passStatus = songDifficultyStatus.extreme
            )
        }
        difficulty.extraExtreme?.let {
            DifficultyButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                difficulty = it,
                difficultyLevel = DifficultyLevel.EXTRA_EXTREME,
                passStatus = songDifficultyStatus.extraExtreme
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyButton(
    modifier: Modifier = Modifier,
    difficulty: Int = 3,
    difficultyLevel: DifficultyLevel = DifficultyLevel.EASY,
    passStatus: PassStatus = PassStatus.FAIL
){
    Card(onClick = {}, modifier) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            difficultyLevel
                                .color()
                                .copy(alpha = 0.3f),
                            difficultyLevel.color()
                        )
                    )
                )
                .width(intrinsicSize = IntrinsicSize.Min)
                .padding(top = 4.dp)
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(
                (0).dp
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
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(18.dp)
                    )
                }
                TaikoText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(difficultyLevel.name()),
                    color = Color.Black,
                    textAlign = TextAlign.End,
                    outlineSize = 5f,
                    outlineColor = Color.White
                )
            }
            StarBar(
                modifier = Modifier.offset(y = (-5).dp),
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
    Row(modifier = modifier.wrapContentWidth(), verticalAlignment = Alignment.Bottom){
        Spacer(modifier = Modifier.padding(start = 4.dp))
        TaikoText(
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 1.dp, start = 1.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = this.size.minDimension - if (stars > 9) 15 else 0
                    )
                },
            text = stars.toString(),
            color = Color.White,

        )
        Row(
            modifier = Modifier
                .background(color = Color(0x26000000), shape = RoundedCornerShape(4.dp))
        ) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            for (i in 0..9) {
                if (i <= stars) {
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