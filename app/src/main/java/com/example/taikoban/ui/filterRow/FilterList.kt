package com.example.taikoban.ui.filterRow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taikoban.objects.Genre
import com.example.taikoban.objects.Genre.Companion.getColor
import com.example.taikoban.objects.Genre.Companion.getName
import com.example.taikoban.ui.common.TaikoText
import com.example.taikoban.viewModels.LocalScoreBoardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun FilterList(
    modifier: Modifier = Modifier,
    viewModel: LocalScoreBoardViewModel = LocalScoreBoardViewModel(),
    state: LazyListState = rememberLazyListState()
){
    val expanded = viewModel.currentGenre
    val coroutine = rememberCoroutineScope()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = state)
    LaunchedEffect(expanded.value) {
        coroutine.launch { state.animateScrollToItem(expanded.value.ordinal) }
    }
    LazyRow(
        modifier = modifier.wrapContentSize(),
        state = state,
        flingBehavior = snapBehavior
    ) {
        item(){
            Spacer(modifier = Modifier.padding(horizontal = 24.dp))
        }
        items(Genre.values()){ genre ->
            FilterButton(
                expanded = expanded.value == genre,
                genre = genre,
                onClick = {
                    viewModel.selectedGenre.value = genre
                }
            )
        }
        item(){
            Spacer(modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}
@Composable
fun FilterButton(
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