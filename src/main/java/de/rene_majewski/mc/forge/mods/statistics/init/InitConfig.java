package de.rene_majewski.mc.forge.mods.statistics.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import de.rene_majewski.mc.forge.mods.statistics.config.ModConfig;
import de.rene_majewski.mc.forge.mods.statistics.db.DbManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;

/**
 * Reagiert auf Änderungen in der Konfiguration.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@EventBusSubscriber(modid = StatisticsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class InitConfig {
	private static final Logger LOGGER = LogManager.getLogger(StatisticsMod.MOD_ID + " od Event Subscriber");
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent event) {
		final net.minecraftforge.fml.config.ModConfig config = event.getConfig();
		
		if (config.getSpec() == ModConfig.CLIENT_SPEC) {
			ModConfig.bakeClient(config);
			DbManager.getInstance().initConnection();
			LOGGER.debug("Baked client config");
		}
	}
}
