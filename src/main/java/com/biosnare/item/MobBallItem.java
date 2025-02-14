package com.biosnare.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;
import java.util.Optional;

public class MobBallItem extends Item {
    public MobBallItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        
        if (!hasMobStored(stack)) {
            return TypedActionResult.pass(stack);
        }

        if (!world.isClient) {
            // 获取玩家视线方向的方块
            HitResult hitResult = user.raycast(5.0D, 1.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());

                // 释放生物
                if (releaseMob(stack, world, pos)) {
                    // 播放音效
                    world.playSound(null, user.getX(), user.getY(), user.getZ(),
                            SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.PLAYERS,
                            0.5F, 1.0F);

                    // 如果不是创造模式，消耗物品
                    if (!user.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                }
            }
        }

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        
        if (hasMobStored(stack)) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("StoredMobId")) {
                String mobId = nbt.getString("StoredMobId");
                tooltip.add(Text.translatable("item.biosnare.mob_ball.stored_mob", 
                    Text.translatable("entity." + mobId.replace(":", "."))));
            }
        }
    }

    public static void storeMobData(ItemStack stack, LivingEntity entity) {
        NbtCompound nbt = new NbtCompound();
        
        // 保存实体ID
        Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
        nbt.putString("StoredMobId", entityId.toString());
        
        // 保存实体数据
        NbtCompound entityNbt = new NbtCompound();
        entity.writeNbt(entityNbt);
        nbt.put("StoredMobData", entityNbt);
        
        stack.setNbt(nbt);
    }

    private boolean hasMobStored(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.contains("StoredMobId");
    }

    private boolean releaseMob(ItemStack stack, World world, BlockPos pos) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains("StoredMobId") || !nbt.contains("StoredMobData")) {
            return false;
        }

        String mobId = nbt.getString("StoredMobId");
        Optional<EntityType<?>> entityType = Registries.ENTITY_TYPE.getOrEmpty(new Identifier(mobId));
        
        if (entityType.isPresent()) {
            Entity entity = entityType.get().create(world);
            if (entity != null) {
                NbtCompound entityNbt = nbt.getCompound("StoredMobData");
                entity.readNbt(entityNbt);
                
                // 设置实体位置
                entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                
                // 生成实体
                return world.spawnEntity(entity);
            }
        }
        
        return false;
    }
} 