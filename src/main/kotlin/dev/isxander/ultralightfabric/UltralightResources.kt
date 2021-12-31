/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric

import dev.isxander.ultralightfabric.utils.*
import dev.isxander.utils.http
import dev.isxander.utils.liquidBounceUrl
import dev.isxander.utils.logger
import dev.isxander.utils.mc
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.system.exitProcess

class UltralightResources {

    companion object {

        /**
         * Exact library version of the LabyMod Ultralight Bindings.
         */
        private const val LIBRARY_VERSION = 0.46

    }

    private val ultralightRoot = File(mc.runDirectory, "config/ultralight")
    val binRoot = File(ultralightRoot, "bin")
    val cacheRoot = File(ultralightRoot, "cache")
    val resourcesRoot = File(ultralightRoot, "resources")

    /**
     * Download resources
     */
    fun downloadResources() {
        runCatching {
            val versionsFile = File(ultralightRoot, "VERSION")

            // Check if library version is matching the resources version
            if (versionsFile.exists() && versionsFile.readText().toDoubleOrNull() == LIBRARY_VERSION) {
                return
            }

            // Make sure the old natives are being deleted
            if (binRoot.exists()) {
                binRoot.deleteRecursively()
            }

            if (resourcesRoot.exists()) {
                resourcesRoot.deleteRecursively()
            }

            // Translate os to path
            val os = when {
                IS_WINDOWS -> "win"
                IS_MAC -> "mac"
                IS_UNIX -> "linux"
                else -> error("unsupported operating system")
            }

            logger.info("Downloading v$LIBRARY_VERSION resources... (os: $os)")
            val nativeUrl = "$liquidBounceUrl/ultralight_resources/$LIBRARY_VERSION/$os-x64.zip"

            // Download resources
            ultralightRoot.mkdir()
            val pkgNatives = File(ultralightRoot, "resources.zip").apply {
                createNewFile()
                runBlocking { writeBytes(http.get(nativeUrl).body()) }
            }

            // Extract resources from zip archive
            logger.info("Extracting resources...")
            extractZip(pkgNatives, ultralightRoot)
            versionsFile.createNewFile()
            versionsFile.writeText(LIBRARY_VERSION.toString())

            // Make sure to delete zip archive to save space
            logger.debug("Deleting resources bundle...")
            pkgNatives.delete()

            logger.info("Successfully loaded resources.")
        }.onFailure {
            logger.error("Unable to download resources", it)

            exitProcess(-1)
        }
    }

}
