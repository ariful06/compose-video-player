package com.multithread.editor.ui.home

import com.multithread.editor.common.base.ViewEvent
import com.multithread.editor.common.base.ViewSideEffect
import com.multithread.editor.common.base.ViewState
import com.multithread.editor.common.domain.exception.DomainException
import com.multithread.editor.domain.entities.Video


internal class HomeContract {
    sealed class Event : ViewEvent {
        data class OnClickVideoItem(val video: Video) : Event()
        object NavigateUp : Event()
    }

    data class State(
        val videos: List<Video> = mutableListOf(),
        val dataState: DataState = DataState.INITIAL,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class GenericError(val exception: DomainException) : Effect()

        sealed class Navigation : Effect() {
            object NavigateUp : Effect()
            data class ToVideoPlayer(val video: Video) : Navigation()
        }
    }
}

internal enum class DataState {
    INITIAL,
    SUCCESS,
    FAILED
}