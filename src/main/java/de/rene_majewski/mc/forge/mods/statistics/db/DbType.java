package de.rene_majewski.mc.forge.mods.statistics.db;

/**
 * Speichert die unterstützen Arten des Datenbank-Zugriffs.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public enum DbType {
	/**
	 * Gibt an, dass MySQL als Datenbank-Treiber genutzt werden soll.
	 */
	MYSQL,
	
	/**
	 * Gibt an, dass SQLite als Datenbank-Treiber genutzt werden soll.
	 */
	SQLITE
}
