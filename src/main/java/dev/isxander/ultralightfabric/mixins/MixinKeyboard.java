/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.mixins;

import dev.isxander.evergreenhud.event.EventManagerKt;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    /**
     * Hook key event
     */
    @Inject(method = "onKey", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", shift = At.Shift.BEFORE, ordinal = 0))
    public void onKeyboardKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        EventManagerKt.getEventBus().postConsumer((listener) -> listener.onKeyboardKey(window, key, scancode, action, modifiers));
    }

    /**
     * Hook char event
     */
    @Inject(method = "onChar", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", shift = At.Shift.BEFORE))
    public void onKeyboardChar(long window, int codePoint, int modifiers, CallbackInfo ci) {
        EventManagerKt.getEventBus().postConsumer((listener) -> listener.onKeyboardChar(window, codePoint));
    }
}
