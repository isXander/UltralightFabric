/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.mixins;

import dev.isxander.evergreenhud.event.EventListener;
import dev.isxander.evergreenhud.event.EventManagerKt;
import dev.isxander.ultralightfabric.UltralightEngine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void onSetScreen(Screen screen, CallbackInfo ci) {
        if (EventManagerKt.getEventBus().post((listener) -> listener.onSetScreen(screen)).stream().anyMatch(Boolean::booleanValue)) {
            ci.cancel();
        }
    }

    @Inject(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;close()V", shift = At.Shift.BEFORE))
    public void onClose(CallbackInfo ci) {
        EventManagerKt.getEventBus().postConsumer(EventListener::onClose);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;onResolutionChanged()V"))
    public void onStartGame(RunArgs args, CallbackInfo ci) {
        UltralightEngine.INSTANCE.init();
    }
}
