package de.rene_majewski.mc.forge.mods.statistics.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Diese Klasse stellt Hilfs-Methoden zum Umgang mit Datum und Zeit bereit.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public final class UtilDate {
	/**
	 * DateTimeFormatter um Angaben von Datum und Zeit für die
	 * Datenbank-Abfragen aufzubereiten.
	 */
	public final static DateTimeFormatter DATE_TIME_FORMATTER = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

	/**
	 * Gibt das aktuelle Datum und die aktuelle Zeit als Zeichenkette zurück.
	 * 
	 * @return Aktuelles Datum und aktuelle Zeit als Zeichenkette.
	 */
	public static String now() {
		return DATE_TIME_FORMATTER.format(LocalDateTime.now());
	}
}
