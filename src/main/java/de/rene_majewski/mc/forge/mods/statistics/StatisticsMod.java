package de.rene_majewski.mc.forge.mods.statistics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Hauptklasse der Mod.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@Mod(StatisticsMod.MOD_ID)
public class StatisticsMod {
	/**
	 * Die ID der Mod unter der die Mod in Minecraft registriert wird.
	 */
	public static final String MOD_ID = "statistics";

	/**
	 * Logger der für diese Mod genutzt werden soll.
	 */
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	/**
	 * Initialisiert die Hauptklasse dieser Mod.
	 */
	public StatisticsMod() {
		ModLoadingContext.get().registerConfig(
				ModConfig.Type.CLIENT,
				de.rene_majewski.mc.forge.mods.statistics.config.ModConfig.CLIENT_SPEC);
	}
}
