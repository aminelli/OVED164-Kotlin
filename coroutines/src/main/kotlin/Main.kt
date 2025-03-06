package com.etlforma.examples.kotlin

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main2() = runBlocking {
    var a = 5;
    launch {
        delay(10000L);
        println("Ciao mondo 1");
        println(a);
        a = 3;
    }
    launch {
        delay(5000L);
        println("Ciao mondo 2");
        println(a);
        a = 7;
    }
    println("Ciao");
    println(a);
    a = 6;
}


fun main3() = runBlocking {
    launch {
        doAnything1();
        doAnything2()
    }
    println("Ciao");
}

suspend fun doAnything1() {
    delay(10000L);
    println("Ciao mondo doAnything 1");
}

suspend fun doAnything2() {
    delay(5000L);
    println("Ciao mondo doAnything 2");
}


fun main4() = runBlocking {
    doWork01();
    doWork02();
    println("E' un mondo difficile");
}


suspend fun doWork01() = coroutineScope {
    launch {
        delay(2000L);
        println("Ciao mondo doWork01 => L1")
    }
    launch {
        delay(100L);
        println("Ciao mondo doWork01 => L2")
    }
    launch {
        delay(500L);
        println("Ciao mondo doWork01 => L3")
    }
    println("Ciao doWork01");
}

suspend fun doWork02() = coroutineScope {
    launch {
        delay(2000L);
        println("Ciao mondo doWork02 => L1")
    }
    launch {
        delay(100L);
        println("Ciao mondo doWork02 => L2")
    }
    launch {
        delay(500L);
        println("Ciao mondo doWork02 => L3")
    }
    println("Ciao doWork02");
}



fun main5() = runBlocking {
    var count = 0;
    val job = launch {
        delay(5000L);
        count++
        println("Ciao mondo job")
        println("Count: $count")
    }
    println("Ciao mondo runBlocking")
    job.join()
    println("Destroy world")
}

fun main() = runBlocking {
    var count = 0;
    repeat(50_000) {
        launch {
            delay(1000L)
            count++
            println("Count: $count")
        }
    }
}
