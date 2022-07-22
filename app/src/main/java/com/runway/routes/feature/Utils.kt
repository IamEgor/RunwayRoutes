package com.runway.routes.feature

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.*

internal fun ComponentContext.createComponentScope(coroutineExceptionHandler: CoroutineExceptionHandler) =
    CoroutineScope(coroutineExceptionHandler + Dispatchers.IO + SupervisorJob())
        .also { scope -> lifecycle.doOnDestroy(scope::cancel) }