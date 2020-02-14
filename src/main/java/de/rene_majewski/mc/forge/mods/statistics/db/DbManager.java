package de.rene_majewski.mc.forge.mods.statistics.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static final Logger LOGGER = LogManager.getLogger(StatisticsMod.MOD_ID + " DbManager");
	
	
	/**
	 * Speichert die Verbindung zur Datenbank.
	 */
	private Connection connection;
	
	/**
	 * Speichert welche Datenbank genutzt wird.
	 */
	private DbType type;

	/**
	 * Initialisieren der Verbindung zur Datenbank.
	 */
	private DbManager() {
		connection = null;
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
	 * Stellt die Verbindung zur angegebenen MySQL-Datenbank her.
	 * 
	 * @param host Name oder IP des Rechners, auf dem der MySQL-Server läuft.
	 * 
	 * @param database Name der Datenbank zu der eine Verbindung hergestellt
	 * werden soll.
	 * 
	 * @param user Name des Benutzers der zur Authentifizierung genutzt werden
	 * soll.
	 * 
	 * @param passwd Passwort, welches zur Authentifizierung genutzt werden
	 * soll.
	 *  
	 * @return Es wird <b>true</b> zurück gegeben, wenn eine Verbindung zur
	 * Datenbank hergestellt werden konnte. Wenn ein Fehler aufgetreten ist
	 * oder die Verbindung zur Datenbank nicht hergestellt werden konnte, wird
	 * <b>false</b> zurück gegeben.
	 */
	private boolean conntectToMysql(String host, String database, String user, String passwd) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			String connectionCommand = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + passwd;
			connection = DriverManager.getConnection(connectionCommand);
			
			LOGGER.info("The connection to the MySQL database '" + database + "' has been established.");
		} catch (Exception ex) {
			LOGGER.error("The connection to the MySQL database '" + database + "' could not be established.", ex);
			return false;
		}
		return true;
	}
	
	/**
	 * Stellt die Verbindung zur angegeben SQLite-Datei her.
	 * 
	 * @param fileName Name der SQLite-Datenbank-Datei.
	 * 
	 * @return Es wird <b>true</b> zurück gegeben, wenn eine Verbindung zur
	 * Datenbank hergestellt werden konnte. Wenn ein Fehler aufgetreten ist
	 * oder die Verbindung zur Datenbank nicht hergestellt werden konnte, wird
	 * <b>false</b> zurück gegeben.
	 */
	private boolean connectToSqlite(String fileName) {
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			
			String connectionCommand = "jdbc:sqlite:" + fileName;			
			connection = DriverManager.getConnection(connectionCommand);
			
			LOGGER.info("Connected to SQLite file: " + fileName);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Not found the JDBC driver for SQLite.", e);
			return false;
		} catch (Exception ex) {
			StatisticsMod.LOGGER.error("The SQLite file '" + fileName + "' could not be opened.", ex);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück.
	 * 
	 * @return Hergestellte Verbindung zur Datenbank.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Erzeugt eine Verbindung zur Datenbank.
	 * 
	 * Besteht schon eine Verbindung zur Datenbank wird diese zu erst
	 * geschlossen.
	 */
	public void initConnection() {
		closeConnection();
		
		type = Config.databaseType;
		
		switch (type) {
			case SQLITE:
				connectToSqlite(Config.databaseName);
				break;
				
			case MYSQL:
				conntectToMysql(
						Config.databaseHost,
						Config.databaseName,
						Config.databaseUser,
						Config.databasePassword
				);
				break;
		}

	}
	
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				LOGGER.debug("Close connection to database.");
			} catch (SQLException e) {
				LOGGER.error("Error when closing the database connection", e);
			}
		}
	}
}
