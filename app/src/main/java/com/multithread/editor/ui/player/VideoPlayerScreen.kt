package com.multithread.editor.ui.player

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.multithread.editor.common.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.multithread.editor.common.composables.Toolbar
import com.multithread.editor.common.domain.exception.DomainException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * @author annah
 * Created 7/28/2023 at 5:39 PM
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VideoPlayerScreen(
    viewModel: VideoPlayerViewModel = hiltViewModel(),
    onError: (DomainException) -> Unit,
    onNavigationRequested: (navigationEffect: VideoPlayerContract.Effect) -> Unit
) {
    val state: VideoPlayerContract.State = viewModel.viewState.value

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is VideoPlayerContract.Effect.Navigation -> {
                    onNavigationRequested(effect)
                }
                is VideoPlayerContract.Effect.GenericError -> {
                    onError(effect.exception)
                }
                is VideoPlayerContract.Effect.Navigation.NavigateUp -> {
                    onNavigationRequested(effect)
                }
            }
        }.collect()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Toolbar(
                "Player",
                onNavigationUp = {
                    viewModel.setEvent(VideoPlayerContract.Event.NavigateUp)
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
            ) {
                VideoPlayer(uri = state.video.uri.toUri())
            }
        }
    )

}


@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(uri: Uri) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))

                setMediaSource(source)
                prepare()
            }
    }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                }
            }
        )
    ) {
        onDispose { exoPlayer.release() }
    }
}
