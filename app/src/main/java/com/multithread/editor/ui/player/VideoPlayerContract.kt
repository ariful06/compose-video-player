package com.multithread.editor.ui.player

import android.net.Uri
import com.multithread.editor.common.base.ViewEvent
import com.multithread.editor.common.base.ViewSideEffect
import com.multithread.editor.common.base.ViewState
import com.multithread.editor.common.domain.exception.DomainException
import com.multithread.editor.domain.entities.Video


internal class VideoPlayerContract {
    sealed class Event : ViewEvent {
        data class Play(val video: Video) : Event()
        object Forward : Event()
        object Backward : Event()
        object NavigateUp : Event()
    }

    data class State(
        val video: Video = Video(
            uri = "",
            name = "",
            duration = 0,
            size = 0
        ),
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class GenericError(val exception: DomainException) : Effect()

        sealed class Navigation : Effect() {
            object NavigateUp : Effect()
        }
    }
}
