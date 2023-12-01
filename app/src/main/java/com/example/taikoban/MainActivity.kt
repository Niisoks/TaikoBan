package com.example.taikoban

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taikoban.ui.common.NoticeDialog
import com.example.taikoban.ui.theme.TaikoBanTheme
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaikoBanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpenCVDialog()
                }
            }
        }
    }
}

@Preview
@Composable
fun OpenCVDialog(){
    val initialized = remember { initializeOpenCV() }
    val openAlertDialog = remember { mutableStateOf(true) }
    Log.d("OpenCVDialog", "$initialized")
    when{
        openAlertDialog.value -> {
            NoticeDialog(
                onConfirmation = { openAlertDialog.value = false },
                dialogTitle = "OpenCV Status",
                dialogText = if(initialized) "OpenCV Initialized" else "OpenCV failed to initialize!",
                icon = R.drawable.donchan,
                confirmButton = "Ok",
                dismissButton = ""
            )
        }
    }
}

fun initializeOpenCV() : Boolean{
    if (!OpenCVLoader.initDebug()) {
        Log.e("OpenCV", "Unable to load OpenCV!")
        return false
    } else {
        Log.d("OpenCV", "OpenCV loaded Successfully!")
        return true
    }
}