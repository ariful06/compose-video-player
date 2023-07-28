package com.multithread.editor.ui.player

import androidx.lifecycle.SavedStateHandle
import com.multithread.editor.common.base.BaseViewModel
import com.multithread.editor.common.domain.exception.InvalidArgumentException
import com.multithread.editor.domain.entities.Video
import com.multithread.editor.domain.repository.VideoRepository
import com.multithread.editor.utils.Constants.ARG_VIDEO
import com.multithread.editor.utils.DispatcherProvider
import com.multithread.editor.utils.JsonConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
internal class VideoPlayerViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<VideoPlayerContract.Event, VideoPlayerContract.State, VideoPlayerContract.Effect>(
    dispatcher = dispatcherProvider.main()
) {
    init {
        try {
            val video = JsonConverter.fromJson(savedStateHandle.get<String>(ARG_VIDEO)!!,Video::class.java)!!
            setState { copy(video = video) }
        } catch (e: Exception) {
            Timber.e(e)
            setEffect { VideoPlayerContract.Effect.GenericError(InvalidArgumentException("Invalid Arguments")) }
        }
    }

    override fun setInitialState() = VideoPlayerContract.State()

    override fun handleEvents(event: VideoPlayerContract.Event) {
        when (event) {
            is VideoPlayerContract.Event.NavigateUp -> setEffect { VideoPlayerContract.Effect.Navigation.NavigateUp }
            VideoPlayerContract.Event.Backward -> {}
            VideoPlayerContract.Event.Forward -> {}
            is VideoPlayerContract.Event.Play -> {}
        }
    }
}
