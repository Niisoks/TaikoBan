package com.example.taikoban.screens

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taikoban.ui.NavLocation
import com.example.taikoban.ui.common.TaikoCard
import com.example.taikoban.ui.common.isScrollingDown
import com.example.taikoban.ui.filterRow.FilterList
import com.example.taikoban.viewModels.LocalScoreBoardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongListScreen(navController: NavController, viewModel: LocalScoreBoardViewModel = LocalScoreBoardViewModel()){
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
            navController.navigate(NavLocation.SCOREBOARD.route)
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
                    modifier = viewModel.cardWidth.value?.let{ Modifier.width(it)} ?: Modifier.onGloballyPositioned { card ->
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