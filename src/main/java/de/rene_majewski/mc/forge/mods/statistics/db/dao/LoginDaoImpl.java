package de.rene_majewski.mc.forge.mods.statistics.db.dao;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import de.rene_majewski.mc.forge.mods.statistics.db.dao.interfaces.ILoginDao;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableLogin;

/**
 * Implementation des DAO-Objektes für Datenbank-Tabelle für die Logins.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public class LoginDaoImpl extends BaseDaoImpl<TableLogin, Long> implements ILoginDao {
	public LoginDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, TableLogin.class);
	}
}
