package com.example.taikoban.ui.difficulty

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taikoban.objects.DifficultyLevel
import com.example.taikoban.objects.DifficultyLevel.Companion.color
import com.example.taikoban.objects.DifficultyLevel.Companion.icon
import com.example.taikoban.objects.DifficultyLevel.Companion.name
import com.example.taikoban.objects.PassStatus
import com.example.taikoban.objects.PassStatus.Companion.icon
import com.example.taikoban.ui.common.TaikoText

@Preview
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