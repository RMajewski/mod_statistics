package de.rene_majewski.mc.forge.mods.statistics.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.rene_majewski.mc.forge.mods.statistics.db.dao.ChatDaoImpl;
import de.rene_majewski.mc.forge.mods.statistics.db.dao.LoginDaoImpl;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableChat;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableLogin;

/**
 * In dieser Klasse werden alle Abfragen zusammengefasst.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public final class Query {
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
	 * Speichert das Dao-Objekt für die Chat-Tabelle.
	 */
	private ChatDaoImpl daoChat;

	/**
	 * Initialisiert die Attribute dieser Klasse.
	 */
	public Query() {
		connection = null;
		isConnected = false;
		
		daoChat = null;
		daoLogin = null;
	}

	/**
	 * Gibt die aufgebaute Verbindung zur Datenbank zurück.
	 * 
	 * @return Aufgebaute Verbindung zur Datenbank.
	 */
	public JdbcConnectionSource getConnection() {
		return connection;
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
	void connectToSqlite(String fileName) {
		try {
			Class.forName("org.sqlite.JDBC");

			String connectionCommand = "jdbc:sqlite:" + fileName;			
			connection = new JdbcConnectionSource(connectionCommand, new SqliteDatabaseType());
			
			DbManager.LOGGER.info("Connected to SQLite file: " + fileName);
		} catch (ClassNotFoundException e) { 
			DbManager.LOGGER.error("Database driver not found.", e);
			isConnected = false;
			return;
		} catch (Exception ex) {
			DbManager.LOGGER.error("The SQLite file '" + fileName + "' could not be opened.", ex);
			isConnected = false;
			return;
		}
		
		isConnected = true;
		
		if (isConnected && connection != null) {
			initDbSchema();
		}
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
	void conntectToMysql(String host, String database, String user, String passwd) {
		try {
			String connectionCommand = "jdbc:mysql://" + host + "/" + database;
			connection = new JdbcConnectionSource(connectionCommand, user, passwd, new MysqlDatabaseType());
			
			DbManager.LOGGER.info("The connection to the MySQL database '" + database + "' has been established.");
		} catch (Exception ex) {
			DbManager.LOGGER.error("The connection to the MySQL database '" + database + "' could not be established.", ex);
			isConnected = false;
			return;
		}
		
		isConnected = true;
		
		if (isConnected && connection != null) {
			initDbSchema();
		}
	}

	/**
	 * Besteht eine Verbindung zur Datenbank?
	 * 
	 * @return Wenn eine Verbindung besteht, wird <b>true</b> zurückgegeben.
	 * Wenn keine Verbindung zur Datenbank besteht, wird <b>false</b>
	 * zurückgegeben.
	 */
	public boolean isConnected() {
		return isConnected;
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
				DbManager.LOGGER.debug("Close connection to database.");
			} catch (IOException e) {
				DbManager.LOGGER.error("Error when closing the database connection", e);
			}

			isConnected = false;
		}
	}
	
	/**
	 * Erzeugt die Dao-Objekte und erstellt die Datenbank-Tabellen, sofern diese
	 * noch nicht angelegt wurden.
	 */
	private void initDbSchema() {
		daoChat = (ChatDaoImpl) initDao(TableChat.class);
		daoLogin = (LoginDaoImpl) initDao(TableLogin.class);
	}
	
	/**
	 * Initialiesiert das DAO-Objekt zur angegebenen Datenbank-Tabelle.
	 * 
	 * @param clazz Datenbank-Tabelle, bei der das DAO-Objekt initialisiert
	 * werden soll.
	 * 
	 * @return Initialisiertes DAO-Objekt.
	 */
	private Dao<?, ?> initDao(Class<?> clazz) {
		Dao<?, ?> dao;
		try {
			dao = DaoManager.createDao(connection, clazz);
			
			if (!dao.isTableExists()) {
				TableUtils.createTableIfNotExists(connection, clazz);
			}
			
			return dao;
		} catch (SQLException e) {
			DbManager.LOGGER.error("Error while initializing the DAO object with name '" + clazz.getSimpleName() + "'");
		}
		
		return null;
	}

	/**
	 * Fügt den übergebenen Datensatz in die Login-Tabelle ein.
	 * 
	 * @param login Datensatz, der in der Login-Tabelle erstellt werden soll. 
	 */
	public void addLogin(TableLogin login) {
		try {
			daoLogin.create(login);
			DbManager.LOGGER.debug("Create login record");
		} catch (SQLException e) {
			DbManager.LOGGER.error("An error occurred while creating a record in '" + login.getClass().getSimpleName() + "'.");
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
				DbManager.LOGGER.debug("Login record was successfully updated.");
			} else {
				DbManager.LOGGER.debug("The login record could not be updated.");				
			}
		} catch (SQLException e) {
			DbManager.LOGGER.error("Error while updating a login record.", e);
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
			List<TableLogin> request = daoLogin.queryBuilder().orderBy("id", false).limit(1L).query();
			if (request.size() > 0) {
				return request.get(0);
			}
		} catch (SQLException e) {
			DbManager.LOGGER.error("Error while determining the last login record.", e);
		}
		
		return null;
	}

	/**
	 * Schreibt die angegebene Chat-Nachricht in die Datenbank.
	 * 
	 * @param data Chat-Nachricht, die in die Datenbank gespeichert werden
	 * soll.
	 */
	public void addChatMessage(TableChat data) {
		try {
			daoChat.create(data);
			DbManager.LOGGER.debug("Create chat record");
		} catch (SQLException e) {
			DbManager.LOGGER.error("An error occurred while creating a record in '" + data.getClass().getSimpleName() + "'.");
		}
	}
}
