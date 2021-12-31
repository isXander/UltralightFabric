/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.utils

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.createDirectories

class CopyDirVisitor(val sourceDir: Path, val targetDir: Path) : SimpleFileVisitor<Path>() {
    override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        val targetFile = targetDir.resolve(sourceDir.relativize(file))
        Files.copy(file, targetFile)

        return FileVisitResult.CONTINUE
    }

    override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
        val newDir = targetDir.resolve(sourceDir.relativize(dir))
        newDir.createDirectories()

        return FileVisitResult.CONTINUE
    }
}

fun Path.copyRecursively(targetDir: Path) {
    Files.walkFileTree(this, CopyDirVisitor(this, targetDir))
}
