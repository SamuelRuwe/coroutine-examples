package samples

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

/**
 * runBlocking starts a new coroutine on the main thread
 * async also starts a new coroutine on a thread from the thread pool
 *
 * There is no guarantee that waiting... is printed before loading...
 * Calling .await() suspends the main coroutine until the result of the other coroutine is available.
 * If the result is available it will be returned and the code will continue its flow
 *
 * In this example, it is not available yet so the main coroutine gets suspended
 *
 * Delay suspends the outer coroutine for the given amount of time
 *
 * After one second, loadData coroutine is resumed and prints loaded
 * 42 is returned from loadData and wakes up the main coroutine that was waiting on the result of loadData
 *
 * If a dispatcher is not provided as an argument the coroutine will be run on the same thread
 */
fun main() = runBlocking {
    val deferredDifferentThread: Deferred<Int> = async(Dispatchers.Default) {
        loadDataExample("Different Thread")
    }

    val deferredSameThread: Deferred<Int> = async {
        loadDataExample("Same Thread")
    }

    val sam = loadDataExample("Sam")

    val sam2 = coroutineScope {
        loadDataExample("Sam2")
    }

    log("waiting...")
    log("Different thread: ${deferredDifferentThread.await()}")
    log("Same thread: ${deferredSameThread.await()}")
    log("finished!")
    log("$sam")
    log("sam2: $sam2")
}

suspend fun loadDataExample(name: String): Int {
    log("$name loading...")
    delay(1000L)
    log("$name loaded!")
    return 42
}
