package de.jeff_media.AngelChestPlus.listeners;

import de.jeff_media.AngelChestPlus.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

/**
 * Handles hologram / armor stand related events related to AngelChest
 */
public class HologramListener implements Listener {
	
	final Main main;
	
	public HologramListener() {
		this.main = Main.getInstance();
	}

	/**
	 * Prevents AngelChest holograms from being manipulated
	 * @param event PlayerArmorStandManipulateEvent
	 */
	@EventHandler
	public void manipulate(PlayerArmorStandManipulateEvent event)
	{
		if(event.getRightClicked().isVisible()) return;
		if(!main.isAngelChestHologram(event.getRightClicked())) return;
	    event.setCancelled(true);
	}

}