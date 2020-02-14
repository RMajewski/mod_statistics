package de.rene_majewski.mc.forge.mods.statistics.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

/**
 * Erstellt den Zugriff auf die Konfigurations-Datei.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@Mod.EventBusSubscriber
public class ModConfig {
	/**
	 * Builder für die Client-Konfiguration.
	 */
	public static final ForgeConfigSpec CLIENT_SPEC;
	
	/**
	 * zugriff auf die Client-Konfiguration.
	 */
	static final ClientConfig CLIENT;
	
	static {
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT = specPair.getLeft();
		CLIENT_SPEC = specPair.getRight();
	}
	
	/**
	 * Liest die Konfiguration aus.
	 * 
	 * @param config Konfiguration-Klasse, die die Daten bereitstellt. 
	 */
	public static void bakeClient(
			final net.minecraftforge.fml.config.ModConfig config
	) {
		// Datenbank-Konfiguration
		Config.databaseType = CLIENT.databaseType.get();
		Config.databaseName = CLIENT.databaseName.get();
		Config.databaseHost = CLIENT.databaseHost.get();
		Config.databaseUser = CLIENT.databaseUser.get();
		Config.databaseUser = CLIENT.databaseUser.get();
	}
}
