package com.multithread.editor.common.domain.exception

import java.io.IOException

open class DomainException(
    open val code: Int = 0,
    override val message: String,
    open val desc: String,
) : IOException(message) {
    override fun toString(): String {
        return "{\"code\": $code, \"message\": \"$message\", \"desc\": \"$desc\"}"
    }
}