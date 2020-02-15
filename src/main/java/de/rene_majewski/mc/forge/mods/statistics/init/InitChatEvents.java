package de.rene_majewski.mc.forge.mods.statistics.init;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rene_majewski.mc.forge.mods.statistics.db.DbManager;
import de.rene_majewski.mc.forge.mods.statistics.db.tables.TableChat;
import de.rene_majewski.mc.forge.mods.statistics.util.UtilDate;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Reagiert auf Chat-Events.
 * 
 * @author René Majewski
 * @since 0.1.0
 */
public class InitChatEvents {
	private Logger LOGGER = LogManager.getLogger();
	/**
	 * Wird aufgerufen, wenn der Client eine Chat-Nachricht oder einen Befehl
	 * zum Server sendet.
	 * 
	 * @param event Daten des Events.
	 */
	@SubscribeEvent
	public void onChatSend(ClientChatEvent event) {
		TableChat record = new TableChat();
		record.setMessage(event.getOriginalMessage());
		record.setTimestamp(Timestamp.valueOf(UtilDate.now()));
		
		DbManager.getInstance().queries().addChatMessage(record);
	}
	
	/**
	 * Wird aufgerufen, wenn ein Chat-Nachricht empfangen wurde.
	 * 
	 * @param event Daten des Events.
	 */
	@SubscribeEvent
	public void onChatReceive(ClientChatReceivedEvent event) {
		String message = event.getMessage().getUnformattedComponentText();
		
		if (message.indexOf(Minecraft.getInstance().player.getName().getUnformattedComponentText()) == -1) {
			TableChat record = new TableChat();
			record.setMessage(message);
			record.setTimestamp(Timestamp.valueOf(UtilDate.now()));
			
			DbManager.getInstance().queries().addChatMessage(record);
		}
	}
}
