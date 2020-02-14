package de.rene_majewski.mc.forge.mods.statistics.config;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import de.rene_majewski.mc.forge.mods.statistics.db.DbType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

/**
 * Stellt den Zugriff auf die Konfiguration her.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
final class ClientConfig {
	/**
	 * Gibt ab, welche Datenbank-Type genutzt werden soll.
	 */
	ForgeConfigSpec.EnumValue<DbType> databaseType; 

	/**
	 * Name der Datenbank.
	 * 
	 * Wir der Datenbank-Treiber SQLite genutzt, so wird der Name der
	 * SQLite-Datei angegeben. Wird MySQL genutzt, so muss hier der
	 * Datenbank-Name angegeben werden.
	 */
	ForgeConfigSpec.ConfigValue<String> databaseName;
	
	/**
	 * Gibt den Server an, auf dem die Datenbank erreichbar ist.
	 */
	ForgeConfigSpec.ConfigValue<String> databaseHost;
	
	/**
	 * Gibt den Benutzer an, der zur Authentifierzung am Datenbank-Server
	 * benutzt werden soll.
	 */
	ForgeConfigSpec.ConfigValue<String> databaseUser; 

	/**
	 * Gibt das Passwort an, dass zur Authentifizierung am Datenbank-Server
	 * bentutz wrden soll.
	 */
	ForgeConfigSpec.ConfigValue<String> databasePassword; 
	
	/**
	 * Initialisiert den Zugriff auf die Konfiguration.
	 * 
	 * @param client
	 */
	ClientConfig(final ForgeConfigSpec.Builder client) {
		client.comment("Client configuration for Mod " + StatisticsMod.MOD_ID);
		
		initDatabaseConfig(client);
	}

	/**
	 * Initialisiert denZugriff auf die Datenbank-Konfiguration.
	 * 
	 * @param client
	 */
	private void initDatabaseConfig(ForgeConfigSpec.Builder client) {
		client.push("database");
		client.comment("Configuration for database uses");
		
		databaseType = client
				.comment("Which database should be used? Possible values are mysql or sqlite.")
				.translation(StatisticsMod.MOD_ID + ".config.database.type")
				.defineEnum("database.type", DbType.SQLITE);
		
		databaseName = client
				.comment("Database to be used.")
				.translation(StatisticsMod.MOD_ID + ".config.database.name")
				.define("database.name", new String("memory"));
		
		databaseHost = client
				.comment("Host to be used.")
				.translation(StatisticsMod.MOD_ID + ".config.database.host")
				.define("database.host", new String());
		
		databaseUser = client
				.comment("User tu be used for authentification.")
				.translation(StatisticsMod.MOD_ID + ".config.database.user")
				.define("database.user", new String());
		
		databasePassword = client
				.comment("Password to be used for authentification.")
				.translation(StatisticsMod.MOD_ID + ".config.database.password")
				.define("database.password", new String());
		
		client.pop();
	}
}
