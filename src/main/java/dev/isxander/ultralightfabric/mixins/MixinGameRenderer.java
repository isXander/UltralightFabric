/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 * Copyright (c) 2016 - 2021 CCBlueX
 *
 * Changes have been made to convert the code into a library.
 */

package dev.isxander.ultralightfabric.mixins;

import dev.isxander.evergreenhud.event.EventListener;
import dev.isxander.evergreenhud.event.EventManagerKt;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "render", at = @At("HEAD"))
    public void onGameRender(CallbackInfo ci) {
        EventManagerKt.getEventBus().postConsumer(EventListener::onGameRender);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"))
    public void onScreenRender(Screen screen, MatrixStack matrices, int mouseX, int mouseY, float delta) {
        screen.render(matrices, mouseX, mouseY, delta);
        EventManagerKt.getEventBus().postConsumer((listener) -> listener.onScreenRender(screen, matrices, mouseX, mouseY, delta));
    }
}
