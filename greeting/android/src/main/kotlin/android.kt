package org.greeting


actual class Platform actual constructor() {
    actual val platform: String = "Android"
}

actual class Product(actual val user: String) {
    fun androidSpecificOperation() {
        println("I am from android by code)")
    }
    override fun toString() = "Android product of $user for some build model here"
}

actual object Factory {
    actual fun create(config: Map<String, String>) =
            Product(config["user"]!!)
    actual val platform: String = "android"
}
