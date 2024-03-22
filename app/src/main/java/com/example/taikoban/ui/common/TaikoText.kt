package com.example.taikoban.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.taikoban.R


@OptIn(ExperimentalTextApi::class)
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