/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.utils

private val OS = System.getProperty("os.name").toLowerCase()
var IS_WINDOWS = OS.indexOf("win") >= 0
var IS_MAC = OS.indexOf("mac") >= 0 || OS.indexOf("darwin") >= 0
var IS_UNIX = OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0
