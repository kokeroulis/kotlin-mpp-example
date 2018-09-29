package org.konan.multiplatform

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.BroadcastChannel
import org.greeting.cooo.DummyNetworkService
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope

class Presenter(private val dummyNetworkService: DummyNetworkService) {

    private val _models = ConflatedBroadcastChannel<String>()
    val models : ReceiveChannel<String> get() = _models.openSubscription()

    suspend fun load() = coroutineScope<Unit> {
            launch(Dispatchers.IO) {
                Log.e("test2", "inside load")
                Log.e("test", Thread.currentThread().name)
                val res = dummyNetworkService.doSomeOperation()
                Log.e("test", Thread.currentThread().name)
                Log.e("test3", "after res")
                Log.e("test", Thread.currentThread().name)
                _models.offer(res)
                //_models.offer(res)
                Log.e("test4", Thread.currentThread().name)
                Log.e("test", "after offer")
            }
    }
}