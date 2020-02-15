package de.rene_majewski.mc.forge.mods.statistics.db.dao;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import de.rene_majewski.mc.forge.mods.statistics.db.dao.interfaces.IChatDao;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableChat;

public class ChatDaoImpl extends BaseDaoImpl<TableChat, Long> implements IChatDao {
	public ChatDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, TableChat.class);
	}
}
