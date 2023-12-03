package com.teampotato.letmeuseit.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow public abstract boolean isCreative();

    @Shadow @Final private ItemCooldowns cooldowns;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;tick()V"))
    private void onCooldownTick(ItemCooldowns instance) {
        if (!this.isCreative()) {
            this.cooldowns.tick();
        } else {
            if (!this.cooldowns.cooldowns.isEmpty()) {
                this.cooldowns.cooldowns.clear();
            }
        }
    }
}
