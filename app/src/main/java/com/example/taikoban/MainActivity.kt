package com.example.taikoban

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taikoban.screens.SongListScreen
import com.example.taikoban.ui.NavLocation
import com.example.taikoban.ui.common.NoticeDialog
import com.example.taikoban.viewModels.LocalScoreBoardViewModel
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = remember{LocalScoreBoardViewModel()}
//            TaikoBanTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    NavHost(navController = navController, startDestination = NavLocation.SONG_LIST.route){
                        composable(NavLocation.SONG_LIST.route){ SongListScreen(navController, viewModel) }
                        composable(NavLocation.SCOREBOARD.route){ SongListScreen(navController, viewModel) }
                    }
                }
//            }
        }
    }
}

@Preview
@Composable
fun OpenCVDialog(){
    val initialized = remember { initializeOpenCV() }
    val openAlertDialog = rememberSaveable{ mutableStateOf(true) }
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