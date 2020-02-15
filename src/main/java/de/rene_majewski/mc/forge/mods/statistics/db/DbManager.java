package de.rene_majewski.mc.forge.mods.statistics.db;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import de.rene_majewski.mc.forge.mods.statistics.config.Config;
import de.rene_majewski.mc.forge.mods.statistics.db.dao.LoginDaoImpl;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableLogin;

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
	private JdbcConnectionSource connection;
	
	/**
	 * Speichert ob die Datenbank initialisiert werden konnte.
	 */
	private boolean isConnected;
	
	/**
	 * Speichert das Dao-Objekt für die Login-Tabelle.
	 */
	private LoginDaoImpl daoLogin;

	/**
	 * Initialisieren der Verbindung zur Datenbank.
	 */
	private DbManager() {
		connection = null;
		isConnected = false;
		daoLogin = null;
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
			String connectionCommand = "jdbc:mysql://" + host + "/" + database;
			connection = new JdbcConnectionSource(connectionCommand, user, passwd, new MysqlDatabaseType());
			
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
			Class.forName("org.sqlite.JDBC");

			String connectionCommand = "jdbc:sqlite:" + fileName;			
			connection = new JdbcConnectionSource(connectionCommand, new SqliteDatabaseType());
			
			LOGGER.info("Connected to SQLite file: " + fileName);
		} catch (ClassNotFoundException e) { 
			LOGGER.error("Database driver not found.", e);
		} catch (Exception ex) {
			LOGGER.error("The SQLite file '" + fileName + "' could not be opened.", ex);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gibt die Verbindung zur Datenbank zurück.
	 * 
	 * @return Hergestellte Verbindung zur Datenbank.
	 */
	public JdbcConnectionSource getConnection() {
		return connection;
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
		closeConnection();
		
		switch (Config.databaseType) {
			case SQLITE:
				isConnected = connectToSqlite(Config.databaseName);
				break;
				
			case MYSQL:
				isConnected = conntectToMysql(
						Config.databaseHost,
						Config.databaseName,
						Config.databaseUser,
						Config.databasePassword
				);
				break;
		}
		
		if (isConnected && connection != null) {
			initDbSchema();
		}

	}
	
	/**
	 * Erzeugt die Dao-Objekte und erstellt die Datenbank-Tabellen, sofern diese
	 * noch nicht angelegt wurden.
	 */
	private void initDbSchema() {
		daoLogin = (LoginDaoImpl) initDao(TableLogin.class);
	}
	
	private Dao<?, ?> initDao(Class<?> clazz) {
		Dao<?, ?> dao;
		try {
			dao = DaoManager.createDao(connection, clazz);
			
			if (!dao.isTableExists()) {
				TableUtils.createTableIfNotExists(connection, clazz);
			}
			
			return dao;
		} catch (SQLException e) {
			LOGGER.error("Error while initializing the DAO object with name '" + clazz.getSimpleName() + "'");
		}
		
		return null;
	}

	/**
	 * Schließt die Verbindung zur Datenbank.
	 * 
	 * Wenn eine Verbindung zur Datenbank hergestellt wurde, wird diese
	 * geschlossen.
	 */
	public void closeConnection() {
		if (isConnected && connection != null) {
			try {
				connection.close();
				LOGGER.debug("Close connection to database.");
			} catch (IOException e) {
				LOGGER.error("Error when closing the database connection", e);
			}

			isConnected = false;
		}
	}
	
	/**
	 * Fügt den übergebenen Datensatz in die Login-Tabelle ein.
	 * 
	 * @param login Datensatz, der in der Login-Tabelle erstellt werden soll. 
	 */
	public void addLogin(TableLogin login) {
		try {
			daoLogin.create(login);
			LOGGER.debug("Create login record");
		} catch (SQLException e) {
			LOGGER.error("An error occurred while creating a record in '" + login.getClass().getSimpleName() + "'.");
		}
	}
	
	/**
	 * Updatet den angegeben Datensatz in der Datenbank.
	 * 
	 * @param login Datensatz der in der Datenbank aktualisiert werden soll.
	 */
	public void updateLogin(TableLogin login) {
		try {
			if (daoLogin.update(login) > 0) {
				LOGGER.debug("Login record was successfully updated.");
			} else {
				LOGGER.debug("The login record could not be updated.");				
			}
		} catch (SQLException e) {
			LOGGER.error("Error while updating a login record.", e);
		}
	}
	
	/**
	 * Ermittelt den letzten Datensatz der Login-Tabelle.
	 * 
	 * @return Der letzte Datensatz der Login-Tabelle. Bei einem Fehler wird
	 * <b>null</b> zurückgegeben.
	 */
	public TableLogin getLatestLogin() {
		try {
			return daoLogin.queryBuilder().orderBy("id", false).limit(1L).query().get(0);
		} catch (SQLException e) {
			LOGGER.error("Error while determining the last login record.", e);
		}
		
		return null;
	}
}
