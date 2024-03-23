package com.example.taikoban.ui.difficulty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taikoban.R
import com.example.taikoban.ui.common.TaikoText

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

@Preview
@Composable
private fun preview(){
    Column {
        StarBar(modifier = Modifier.padding(8.dp), stars = 0)
        StarBar(modifier = Modifier.padding(8.dp), stars = 5)
        StarBar(modifier = Modifier.padding(8.dp), stars = 10)
    }
}