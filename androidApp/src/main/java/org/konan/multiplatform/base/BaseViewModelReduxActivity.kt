package org.konan.multiplatform.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.Main
import org.greeting.cooo.ViewModelRedux
import org.greeting.cooo.ViewModelStore
import org.greeting.cooo.start

abstract class BaseViewModelReduxActivity<VIEWMODEL : ViewModelRedux<*, *>> : AppCompatActivity() {

    private val binderJob = Job()
    protected val scope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + binderJob)

    private lateinit var viewModelStore: ViewModelStore<VIEWMODEL>
    protected val viewModelRedux by lazy { viewModelStore.viewModelRedux }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelStore = lastCustomNonConfigurationInstance as? ViewModelStore<VIEWMODEL>
            ?: createViewModel().start(Dispatchers.Main)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return viewModelStore
    }

    protected abstract fun createViewModel(): VIEWMODEL

    override fun onDestroy() {
        binderJob.cancel()
        if (!isChangingConfigurations) {
            viewModelStore.stop()
        }
        super.onDestroy()
    }
}