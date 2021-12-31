/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.pages

import java.io.File
import java.nio.file.StandardWatchEventKinds

class Page private constructor(val name: String) {
    private val pageFolder = File(PageManager.guisFolder, name)

    val viewableFile: String
        get() = "file:///${File(pageFolder, "index.html").absolutePath}"

    val exists: Boolean
        get() = pageFolder.exists()

    private val watcher by lazy {
        val path = pageFolder.toPath()
        val watchService = path.fileSystem.newWatchService()
        path.register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY
        )
        watchService
    }

    fun hasUpdate(): Boolean {
        val watchKey = watcher.poll()
        val shouldUpdate = watchKey?.pollEvents()?.isNotEmpty() == true
        watchKey?.reset()
        return shouldUpdate
    }

    fun close() {
        watcher.close()
    }

    override fun toString() = "Page($name, $viewableFile)"

    companion object {
        fun of(name: String) = Page(name).takeIf { it.exists }
    }
}
