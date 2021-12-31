/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.js.bindings

import net.minecraft.util.Util

object UltralightJsUtils {
    fun browse(url: String) = Util.getOperatingSystem().open(url)
}
