package org.greeting.cooo

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach

abstract class ViewModelRedux<STATE, ACTION> {
    protected abstract val dispatcher: CoroutineDispatcher

    private val actions = ConflatedBroadcastChannel<ACTION>()
    private val stateChannel = ConflatedBroadcastChannel<STATE>()

    private var state: STATE = initialState()

    abstract fun initialState(): STATE

    fun offerAction(action: ACTION) {
        actions.offer(action)
    }

    protected abstract fun reduce(currentAction: ACTION, currentState: STATE): STATE

    protected abstract suspend fun sideEffects(currentAction: ACTION, currentState: STATE): ACTION

    suspend fun initialize() = coroutineScope<Unit> {
            withContext(dispatcher) {
                // dispatch the initial state
                stateChannel.offer(state)
                
                actions.consumeEach {
                    val sideEffect = sideEffects(currentAction = it, currentState = state)
                    val newState = reduce(currentAction = sideEffect, currentState = state)
                    if (newState != state) {
                        state = newState
                        stateChannel.offer(newState)
                    }
                }
            }
    }

    fun bindTo(scope: CoroutineScope, coroutineDispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
               stateUpdated: (newSTATE: STATE) -> Unit) {
        scope.launch(coroutineDispatcher) {
            stateChannel.consumeEach {
                stateUpdated(it)
            }
        }
    }
}

class ViewModelStore<viewModel: ViewModelRedux<*, *>>(
    val job: Job?,
    val viewModelRedux: viewModel
) {

    fun stop() {
        job?.cancel()
    }
}

fun <ViewModel : ViewModelRedux<*, *>> ViewModel.start(dispatcher: CoroutineDispatcher) : ViewModelStore<ViewModel> {
    val job = GlobalScope.launch(dispatcher) { initialize() }
    return ViewModelStore(null, this)
}