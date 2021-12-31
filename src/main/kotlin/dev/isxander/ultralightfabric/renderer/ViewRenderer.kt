/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.renderer

import com.labymedia.ultralight.UltralightView
import com.labymedia.ultralight.config.UltralightViewConfig
import net.minecraft.client.util.math.MatrixStack

/**
 * Render Views
 */
interface ViewRenderer {
    /**
     * Setup [viewConfig]
     */
    fun setupConfig(viewConfig: UltralightViewConfig)

    /**
     * Render view
     */
    fun render(view: UltralightView, matrices: MatrixStack)

    /**
     * Delete
     */
    fun delete()
}
