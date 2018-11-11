package org.konan.multiplatform

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import org.greeting.cooo.DummyNetworkService
import org.greeting.cooo.ViewModelRedux

class MainViewModelRedux(
    override val dispatcher: CoroutineDispatcher,
    private val dummyNetworkService: DummyNetworkService
) : ViewModelRedux<MainState, MainAction>() {
    override fun initialState(): MainState {
        return MainState()
    }

    override fun reduce(currentAction: MainAction, currentState: MainState): MainState {
        return when (currentAction) {
            is MainAction.LoadMainContent -> currentState.copy(true, null)
            is MainAction.MainContentLoaded -> currentState.copy(false, currentAction.content)
            is MainAction.AnotherAction -> {
                Log.e("test", "other actionnn")
                currentState
            }
        }
    }

    override suspend fun sideEffects(currentAction: MainAction, currentState: MainState): MainAction? {
        return when (currentAction) {
            is MainAction.LoadMainContent -> {
                val result = dummyNetworkService.doSomeOperation()
                MainAction.MainContentLoaded(result)
            }
            else -> null
        }
    }
}