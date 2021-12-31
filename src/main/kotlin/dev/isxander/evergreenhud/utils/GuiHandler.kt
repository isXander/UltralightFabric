/*
 * EvergreenHUD - A mod to improve your heads-up-display.
 * Copyright (c) isXander [2019 - 2021].
 *
 * This work is licensed under the CC BY-NC-SA 4.0 License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0
 */

package dev.isxander.evergreenhud.utils

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.gui.screen.Screen

object GuiHandler {
    private var gui: Screen? = null

    init {
        ClientTickEvents.END_CLIENT_TICK.register {
            if (gui != null) {
                mc.setScreen(gui)
                gui = null
            }
        }
    }

    fun displayGui(gui: Screen) {
        this.gui = gui
    }
}
