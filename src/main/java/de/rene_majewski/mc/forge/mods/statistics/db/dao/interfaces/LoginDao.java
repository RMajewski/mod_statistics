package de.rene_majewski.mc.forge.mods.statistics.db.dao.interfaces;

import com.j256.ormlite.dao.Dao;

import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableLogin;

/**
 * Dao für die Datenbank-Tabelle für die Logins.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public interface LoginDao extends Dao<TableLogin, Long> {
}
