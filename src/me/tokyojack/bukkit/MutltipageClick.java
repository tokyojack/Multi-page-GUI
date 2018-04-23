package com.valeon.core.multipage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MutltipageClick implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		String fixedInventoryName = clearNumbers(event.getInventory().getName());
		MultipageManager multipageManager = MultipageManager.getInstance();

		// Check's the inventory that was clicked in is an multipage inventory
		if (!multipageManager.getMultipages().containsKey(fixedInventoryName))
			return;

		// Check's if the clicker is a player
		if (!(event.getWhoClicked() instanceof Player))
			return;

		Player player = (Player) event.getWhoClicked();
		ItemStack currentItem = event.getCurrentItem();

		// Check's if the item that was clicked wasn't air/nothing
		if (currentItem == null || currentItem.getType() == Material.AIR)
			return;

		// Get's the multipage that was clicked
		Multipage multipage = multipageManager.getMultipages().get(fixedInventoryName);

		String currentItemName = currentItem.getItemMeta().getDisplayName();

		String nextButtonName = multipage.getNextButtonItem().getItemMeta().getDisplayName();
		String backButtonName = multipage.getBackButtonItem().getItemMeta().getDisplayName();

		// Get's what page players are on in the inventory
		Map<String, Integer> playerPages = multipageManager.getPlayerPages();

		// If the clicked item was the next button
		if (currentItemName.equals(nextButtonName)) {
			
			// If the map already contains a page number, add 1 to it, else if the player isn't in it (page 1), make the page number 2
			playerPages.put(player.getName(),
					(playerPages.containsKey(player.getName()) ? (playerPages.get(player.getName()) + 1) : 2));

			event.setCancelled(true);
		}

		// If the clicked item was the back buton
		if (currentItemName.equals(backButtonName)) {
			int playerPage = playerPages.get(player.getName());

			if (playerPage == 1) {
				// If the page is 1, remove the player
				playerPages.remove(player.getName());
			} else {
				// Else minus one from the players page count
				playerPages.put(player.getName(), (playerPage - 1));
			}

			event.setCancelled(true);
		}

		// Reopens the inventory for the player
		multipage.openInventory(player,
				playerPages.containsKey(player.getName()) ? playerPages.get(player.getName()) : 1, true);
	}

	private String clearNumbers(String string) {
		String fixed = string.replaceAll("\\d", "%page%");
		Pattern p = Pattern.compile("%page%");
		Matcher m = p.matcher(fixed);

		StringBuilder builder = new StringBuilder();

		while (m.find())
			builder.append("%page%");

		return fixed.replace(builder.toString(), "%page%");
	}

}
