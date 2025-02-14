package com.biosnare.item;

import com.biosnare.registry.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.List;

public class BioSnareNetItem extends Item {
    public BioSnareNetItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        // 如果在客户端，返回SUCCESS来阻止默认交互
        if (player.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        // 如果是玩家，不执行捕捉
        if (entity instanceof PlayerEntity) {
            return ActionResult.PASS;
        }

        World world = player.getWorld();
        
        // 创建生物球物品并设置NBT数据
        ItemStack mobBall = new ItemStack(ModItems.MOB_BALL);
        MobBallItem.storeMobData(mobBall, entity);

        // 给予玩家生物球
        if (!player.getInventory().insertStack(mobBall)) {
            player.dropItem(mobBall, false);
        }

        // 移除被捕捉的实体
        entity.discard();

        // 播放音效
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F,
                1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);

        // 发送成功消息
        player.sendMessage(Text.translatable("message.biosnare.capture_success"), true);

        // 损坏捕捉网
        stack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        
        // 显示剩余耐久度
        int maxDurability = stack.getMaxDamage();
        int currentDurability = maxDurability - stack.getDamage();
        tooltip.add(Text.translatable("tooltip.biosnare.durability", currentDurability));
    }
} 