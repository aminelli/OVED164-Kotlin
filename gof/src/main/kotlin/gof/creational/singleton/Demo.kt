package com.etlforma.examples.kotlin.gof.creational.singleton

object Singleton {
    var count: Int = 0

    fun printCount() {
        println("Count: $count")
    }

    fun increment() {
        count++
    }
}

fun main() {
    // Accesso al singleton e utilizzo dei suoi metodi
    Singleton.printCount()  // Output: Count: 0
    Singleton.increment()
    Singleton.printCount()  // Output: Count: 1
}