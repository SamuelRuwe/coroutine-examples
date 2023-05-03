package samples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*

/**
 * The coroutine that sent a message to the channel with channel.send gets suspended
 * until the channel receives the message
 *
 * When the rendezvous moment happens (the send call is received) the suspended coroutine is awakened
 * If the thread is not busy, it will be resumed. Otherwise it will be scheduled for execution later
 *
 * If a receive call is already waiting, the coroutine that called channel.send will not be suspended
 */
fun main() = runBlocking<Unit> {
    // rendezvous channel
    val channel = Channel<String>()

    launch {
        channel.send("A1")
        channel.send("A2")
        log("A done")
    }

    launch {
        channel.send("B1")
        log("B done")
    }

    launch {
        repeat(3) {
            val x = channel.receive()
            log(x)
        }
    }
}
