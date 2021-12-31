/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.mixins;

import dev.isxander.evergreenhud.event.EventManagerKt;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {
    @Shadow @Final private long handle;

    /**
     * Hook window resize
     */
    @Inject(method = "onWindowSizeChanged", at = @At("HEAD"))
    public void onResizeWindow(long window, int width, int height, CallbackInfo ci) {
        if (window == handle)
            EventManagerKt.getEventBus().postConsumer((listener) -> listener.onWindowResize(window, width, height));
    }

    /**
     * Hook window resize
     */
    @Inject(method = "onWindowFocusChanged", at = @At(value = "FIELD", target = "Lnet/minecraft/client/util/Window;eventHandler:Lnet/minecraft/client/WindowEventHandler;"))
    public void hookFocus(long window, boolean focused, CallbackInfo callbackInfo) {
        EventManagerKt.getEventBus().postConsumer((listener) -> listener.onWindowFocus(window, focused));
    }
}
