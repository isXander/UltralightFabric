/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.utils

class ThreadLock<T> {
    private lateinit var lockedOnThread: Thread
    private var content: T? = null

    fun get(): T {
        if (Thread.currentThread() != lockedOnThread) {
            error("thread-locked content accessed by other thread")
        }

        return content!!
    }

    fun lock(t: T) {
        lockedOnThread = Thread.currentThread()
        content = t
    }
}
