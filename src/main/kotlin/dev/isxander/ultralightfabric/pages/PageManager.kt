/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.pages

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.isxander.evergreenhud.event.EventListener
import dev.isxander.evergreenhud.event.eventBus
import dev.isxander.evergreenhud.utils.GuiHandler
import dev.isxander.evergreenhud.utils.copyRecursively
import dev.isxander.evergreenhud.utils.logger
import dev.isxander.ultralightfabric.UltralightResources
import dev.isxander.ultralightfabric.js.bindings.UltralightScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

object PageManager : EventListener {
    val tmpFolder = File(UltralightResources.ultralightRoot, "tmp")
    val guisFolder = File(tmpFolder, "guis").also { it.mkdirs() }

    init {
        eventBus.register(this)
    }

    fun registerModPages() {
        cleanup()

        for (mod in FabricLoader.getInstance().allMods) {
            val path = mod.getPath("ultralight")
            if (!path.exists()) continue

            logger.info("Registering ultralight mod: ${mod.metadata.id}")
            val targetDir = File(guisFolder, mod.metadata.id).toPath()
            targetDir.createDirectories()
            path.createDirectories()
            path.copyRecursively(targetDir)
        }

        ClientCommandManager.DISPATCHER.register(LiteralArgumentBuilder.literal<FabricClientCommandSource?>("testscreen").executes {
            logger.info(Page.of("ultralightfabric/funny"))
            GuiHandler.displayGui(UltralightScreen(Page.of("ultralightfabric/funny")!!))
            1
        })
    }

    fun cleanup() {
        tmpFolder.deleteRecursively()
    }

    override fun onClose() {
        cleanup()
    }
}
