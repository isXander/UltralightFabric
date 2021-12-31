/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.js

import com.labymedia.ultralight.UltralightView
import com.labymedia.ultralight.databind.Databind
import com.labymedia.ultralight.databind.DatabindConfiguration
import com.labymedia.ultralight.javascript.JavascriptContext
import com.labymedia.ultralight.javascript.JavascriptObject
import dev.isxander.evergreenhud.event.eventBus
import dev.isxander.evergreenhud.utils.logger
import dev.isxander.evergreenhud.utils.mc
import dev.isxander.ultralightfabric.ScreenView
import dev.isxander.ultralightfabric.UltralightEngine
import dev.isxander.ultralightfabric.View
import dev.isxander.ultralightfabric.js.bindings.UltralightJsUi
import dev.isxander.ultralightfabric.js.bindings.UltralightJsUtils
import dev.isxander.ultralightfabric.utils.ThreadLock
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.util.ActionResult

/**
 * Context setup
 */
class UltralightJsContext(view: View, ulView: ThreadLock<UltralightView>) {
    val contextProvider = ViewContextProvider(ulView)
    val databind = Databind(
        DatabindConfiguration
            .builder()
            .contextProviderFactory(ViewContextProvider.Factory(ulView))
            .build()
    )

    fun setupContext(view: View, context: JavascriptContext) {
        val globalContext = context.globalContext
        val globalObject = globalContext.globalObject

        globalObject.setProperty(
            "engine",
            databind.conversionUtils.toJavascript(context, UltralightEngine),
            0
        )

        globalObject.setProperty(
            "view",
            databind.conversionUtils.toJavascript(context, view),
            0
        )

        // todo: minecraft has to be remapped
        globalObject.setProperty(
            "minecraft",
            databind.conversionUtils.toJavascript(context, mc),
            0
        )

        globalObject.setProperty(
            "ui",
            databind.conversionUtils.toJavascript(context, UltralightJsUi),
            0
        )

        globalObject.setProperty(
            "utils",
            databind.conversionUtils.toJavascript(context, UltralightJsUtils),
            0
        )

        globalObject.setProperty(
            "eventBus",
            databind.conversionUtils.toJavascript(context, eventBus),
            0
        )

        globalObject.setProperty(
            "logger",
            databind.conversionUtils.toJavascript(context, logger),
            0
        )

        if (view is ScreenView) {
            globalObject.setProperty(
                "screen",
                databind.conversionUtils.toJavascript(context, view.screen),
                0
            )

            val parentScreen = view.parentScreen

            if (parentScreen != null) {
                globalObject.setProperty(
                    "parentScreen",
                    databind.conversionUtils.toJavascript(context, view.parentScreen),
                    0
                )
            }
        }

        SetupContextCallback.EVENT.invoker().setupContext(context, globalObject, databind)
    }

    fun interface SetupContextCallback {
        fun setupContext(context: JavascriptContext, globalObject: JavascriptObject, databind: Databind): ActionResult

        companion object {
            @JvmStatic
            val EVENT = EventFactory.createArrayBacked(SetupContextCallback::class.java) { listeners -> SetupContextCallback { context, globalObject, databind ->
                for (listener in listeners) {
                    val result = listener.setupContext(context, globalObject, databind)

                    if (result != ActionResult.SUCCESS) {
                        return@SetupContextCallback result
                    }
                }

                return@SetupContextCallback ActionResult.SUCCESS
            }}
        }
    }
}
