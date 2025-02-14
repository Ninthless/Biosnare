package com.biosnare.registry;

import com.biosnare.BioSnare;
import com.biosnare.item.BioSnareNetItem;
import com.biosnare.item.MobBallItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    // 创建物品
    public static final Item BIOSNARE_NET = new BioSnareNetItem(new FabricItemSettings().maxCount(1).maxDamage(10));
    public static final Item MOB_BALL = new MobBallItem(new FabricItemSettings().maxCount(16));

    // 创建物品组
    public static final ItemGroup BIOSNARE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BIOSNARE_NET))
            .displayName(Text.translatable("itemgroup.biosnare"))
            .entries((context, entries) -> {
                entries.add(BIOSNARE_NET);
                entries.add(MOB_BALL);
            })
            .build();

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(BioSnare.MOD_ID, "biosnare_net"), BIOSNARE_NET);
        Registry.register(Registries.ITEM, new Identifier(BioSnare.MOD_ID, "mob_ball"), MOB_BALL);
        Registry.register(Registries.ITEM_GROUP, new Identifier(BioSnare.MOD_ID, "biosnare_group"), BIOSNARE_GROUP);
    }
} 