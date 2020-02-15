package de.rene_majewski.mc.forge.mods.statistics.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import de.rene_majewski.mc.forge.mods.statistics.config.Config;

/**
 * Stellt die Verbindung zur Datenbank mit den entsprechenden Treiber her.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public class DbManager {
	/**
	 * Speichert die Instanz dieser Klasse.
	 */
	private static DbManager instanceDbManager;
	
	/**
	 * Logger für diese Klasse.
	 */
	static final Logger LOGGER = LogManager.getLogger(StatisticsMod.MOD_ID + " DbManager");
	
	/**
	 * Instanz der Abfragen-Klasse.
	 */
	private Query query;

	/**
	 * Initialisieren der Verbindung zur Datenbank.
	 */
	private DbManager() {
		query = new Query();
	}
	
	/**
	 * Gibt die Instanz dieser Klasse zurück.
	 * 
	 * Wenn noch keine Instanz dieser Klasse existiert, wird die Instanz
	 * erzeugt, bevor sie zurückgegeben wird.
	 * 
	 * @return Instanz dieser Klasse.
	 */
	public static DbManager getInstance() {
		if (DbManager.instanceDbManager == null) {
			DbManager.instanceDbManager = new DbManager();
		}
		
		return DbManager.instanceDbManager;
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück.
	 * 
	 * @return Hergestellte Verbindung zur Datenbank.
	 */
	public JdbcConnectionSource getConnection() {
		return query.getConnection();
	}

	/**
	 * Erzeugt eine Verbindung zur Datenbank.
	 * 
	 * Besteht schon eine Verbindung zur Datenbank wird diese zu erst
	 * geschlossen.
	 * 
	 * Wenn die Verbindung zur Datenbank hergestellt werden konnte, wird das
	 * Datenbank-Schema geladen.
	 */
	public void initConnection() {
		query.closeConnection();
		
		switch (Config.databaseType) {
			case SQLITE:
				query.connectToSqlite(Config.databaseName);
				break;
				
			case MYSQL:
				query.conntectToMysql(
						Config.databaseHost,
						Config.databaseName,
						Config.databaseUser,
						Config.databasePassword
				);
				break;
		}
	}
	
	/**
	 * Gibt die Instanz der Query-Klasse zurück.
	 * 
	 * @return Instanz der Query-Klasse.
	 */
	public Query queries() {
		return query;
	}
}
