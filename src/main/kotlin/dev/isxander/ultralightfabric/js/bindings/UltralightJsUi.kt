/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.js.bindings

import dev.isxander.evergreenhud.utils.mc
import dev.isxander.ultralightfabric.ScreenView
import dev.isxander.ultralightfabric.UltralightEngine
import dev.isxander.ultralightfabric.pages.Page
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText

/**
 * Referenced by JS as `ui`
 */
object UltralightJsUi {
    fun get(name: String) = UltralightScreen(Page.of(name)!!)

    fun open(name: String, parent: Screen?) {
        mc.setScreen(get(name))
    }

}

class UltralightScreen(val page: Page, private val parent: Screen? = mc.currentScreen) : Screen(LiteralText(page.name)) {
    var firstRender = true
    lateinit var view: ScreenView
        private set

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        if (firstRender) {
            view = UltralightEngine.newScreenView(this).apply {
                loadPage(page)
            }

            firstRender = false
        }
    }

    override fun onClose() {
        UltralightEngine.removeView(view)
        super.onClose()
    }
}
