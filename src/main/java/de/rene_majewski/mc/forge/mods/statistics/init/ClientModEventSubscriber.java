package de.rene_majewski.mc.forge.mods.statistics.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Registriert die Ereignisse des Clients.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@EventBusSubscriber(
		modid = StatisticsMod.MOD_ID,
		bus = EventBusSubscriber.Bus.MOD,
		value = Dist.CLIENT
)
public final class ClientModEventSubscriber {
	private static final Logger LOGGER = LogManager.getLogger(StatisticsMod.MOD_ID + " Client Mod Event Subscriber");
	
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
		LOGGER.debug("Setup client events");
		MinecraftForge.EVENT_BUS.register(new InitChatEvents());
		MinecraftForge.EVENT_BUS.register(new InitPlayerEvents());
	}
}
