package com.example.taikoban.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import com.example.taikoban.objects.PassStatus.Companion.icon
import com.example.taikoban.objects.PeripheralType.Companion.getIcon
import com.example.taikoban.ui.common.TaikoText
import com.example.taikoban.ui.common.UserButton
import com.example.taikoban.viewModels.LocalScoreBoardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongScoreboardScreen(
    navController: NavController,
    viewModel: LocalScoreBoardViewModel
){
    val state = rememberLazyListState()
    val difficulty = viewModel.selectedDifficulty.value
    val scoreBoard = viewModel.filteredScores.value
    Box(Modifier.fillMaxSize()) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(
                items = scoreBoard,
            ) {i, it ->
                val expanded = remember{ mutableStateOf(false) }
                Card(
                    onClick = {expanded.value = true}
                ) {
                    if(it == null) return@Card // not possible but itll cry otherwise
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                    ) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            TaikoText(
                                modifier = Modifier.fillMaxWidth(),
                                text = (i + 1).toString(),
                                outlineSize = 12f
                            )
                        }
                        UserButton(
                            user = it!!.user,
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            painter = painterResource(id = it.score.passStatus.icon()),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp).weight(0.3f)
                        )
                        Box(modifier = Modifier.weight(0.7f)) {
                            TaikoText(
                                modifier = Modifier.fillMaxWidth(),
                                text = it.score.points.toString(),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    if(expanded.value || true){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp).padding(horizontal = 12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.Black.copy(alpha = 0.4f)),
                        ){
                            Row(
                            ){
                                Image(
                                    painter = painterResource(id = it.score.difficultyLevel.icon()),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                                Image(
                                    painter = painterResource(id = it.type.getIcon()),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview(){
    val context = LocalContext.current
    val viewModel = remember {LocalScoreBoardViewModel()}
    viewModel.selectSongAndFilter(viewModel.scoreBoard.value.first(), null)
    SongScoreboardScreen(
        navController = NavController(context),
        viewModel = viewModel
    )
}