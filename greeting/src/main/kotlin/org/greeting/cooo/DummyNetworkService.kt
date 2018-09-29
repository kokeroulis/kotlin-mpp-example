package org.greeting.cooo

class DummyNetworkService(private val heavyNetworkOperation: HeavyNetworkOperation) {


    suspend fun doSomeOperation(): String {
        val res = heavyNetworkOperation.takeSomethingFromWeb()

        return res
    }
}