package com.multithread.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.multithread.editor.ui.VideoEditorNavigation
import com.virus.videoeditor.ui.theme.VideoEditorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoEditorTheme {
                val scope = rememberCoroutineScope()
                VideoEditorNavigation(
                    activity = this,
                    navController = rememberNavController(),
                    coroutineScope = scope,
                    onError =  {

                    }
                )
            }
        }
    }
}