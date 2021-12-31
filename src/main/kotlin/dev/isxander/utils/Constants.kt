package dev.isxander.utils

import net.minecraft.client.MinecraftClient
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val mc: MinecraftClient
    get() = MinecraftClient.getInstance()

val logger: Logger = LogManager.getLogger("Ultralight")

const val xanderDlUrl = "https://dl.isxander.dev"
const val liquidBounceUrl = "https://cloud.liquidbounce.net/LiquidBounce"
