package de.rene_majewski.mc.forge.mods.statistics.init;

import java.sql.Timestamp;

import de.rene_majewski.mc.forge.mods.statistics.StatisticsMod;
import de.rene_majewski.mc.forge.mods.statistics.db.DbManager;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableLogin;
import de.rene_majewski.mc.forge.mods.statistics.util.UtilDate;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


/**
 * Reagiert auf Spieler-Events.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public class InitPlayerEvents {

	/**
	 * Spieler loggt sich auf einen Server ein.
	 */
	@SubscribeEvent
	public void onLogin(LoggedInEvent event) {
		StatisticsMod.LOGGER.debug("Called LoggedInEvent");
		TableLogin login = new TableLogin();
		login.setLoginDateTime(Timestamp.valueOf(UtilDate.now()));
		String serverName = new String("without a server");
		if (event.getPlayer().getServer() != null) {
			serverName = event.getPlayer().getServer().getServerHostname();
		}
		login.setServerName(serverName);
		
		DbManager.getInstance().queries().addLogin(login);
	}

	@SubscribeEvent
	public void onLogout(LoggedOutEvent event) {
		TableLogin record = DbManager.getInstance().queries().getLatestLogin();
		if (record != null) {
			record.setLogoutDateTime(Timestamp.valueOf(UtilDate.now()));
			DbManager.getInstance().queries().updateLogin(record);
		}
	}
}
