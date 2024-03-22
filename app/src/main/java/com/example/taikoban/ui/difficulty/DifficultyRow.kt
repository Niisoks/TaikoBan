package com.example.taikoban.ui.difficulty

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taikoban.objects.Difficulty
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.SongDifficultyStatus

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