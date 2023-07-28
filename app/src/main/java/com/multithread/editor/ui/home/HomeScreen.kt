package com.multithread.editor.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.multithread.editor.R
import com.multithread.editor.common.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.multithread.editor.common.composables.Toolbar
import com.multithread.editor.common.domain.exception.DomainException
import com.multithread.editor.domain.entities.Video
import com.multithread.editor.ui.player.VideoPlayerContract
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * @author annah
 * Created 7/28/2023 at 5:39 PM
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onError: (DomainException) -> Unit,
    onNavigationRequested: (navigationEffect: HomeContract.Effect) -> Unit
) {

    val state: HomeContract.State = viewModel.viewState.value

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        viewModel.getVideos()
        viewModel.effect.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation.ToVideoPlayer -> {
                    onNavigationRequested(effect)
                }
                is HomeContract.Effect.GenericError -> {
                    onError(effect.exception)
                }
                is HomeContract.Effect.Navigation.NavigateUp -> {
                    onNavigationRequested(effect)
                }
            }
        }.collect()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Toolbar(
                "Home",
                onNavigationUp = {
                    viewModel.setEvent(HomeContract.Event.NavigateUp)
                }
            )
        },
        content = { paddingValues ->
            when (state.dataState) {
                DataState.INITIAL -> {}
                DataState.SUCCESS -> {
                    HomeContent(
                        modifier = Modifier.padding(paddingValues),
                        videos = state.videos,
                        onEventSent = {
                            viewModel.setEvent(it)
                        }
                    )
                }
                DataState.FAILED -> {
                    EmptyView()
                }
            }
        }
    )

}


@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    onEventSent: (HomeContract.Event) -> Unit,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 25.dp),
    ) {
        if (videos.isNotEmpty()) {
            items(videos.size) { index ->
                VideoItemRow(
                    video = videos[index],
                    onItemClicked = {
                        onEventSent(HomeContract.Event.OnClickVideoItem(videos[index]))
                    }
                )
            }
        } else {
            item {
                EmptyView()
            }
        }
    }
}


@Composable
fun VideoItemRow(
    modifier: Modifier = Modifier,
    video: Video,
    onItemClicked: (video: Video) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(video) },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_video),
            contentDescription = "view iconaq"
        )
        Spacer(modifier.width(16.dp))
        Text(video.name, maxLines = 2)
    }
}


@Composable
fun EmptyView() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("No data found")

    }
}