/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric

import dev.isxander.ultralightfabric.utils.*
import dev.isxander.evergreenhud.utils.http
import dev.isxander.evergreenhud.utils.liquidBounceUrl
import dev.isxander.evergreenhud.utils.logger
import dev.isxander.evergreenhud.utils.mc
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

        val ultralightRoot = File(mc.runDirectory, "config/ultralight")
        val nativesRoot = File(ultralightRoot, "natives").also { it.mkdirs() }
    }

    val binRoot = File(nativesRoot, "bin")
    val cacheRoot = File(nativesRoot, "cache")
    val resourcesRoot = File(nativesRoot, "resources")

    /**
     * Download resources
     */
    fun downloadResources() {
        runCatching {
            val versionsFile = File(nativesRoot, "VERSION")

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
            val pkgNatives = File(nativesRoot, "resources.zip").apply {
                createNewFile()
                runBlocking { writeBytes(http.get(nativeUrl).body()) }
            }

            // Extract resources from zip archive
            logger.info("Extracting resources...")
            extractZip(pkgNatives, nativesRoot)
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
