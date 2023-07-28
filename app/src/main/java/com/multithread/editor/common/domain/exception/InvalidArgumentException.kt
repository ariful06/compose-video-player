package com.multithread.editor.common.domain.exception

open class InvalidArgumentException(
    override val message: String,
) : DomainException(message = message, desc = "Invalid Argument Exception") {
    override fun toString(): String {
        return "{\"message\": \"$message\"}"
    }
}