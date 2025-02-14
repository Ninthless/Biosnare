package com.biosnare.mixin;

import com.biosnare.item.BioSnareNetItem;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).getItem() instanceof BioSnareNetItem net) {
            // 如果在客户端，直接返回SUCCESS来阻止默认交互
            if (player.getWorld().isClient) {
                cir.setReturnValue(ActionResult.SUCCESS);
                cir.cancel();
                return;
            }

            // 直接调用捕捉网的捕捉逻辑
            ActionResult result = net.useOnEntity(player.getStackInHand(hand), player, (LivingEntity)(Object)this, hand);
            cir.setReturnValue(result);
            cir.cancel();
        }
    }
} 