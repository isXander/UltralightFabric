/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.utils

import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val mc: MinecraftClient
    get() = MinecraftClient.getInstance()

val logger: Logger = LogManager.getLogger("Ultralight")

const val xanderDlUrl = "https://dl.isxander.dev"
const val liquidBounceUrl = "https://cloud.liquidbounce.net/LiquidBounce"
