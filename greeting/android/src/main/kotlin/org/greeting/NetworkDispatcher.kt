package org.greeting

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val NetworkDispatcher: CoroutineDispatcher = Dispatchers.Default
