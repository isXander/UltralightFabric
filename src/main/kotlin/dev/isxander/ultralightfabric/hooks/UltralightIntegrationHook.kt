/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.hooks

import dev.isxander.evergreenhud.event.EventListener
import dev.isxander.ultralightfabric.RenderLayer
import dev.isxander.ultralightfabric.UltralightEngine
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack

object UltralightIntegrationHook : EventListener {
    override fun onGameRender() {
        UltralightEngine.update()
    }

    override fun onScreenRender(screen: Screen, matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        UltralightEngine.render(RenderLayer.SCREEN_LAYER, matrices)
    }

    override fun onRenderOverlay(matrices: MatrixStack, tickDelta: Float) {
        UltralightEngine.render(RenderLayer.OVERLAY_LAYER, matrices)
    }

    override fun onWindowResize(window: Long, width: Int, height: Int) {
        UltralightEngine.resize(width.toLong(), height.toLong())
    }

    override fun onWindowFocus(window: Long, focused: Boolean) {
        UltralightEngine.inputAdapter.focusCallback(window, focused)
    }

    override fun onMouseButton(window: Long, button: Int, action: Int, mods: Int) {
        UltralightEngine.inputAdapter.mouseButtonCallback(window, button, action, mods)
    }

    override fun onMouseScroll(window: Long, horizontal: Double, vertical: Double) {
        UltralightEngine.inputAdapter.scrollCallback(window, horizontal, vertical)
    }

    override fun onMouseCursor(window: Long, x: Double, y: Double) {
        UltralightEngine.inputAdapter.cursorPosCallback(window, x, y)
    }

    override fun onKeyboardKey(window: Long, key: Int, scancode: Int, action: Int, modifiers: Int) {
        UltralightEngine.inputAdapter.keyCallback(window, key, scancode, action, modifiers)
    }

    override fun onKeyboardChar(window: Long, codePoint: Int) {
        UltralightEngine.inputAdapter.charCallback(window, codePoint)
    }
}
