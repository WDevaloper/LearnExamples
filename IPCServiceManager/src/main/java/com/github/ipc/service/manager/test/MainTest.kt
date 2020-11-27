package com.github.ipc.service.manager.test

object MainTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val testDelegateTest = TestDelegateTest()
        println(testDelegateTest.tsetDelegate)

        testDelegateTest.tsetDelegate = "asdasdasdas"


        println(testDelegateTest.tsetDelegate)
    }
}