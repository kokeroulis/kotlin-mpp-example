package org.greeting.cooo

class HeavyNetworkOperation(private val longOperation: () -> Unit) {


    fun takeSomethingFromWeb(): String {
        longOperation()
        return "Long operation from web done"
    }
}