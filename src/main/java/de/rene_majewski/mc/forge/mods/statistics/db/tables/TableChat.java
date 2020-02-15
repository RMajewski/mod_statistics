package de.rene_majewski.mc.forge.mods.statistics.db.tables;

import java.sql.Timestamp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.rene_majewski.mc.forge.mods.statistics.db.dao.ChatDaoImpl;

/**
 * Speichert Chat-Nachrichten.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
@DatabaseTable(tableName = "statistics_chat", daoClass = ChatDaoImpl.class)
public class TableChat {
	/**
	 * Speichert die ID des Datensatzes.
	 */
	@DatabaseField(generatedId = true, useGetSet = true)
	private long id;
	
	/**
	 * Speichert die Chat-Nachricht.
	 */
	@DatabaseField(canBeNull = false, useGetSet = true, columnDefinition = "TEXT")
	private String message;
	
	/**
	 * Speichert den Timestamp, wann die Nachricht geschrieben / empfangen wurde.
	 */
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Timestamp timestamp;

	/**
	 * Initialisiert einen leeren Datensatz.
	 */
	public TableChat() {
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
	 * Gibt die Chat-Nachricht zurück.
	 * 
	 * @return Chat-Nachricht
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setzt die Char-Nachricht.
	 * 
	 * @param message Chat-Nachricht
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gibt das Datum und die Zeit zurück, zu der die Chat-Nachricht geschrieben
	 * wurde.
	 * 
	 * @return Datum und Zeit der Chat-Nachricht.
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Setzt das Datum und die Zeit, an dem die Nachricht geschrieben wurde.
	 * 
	 * @param timestamp Datum und Zeit der Chat-Nachricht.
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
