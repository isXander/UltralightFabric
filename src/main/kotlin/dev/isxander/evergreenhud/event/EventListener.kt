/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.event

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack

interface EventListener {
    fun onGameRender() {}
    fun onScreenRender(screen: Screen, matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {}
    fun onRenderOverlay(matrices: MatrixStack, tickDelta: Float) {}
    fun onWindowResize(window: Long, width: Int, height: Int) {}
    fun onWindowFocus(window: Long, focused: Boolean) {}
    fun onMouseButton(window: Long, button: Int, action: Int, mods: Int) {}
    fun onMouseScroll(window: Long, horizontal: Double, vertical: Double) {}
    fun onMouseCursor(window: Long, x: Double, y: Double) {}
    fun onKeyboardKey(window: Long, key: Int, scancode: Int, action: Int, modifiers: Int) {}
    fun onKeyboardChar(window: Long, codePoint: Int) {}
    fun onSetScreen(screen: Screen?): Boolean { return false }
    fun onClose() {}
}
