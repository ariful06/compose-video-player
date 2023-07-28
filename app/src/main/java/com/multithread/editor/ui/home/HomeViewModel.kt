package com.multithread.editor.ui.home

import androidx.lifecycle.viewModelScope
import com.multithread.editor.common.base.BaseViewModel
import com.multithread.editor.domain.repository.VideoRepository
import com.multithread.editor.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.multithread.editor.common.domain.Result
import javax.inject.Inject


@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
    dispatcher = dispatcherProvider.main()
) {

    fun getVideos() {
        viewModelScope.launch(dispatcherProvider.io()) {
            when (val result = videoRepository.retrieveVideos()) {
                is Result.Success -> {
                    setState { copy(videos = result.data, dataState = DataState.SUCCESS) }
                }
                is Result.Error -> {
                    setEffect { HomeContract.Effect.GenericError(result.exception) }
                    setState { copy(dataState = DataState.FAILED) }
                }
            }
        }
    }

    override fun setInitialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.NavigateUp -> setEffect { HomeContract.Effect.Navigation.NavigateUp }
            is HomeContract.Event.OnClickVideoItem -> setEffect { HomeContract.Effect.Navigation.ToVideoPlayer(event.video) }
        }
    }
}
