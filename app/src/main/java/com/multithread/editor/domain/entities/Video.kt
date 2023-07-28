package com.multithread.editor.domain.entities

data class Video(
    val uri: String,
    val name: String,
    val duration: Long,
    val size: Long
)