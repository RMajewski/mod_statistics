package de.rene_majewski.mc.forge.mods.statistics.config;

import de.rene_majewski.mc.forge.mods.statistics.db.DbType;

/**
 * Stellt die Konfiguration für die anderen Packages bereit.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public class Config {
	/**
	 * Welche Datenbank soll genutzt werden?
	 */
	public static DbType databaseType;
	
	/**
	 * Name der Datenbank.
	 * 
	 * @see ClientConfig.database_name
	 */
	public static String databaseName;
	
	/**
	 * Name des Datenbank-Servers.
	 */
	public static String databaseHost;
	
	/**
	 * Benutzername zur Authentifizierung am Datenbank-Server.
	 */
	public static String databaseUser;
	
	/**
	 * Passwort zur Authentifizierung am Datenbank-Server.
	 */
	public static String databasePassword;
}
