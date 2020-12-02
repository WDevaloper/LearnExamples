package com.github.pokemon

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*


class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testKotlin() = runBlocking {
        val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)


         launch {
            while (true) {
                val element = broadcastChannel.openSubscription()
                println(element.receive())
            }
        }


        launch {
            while (true) {
                val element = broadcastChannel.openSubscription()
                println("element--->${element.receive()}")
            }
        }


        launch {
            var i = 0
            while (true) {
                broadcastChannel.send(i++)
                delay(5000)
            }
        }
        Unit
    }
}
