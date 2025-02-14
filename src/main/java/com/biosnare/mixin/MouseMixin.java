package com.biosnare.mixin;

import com.biosnare.item.BioSnareNetItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (this.client.player != null && button == 1 && action == 1) { // 右键按下
            // 如果玩家手持捕捉网
            boolean isHoldingNet = this.client.player.getMainHandStack().getItem() instanceof BioSnareNetItem ||
                                 this.client.player.getOffHandStack().getItem() instanceof BioSnareNetItem;
            
            // 获取玩家视线所指的目标
            HitResult hit = this.client.crosshairTarget;
            if (isHoldingNet && hit != null && hit.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hit;
                Entity entity = entityHit.getEntity();
                
                if (entity instanceof LivingEntity) {
                    // 取消客户端的右键事件
                    ci.cancel();
                    
                    // 手动调用物品的使用方法
                    Hand hand = this.client.player.getMainHandStack().getItem() instanceof BioSnareNetItem ? Hand.MAIN_HAND : Hand.OFF_HAND;
                    this.client.interactionManager.interactEntity(this.client.player, entity, hand);
                }
            }
        }
    }
} 