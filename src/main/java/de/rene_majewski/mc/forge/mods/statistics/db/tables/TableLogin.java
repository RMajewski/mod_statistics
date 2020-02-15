package de.rene_majewski.mc.forge.mods.statistics.db.tables;

import java.sql.Timestamp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.rene_majewski.mc.forge.mods.statistics.db.dao.LoginDaoImpl;

/**
 * Stellt die Datenbank-Tabelle für die Logins-Logouts bereit.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@DatabaseTable(tableName = "statistics_logins", daoClass = LoginDaoImpl.class)
public class TableLogin {
	/**
	 * Speichert die ID des Datensatzes.
	 */
	@DatabaseField(generatedId = true, useGetSet = true)
	private long id;
	
	/**
	 * Speichert das Datum und die Zeit, wann ein Server betreten wurde.
	 */
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Timestamp loginDateTime;
	
	/**
	 * Speichert das Datum und die Zeit, wann ein Server verlassen wurde.
	 */
	@DatabaseField(canBeNull = true, useGetSet = true)
	private Timestamp logoutDateTime;

	/**
	 * Speichert den Server-Namen der benutzt wurde.
	 */
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String serverName;

	/**
	 * Konstruktor
	 * 
	 * Initialisiert diese Klasse.
	 */
	public TableLogin() {
		this.serverName = new String();
	}
	
	/**
	 * Initialisiert einen Datensatz.
	 * 
	 * @param login Datum und Uhrzeit des Logins.
	 * 
	 * @param logout Datum und Uhrzeit des Logouts.
	 * 
	 * @param serverName Name des Server der benutzt wurde.
	 */
	public TableLogin(Timestamp login, Timestamp logout, String serverName) {
		this.loginDateTime = login;
		this.logoutDateTime = logout;
		this.serverName = serverName;
	}

	/**
	 * Gibt die ID des Datensatzes zurück.
	 * 
	 * @return ID des Datensatzes.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt die ID des Datensatzes.
	 * 
	 * @param id Neue ID des Datensatzes.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gibt das Datum und die Uhrzeit des Logins zurück.
	 * 
	 * @return Datum und Uhrzeit des Logins.
	 */
	public Timestamp getLoginDateTime() {
		return loginDateTime;
	}

	/**
	 * Setzt das Datum und die Uhrzeit des Logins.
	 * 
	 * @param loginDateTime Neues Datum und neue Uhrzeit des Logins.
	 */
	public void setLoginDateTime(Timestamp loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	/**
	 * Gibt das Datum und die Uhrzeit des Logouts zurück.
	 * 
	 * @return Datum und Uhrzeit des Logouts.
	 */
	public Timestamp getLogoutDateTime() {
		return logoutDateTime;
	}

	/**
	 * Setzt das Datum und die Uhrzeit des Logouts.
	 * 
	 * @param logoutDateTime Neues Datum und neue Uhrzeit des Logouts.
	 */
	public void setLogoutDateTime(Timestamp logoutDateTime) {
		this.logoutDateTime = logoutDateTime;
	}

	/**
	 * Gibt des Server-Names zurück.
	 * 
	 * @return Name des Servers.
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Setzt den Server-Namen.
	 * 
	 * @param serverName Neuer Name des Servers.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	
}
