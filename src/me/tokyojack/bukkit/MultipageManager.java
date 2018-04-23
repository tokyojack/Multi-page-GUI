package com.valeon.core.multipage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MultipageManager {

	public final static ItemStack DEFAULT_NEXT_BUTTON = createNamedItem(Material.STAINED_GLASS_PANE, 13, "&aNext page");

	public final static ItemStack DEFAULT_BACK_BUTTON = createNamedItem(Material.STAINED_GLASS_PANE, 14, "&cBack page");

	public final static ItemStack DEFAULT_NO_ITEMS = createNamedItem(Material.GLASS_BOTTLE, "&fNo items are here :(");

	private static MultipageManager instance;

	private Map<String, Multipage> multipages;
	private Map<String, Integer> playerPages;

	public MultipageManager() {
		instance = this;
		this.multipages = new HashMap<String, Multipage>();
		this.playerPages = new HashMap<String, Integer>();
	}

	/**
	 * Gets all the created multipage inventories
	 *
	 * @return Map<String, Multipage> - The created multipage inventories
	 */
	public Map<String, Multipage> getMultipages() {
		return this.multipages;
	}

	/**
	 * Gets what page the player is on
	 *
	 * @return Map<String, Integer> - The map with what page a player is on
	 */
	public Map<String, Integer> getPlayerPages() {
		return this.playerPages;
	}

	// Not sure if this is the best way todo this. (I'm wanting to keep the same
	// values, without creating a new object each time with new MultipageMain();
	public static MultipageManager getInstance() {
		return instance;
	}

	/**
	 * Returns an ItemStack with the given arguments
	 *
	 * @param Material
	 *            - The item's material
	 * @param String
	 *            - The item's name
	 *
	 * @return ItemStack - The created item
	 */
	private static ItemStack createNamedItem(Material material, String name) {
		return createNamedItem(material, name, 0);
	}

	/**
	 * Returns an ItemStack with the given arguments
	 *
	 * @param Material
	 *            - The item's material
	 * @param String
	 *            - The item's name
	 * @param int
	 *            - The item's durability
	 *
	 * @return ItemStack - The created item
	 */
	private static ItemStack createNamedItem(Material material, String name, int durability) {
		ItemStack item = durability != 0 ? new ItemStack(material, 1, (short) durability) : new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(itemMeta);
		return item;
	}

}
