package org.konan.multiplatform

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.channels.consumeEach
import org.greeting.Factory
import org.greeting.cooo.DummyNetworkService
import org.greeting.cooo.HeavyNetworkOperation
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var rootLayout: LinearLayout by Delegates.notNull()

    private val dummyNetworkService = DummyNetworkService(
        HeavyNetworkOperation { Thread.sleep(2000) }
    )

    private val binderJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + binderJob)

    private lateinit var store: Store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayout = findViewById(R.id.main_view) as LinearLayout
        rootLayout.removeAllViews()

        val product = Factory.create(mapOf("user" to "JetBrains"))
        val tv = TextView(this)
        tv.text = product.toString()
        rootLayout.addView(tv)
        store = lastCustomNonConfigurationInstance as? Store ?: Presenter(dummyNetworkService).startStore(Dispatchers.Main)

        test()
    }

    private fun test() {
        scope.launch(Dispatchers.Main) {
            Log.e("2222", Thread.currentThread().name)
            Log.e("44444", Thread.currentThread().name)

            val models = store.presenter.models
            models.consumeEach {
                Log.e("RESSS", it)
                Log.e("test1", Thread.currentThread().name)

            }
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return store
    }

    class Store(
        private val job: Job,
        val presenter: Presenter) {

        fun stop() {
            job.cancel()
        }
    }

    fun Presenter.startStore(dispatcher: CoroutineDispatcher): Store {
        val job = GlobalScope.launch(dispatcher) { load() }
        return Store(job, this)
    }

    override fun onDestroy() {
        binderJob.cancel()
        if (!isChangingConfigurations) {
            store.stop()
        }
        super.onDestroy()
    }
}