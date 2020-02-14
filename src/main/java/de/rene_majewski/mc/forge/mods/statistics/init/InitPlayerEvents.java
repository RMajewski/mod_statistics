package de.rene_majewski.mc.forge.mods.statistics.init;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


/**
 * Reagiert auf Spieler-Events.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@EventBusSubscriber(modid = StatisticsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitPlayerEvents {

	/**
	 * Spieler loggt sich auf einen Server ein.
	 */
	@SubscribeEvent
	public static void InitDatabase(LoggedInEvent event) {
	}

}
