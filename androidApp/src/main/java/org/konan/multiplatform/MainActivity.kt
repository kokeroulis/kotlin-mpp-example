package org.konan.multiplatform

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.greeting.Factory
import org.greeting.cooo.DummyNetworkService

import org.greeting.cooo.HeavyNetworkOperation
import org.greeting.cooo.ViewModelStore
import org.konan.multiplatform.base.BaseViewModelReduxActivity
import org.konan.multiplatform.di.Injector
import kotlin.properties.Delegates

class MainActivity : BaseViewModelReduxActivity<MainViewModelRedux>() {

    private var rootLayout: LinearLayout by Delegates.notNull()

    private val dummyNetworkService = DummyNetworkService(
        HeavyNetworkOperation {
            Log.e("test", "did job lived?")

            Thread.sleep(8000)
        }
    )
    private lateinit var viewModelStore: ViewModelStore<MainViewModelRedux>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayout = findViewById(R.id.main_view) as LinearLayout
        rootLayout.removeAllViews()

        val product = Factory.create(mapOf("user" to "JetBrains"))
        val tv = TextView(this)
        tv.text = product.toString()
        rootLayout.addView(tv)

        test()

        viewModelRedux.offerAction(MainAction.LoadMainContent)
    }

    private fun test() {

        viewModelRedux.bindTo(scope) { newSTATE ->
            Log.e("test", "New STATEEEEE $newSTATE")

            newSTATE.userList?.let {
                viewModelRedux.offerAction(MainAction.AnotherAction)
            }
        }
    }

    override fun createViewModel(): MainViewModelRedux {
        return MainViewModelRedux(Dispatchers.IO, Injector.injectUserRepository())
    }
}