package com.multithread.editor.data.repository

import com.multithread.editor.domain.entities.Video
import com.multithread.editor.domain.repository.VideoRepository
import com.multithread.editor.utils.FileManager
import javax.inject.Inject
import com.multithread.editor.common.domain.Result
import com.multithread.editor.common.domain.exception.DomainException
import java.lang.Exception

/**
 * @author annah
 * Created 7/28/2023 at 6:47 PM
 */
internal class VideoRepositoryImpl @Inject constructor(
    private val fileManager: FileManager
) :
    VideoRepository {
    override suspend fun retrieveVideos(): Result<List<Video>> {
        return try {
            Result.Success(fileManager.getAllVideos())
        } catch (e: Exception) {
            Result.Error(
                DomainException(
                    message = "Something went wrong",
                    desc = "Something went wrong"
                )
            )
        }
    }
}