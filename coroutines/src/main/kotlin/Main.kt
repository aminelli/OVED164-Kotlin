package com.etlforma.examples.kotlin

import kotlinx.coroutines.*

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

fun main6() = runBlocking {
    var count = 0;
    repeat(50_000) {
        launch {
            delay(1000L)
            count++
            println("Count: $count")
        }
    }
}

fun main7() = runBlocking {
    val job = launch {
         repeat(1000) {
             i ->
             println("I'm sleeping $i ...")
             delay(500L)
         }
    }
    delay(1300L)
    println("fine del''attesa")
    job.cancel()
    job.join()
    println("End")
}

fun main8() = runBlocking {

    var startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var count = 0
        while (count < 5) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: sleep from ${count++}")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("fine dell'attesa")
    job.cancelAndJoin()
    println("End")
}

fun main9() = runBlocking {

    var startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var count = 0
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: sleep from ${count++}")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("fine dell'attesa")
    job.cancelAndJoin()
    println("End")
}


fun main10() = runBlocking {

    val job = launch(Dispatchers.Default) {
        repeat(10) {
            i ->
            try {
                println("count: $i")
                delay(500L)
            } catch (e: Exception) {
                println("Error: $e")
            } finally {
                println("finally: $i")
            }
        }
    }
    delay(1300L)
    println("fine dell'attesa")
    job.cancelAndJoin()
    println("End")
}


fun main11() = runBlocking {

    val job = launch(Dispatchers.Default) {
        try {
            repeat(10) {
                i ->
                println("count: $i")
                delay(500L)

        }
    } catch (e: Exception) {
            println("Error: $e")
        } finally {
            println("finally: end")
        }
    }
    delay(1300L)
    println("fine dell'attesa")
    job.cancelAndJoin()
    println("End")
}

fun main12() = runBlocking {

    val job = launch(Dispatchers.Default) {
        try {
            repeat(10) {
                    i ->
                println("count: $i")
                delay(500L)

            }
        } catch (e: Exception) {
            println("Error: $e")
        } finally {
            withContext(NonCancellable) {
                println("finally: end")
                delay(1000L)
                println("finally: end delay")
            }

        }
    }
    delay(1300L)
    println("fine dell'attesa")
    job.cancelAndJoin()
    println("End")
}

fun main13() = runBlocking {

    withTimeout(1300L) {
        repeat(1000) {
            i ->
            println("count: $i")
            delay(500L)
        }
    }

}

fun main14() = runBlocking {

    val result = withTimeoutOrNull(1300L) {
        repeat(1000) {
                i ->
            println("count: $i")
            delay(500L)
        }
    }
    println("Result: $result")

}

var acquired = 0;

class Resource {
    init { acquired++ }
    fun close() {
        acquired--
    }
}

fun main15() = runBlocking {

    repeat(10_000) {
        launch {
            val res = withTimeout(60) {
                delay(50)
                Resource()
            }
            res.close()
        }
    }
    println(acquired)
}

fun main() = runBlocking {

    repeat(times = 10_000) {
        launch {
            var res:  Resource? = null
            try {
                withTimeout(60) {
                    delay(50)
                    res = Resource()
                    println(acquired)
                }
            } finally {
                println("$res")
                res?.close()
            }

        }
    }
    println(acquired)
}