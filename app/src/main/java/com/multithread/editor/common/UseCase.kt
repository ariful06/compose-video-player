package com.multithread.editor.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<P, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected abstract suspend fun run(params: P): R

    suspend operator fun invoke(params: P): R {
        return withContext(dispatcher) {
            run(params)
        }
    }

    suspend operator fun UseCase<Unit, R>.invoke(): R = this(Unit)

}