package samples

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch(CoroutineName("Sam")) {
            log("Hello from $coroutineContext")
        }
    }
}