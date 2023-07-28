package com.multithread.editor.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @author annah
 * Created 7/28/2023 at 7:54 PM
 */


object JsonConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        return moshi.adapter(clazz).fromJson(json)
    }

    fun <T> toJson(value: T, clazz: Class<T>): String {
        return moshi.adapter(clazz).toJson(value)
    }
}