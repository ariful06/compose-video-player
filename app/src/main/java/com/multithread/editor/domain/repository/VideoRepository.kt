package com.multithread.editor.domain.repository

import com.multithread.editor.domain.entities.Video
import com.multithread.editor.common.domain.Result

internal interface VideoRepository {
    suspend fun retrieveVideos(): Result<List<Video>>
}