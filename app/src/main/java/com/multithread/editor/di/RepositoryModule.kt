package com.multithread.editor.di

import com.multithread.editor.data.repository.VideoRepositoryImpl
import com.multithread.editor.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author annah
 * Created 7/28/2023 at 6:59 PM
 */

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun provideVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository
}