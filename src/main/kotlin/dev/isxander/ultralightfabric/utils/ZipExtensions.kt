/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipInputStream

fun extractZip(zipStream: InputStream, folder: File) {
    if (!folder.exists()) {
        folder.mkdir()
    }

    ZipInputStream(zipStream).use { zipInputStream ->
        var zipEntry = zipInputStream.nextEntry

        while (zipEntry != null) {
            if (zipEntry.isDirectory) {
                zipEntry = zipInputStream.nextEntry
                continue
            }

            val newFile = File(folder, zipEntry.name)
            File(newFile.parent).mkdirs()

            FileOutputStream(newFile).use {
                zipInputStream.copyTo(it)
            }
            zipEntry = zipInputStream.nextEntry
        }

        zipInputStream.closeEntry()
    }
}

fun extractZip(zipFile: File, folder: File) = extractZip(FileInputStream(zipFile), folder)
