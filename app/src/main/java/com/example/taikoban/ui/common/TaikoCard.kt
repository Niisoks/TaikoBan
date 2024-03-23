package com.example.taikoban.ui.common


import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taikoban.R
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import com.example.taikoban.objects.Genre.Companion.getColor
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.PassStatus.Companion.icon
import com.example.taikoban.objects.PeripheralType
import com.example.taikoban.objects.PeripheralType.Companion.getIcon
import com.example.taikoban.objects.Score
import com.example.taikoban.objects.ScoreBoardEntry
import com.example.taikoban.objects.ScoreBoardSong
import com.example.taikoban.objects.SongDifficultyStatus
import com.example.taikoban.objects.User
import com.example.taikoban.ui.difficulty.DifficultyCardRow
import com.example.taikoban.ui.filterRow.FilterList
import com.example.taikoban.viewModels.LocalScoreBoardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
//@Preview
@Composable
fun ScoreBoardSongPreview(viewModel: LocalScoreBoardViewModel = LocalScoreBoardViewModel()) {
    val list = viewModel.filteredScoreBoard.value
    val selectedGenre = viewModel.selectedGenre
    val localDensity = LocalDensity.current
    val verticalState = rememberLazyListState()
    val horizontalState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = verticalState)
    val coroutine = rememberCoroutineScope()

    val firstVisibleGenre by remember {
        derivedStateOf {
            list[verticalState.firstVisibleItemIndex].song.genre
        }
    }

    LaunchedEffect(key1 = selectedGenre.value) {
        selectedGenre.value?.let {genre ->
            coroutine.launch {
                verticalState.animateScrollToItem(list.indexOfFirst { it.song.genre == genre })
            }
            selectedGenre.value = null
        }
    }
    LaunchedEffect(key1 = firstVisibleGenre) {
        coroutine.launch {
            viewModel.currentGenre.value = firstVisibleGenre
        }
    }

    Box() {
        LazyColumn(
            Modifier
                .fillMaxSize(),
            state = verticalState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            flingBehavior = snapBehavior
        ) {
            item{
                Spacer(modifier = Modifier.padding(26.dp))
            }
            items(
                items = list,
                key = { it.song.uid }
            ) { it ->
                TaikoCard(scoreBoardSong = it,
                    modifier = viewModel.cardWidth.value?.let{Modifier.width(it)} ?: Modifier.onGloballyPositioned { card ->
                        with (localDensity) {
                            viewModel.cardWidth.value = card.size.width.toDp()
                        }
                    }
                )
            }
        }

        val offset by animateIntOffsetAsState(
            targetValue = if (verticalState.isScrollingDown()) {
                IntOffset.Zero
            } else {
                IntOffset(0, -72)
            },
            label = "offset"
        )

        FilterList(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { offset },
            viewModel = viewModel,
            state = horizontalState
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaikoCard(
    modifier: Modifier = Modifier,
    scoreBoardSong: ScoreBoardSong
    ){
    val visibleScore = remember{mutableStateOf(false)}
    val scoreBoardEntries = remember{scoreBoardSong.scoreBoardEntries}
    Card(border = BorderStroke(3.dp, Color.Black.copy(alpha = 0.2f)),
        modifier = modifier,
        onClick = { visibleScore.value = !visibleScore.value },
        colors = CardDefaults.cardColors(containerColor = scoreBoardSong.song.genre.getColor())
    ) {
        Card(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 18.dp),
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

        if(visibleScore.value){
            LazyColumn(
                modifier = Modifier.height(256.dp)
            ){
                items(
                    scoreBoardEntries
                ){
                    UserScore(scoreBoardEntry = it)
                }
            }
        }
    }
}


@Composable
fun UserButton(
    modifier: Modifier = Modifier,
    user: User,
    onClick: () -> Unit = {}
){

    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Button(
            modifier = Modifier.padding(start = 24.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            TaikoText(text = user.name)
        }
        ProfileDon(modifier)
    }
}

@Composable
fun ProfileDon(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.donchan),
        "User image",
        modifier = modifier.size(48.dp),
        tint = Color.Unspecified,
    )
}

@Composable
fun UserScore(
    modifier: Modifier = Modifier,
    scoreBoardEntry: ScoreBoardEntry,
    onClick: () -> Unit = {}
){
    val user = scoreBoardEntry.user
    val score = scoreBoardEntry.score
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(color = Color.Black.copy(alpha = 0.4f))
                .fillMaxWidth()
        ) {
            UserButton(user = user)
            Column(
                Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Color.Black.copy(alpha = 0.4f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = score.difficultyLevel.icon()),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Image(
                        painter = painterResource(id = score.passStatus.icon()),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Image(
                        painter = painterResource(id = scoreBoardEntry.type.getIcon()),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
                TaikoText(
                    text = "${score.points}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 24.sp,
                    outlineSize = 15f
                )
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White.copy(alpha = 0.3f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaikoText(
                    text = stringResource(id = R.string.combo),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 16.sp,
                    outlineSize = 15f,
                    maxLines = 1
                )
                TaikoText(
                    text = "${score.combo}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp),
                    fontSize = 16.sp,
                    outlineSize = 15f,
                    maxLines = 1
                )
            }

        }
        Row {
            flatTopScore(title = stringResource(id = R.string.good), score.good         , modifier.weight(1f))
            flatTopScore(title = stringResource(id = R.string.ok), score.ok             , modifier.weight(1f))
            flatTopScore(title = stringResource(id = R.string.bad), score.bad           , modifier.weight(1f))
            flatTopScore(title = stringResource(id = R.string.drumRoll), score.drumRoll , modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun flatTopScore(
    title : String = "Good",
    value : Int = 123421,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(Color.White.copy(alpha = 0.3f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaikoText(text = title, outlineSize = 5f)
        TaikoText(text = value.toString(), outlineSize = 5f, modifier = modifier.padding(horizontal = 8.dp))
    }
}

@Preview
@Composable
fun UserScorePreview(){
    UserScore(scoreBoardEntry = ScoreBoardEntry(
        score = Score(
            good = 500,
            ok = 50,
            bad = 10,
            combo = 1200,
            drumRoll = 100,
            tamaashiGauge = 0.8f,
            passStatus = PassStatus.PASS,
            difficultyLevel = DifficultyLevel.HARD,
            points = 661890345
        ),
        user = User(
            uid = "user1",
            name = "Test User",
            profileIcon = null
        ),
        type = PeripheralType.DRUM
    ))
}

@Preview
@Composable
private fun UserButtonPreview(){
    UserButton(user =  User(
            uid = "user1",
            name = "Test User",
            profileIcon = null
        )
    )
}

// Is this what the mythical use-case is for?
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