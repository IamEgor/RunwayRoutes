package com.runway.routes.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun <T> Task<T>.awaitResult() = suspendCoroutine<T?> { continuation ->
    if (isComplete) {
        val exception = exception
        if (isSuccessful) {
            continuation.resume(result)
        } else if (exception != null) {
            continuation.resumeWithException(exception)
        } else {
            continuation.resume(null)
        }
        return@suspendCoroutine
    }
    addOnSuccessListener { continuation.resume(it) }
    addOnFailureListener { continuation.resumeWithException(it) }
    addOnCanceledListener { continuation.resume(null) }
}
