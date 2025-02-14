package com.biosnare.mixin;

import com.biosnare.item.BioSnareNetItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityInteractionMixin {
    
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Entity thisEntity = (Entity)(Object)this;
        // 如果玩家手持捕捉网且目标是生物实体
        if (player.getStackInHand(hand).getItem() instanceof BioSnareNetItem && thisEntity instanceof LivingEntity) {
            // 如果在客户端，直接返回SUCCESS来阻止默认交互
            if (player.getWorld().isClient) {
                cir.setReturnValue(ActionResult.SUCCESS);
                cir.cancel();
                return;
            }
            
            // 在服务端执行捕捉逻辑
            BioSnareNetItem net = (BioSnareNetItem)player.getStackInHand(hand).getItem();
            net.useOnEntity(player.getStackInHand(hand), player, (LivingEntity)thisEntity, hand);
            // 无论结果如何都取消后续交互
            cir.setReturnValue(ActionResult.SUCCESS);
            cir.cancel();
        }
    }
} 