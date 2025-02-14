package com.biosnare;

import com.biosnare.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioSnare implements ModInitializer {
	public static final String MOD_ID = "biosnare";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		LOGGER.info("BioSnare mod initialized!");
	}
}