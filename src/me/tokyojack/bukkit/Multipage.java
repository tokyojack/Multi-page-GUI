package com.valeon.core.multipage;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Multipage implements Listener {

	private String inventoryName;

	private ItemStack nextButton;
	private ItemStack backButton;
	private ItemStack noValuesItem;
	private List<ItemStack> inventoryItems;

	public Multipage(String inventoryName, ItemStack nextItem, ItemStack backitem, ItemStack noValuesItem,
			List<ItemStack> inventoryItems) {
		this.inventoryName = inventoryName;
		this.nextButton = nextItem;
		this.backButton = backitem;
		this.noValuesItem = noValuesItem;
		this.inventoryItems = inventoryItems;

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
	}

	public Multipage(String inventoryName, List<ItemStack> inventoryItems) {
		this.inventoryName = inventoryName;
		this.nextButton = MultipageManager.DEFAULT_NEXT_BUTTON;
		this.backButton = MultipageManager.DEFAULT_BACK_BUTTON;
		this.noValuesItem = MultipageManager.DEFAULT_NO_ITEMS;
		this.inventoryItems = inventoryItems;

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
	}

	public Multipage(String inventoryName, List<ItemStack> inventoryItems, Player openInvToPlayer) {
		this(inventoryName, inventoryItems);

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
		openInventory(openInvToPlayer);
	}

	public Multipage(String inventoryName, ItemStack nextItem, ItemStack backItem, ItemStack noValuesItem,
			List<ItemStack> inventoryItems, Player openInvToPlayer) {
		this(inventoryName, nextItem, backItem, noValuesItem, inventoryItems);

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
		openInventory(openInvToPlayer);
	}

	public Multipage(String inventoryName, List<ItemStack> inventoryItems, Player openInvToPlayer, int pageToStartAt) {
		this(inventoryName, inventoryItems);

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
		openInventory(openInvToPlayer, pageToStartAt);
	}

	public Multipage(String inventoryName, ItemStack nextItem, ItemStack backitem, ItemStack noValuesItem,
			List<ItemStack> inventoryItems, Player openInvToPlayer, int pageToStartAt) {
		this(inventoryName, nextItem, backitem, noValuesItem, inventoryItems);

		MultipageManager.getInstance().getMultipages().put(this.inventoryName, this);
		openInventory(openInvToPlayer, pageToStartAt);
	}

	/**
	 * Opens a multi-page GUI. NOT USED FOR NORMAL USE
	 *
	 * @param Player
	 *            - The player it'll be opened to
	 * @param int
	 *            - The page it'll open at
	 * @param boolean
	 *            - If the person is opening from another page. You shouldn't use this.
	 */
	public void openInventory(Player player, int pageNumber, boolean isReopening) {
		player.openInventory(createInventory(player, pageNumber, isReopening));
	}

	/**
	 * Opens a multi-page GUI
	 *
	 * @param Player
	 *            - The player it'll be opened to
	 * @param int
	 *            - The page it'll open at
	 */
	public void openInventory(Player player, int pageNumber) {
		openInventory(player, pageNumber, false);
	}

	/**
	 * Opens a multi-page GUI
	 *
	 * @param Player
	 *            - The player it'll be opened to
	 */
	public void openInventory(Player player) {
		openInventory(player, 1, false);
	}

	/**
	 * Creates the mutlipage inventory.
	 *
	 * @param Player
	 *            - The player it'll be opened to
	 * @param int
	 *            - The page it'll open at
	 * @param boolean
	 *            - If the person is opening from another page. You shouldn't use this.
	 */
	private Inventory createInventory(Player player, int pageNumber, boolean isReopening) {
		if (!isReopening)
			MultipageManager.getInstance().getPlayerPages().remove(player.getName());

		pageNumber--;

		Inventory multipageInventory = Bukkit.createInventory(null, 54,
				this.inventoryName.replace("%page%", String.valueOf((pageNumber + 1))));

		int slotSlot = 0;

		int minItemPositionNumber = (35 * pageNumber) + (pageNumber <= 0 ? 0 : pageNumber);
		int maxItemPositionNumber = ((35 * pageNumber) + ((pageNumber <= 0 ? 0 : pageNumber) + 1)) + 35;

		for (int i = minItemPositionNumber; i < maxItemPositionNumber; i++) {
			if (i >= this.inventoryItems.size())
				break;

			if (i >= (maxItemPositionNumber - 1) && ((this.inventoryItems.size() - maxItemPositionNumber) >= 1))
				multipageInventory.setItem(53, this.nextButton);

			multipageInventory.setItem(slotSlot, this.inventoryItems.get(i));
			slotSlot++;
		}

		if (pageNumber >= 1 && slotSlot >= 1)
			multipageInventory.setItem(45, this.backButton);

		if (slotSlot == 0 && this.noValuesItem != null)
			multipageInventory.addItem(this.noValuesItem);

		return multipageInventory;
	}

	/**
	 * Gets the inventory name
	 *
	 * @return String - The inventory name
	 */
	public String getName() {
		return this.inventoryName;
	}

	/**
	 * Gets the inventory next button item
	 *
	 * @return ItemStack - The next button item
	 */
	public ItemStack getNextButtonItem() {
		return this.nextButton;
	}

	/**
	 * Gets the inventory back button item
	 *
	 * @return ItemStack - The back button item
	 */
	public ItemStack getBackButtonItem() {
		return this.backButton;
	}

	/**
	 * Gets the inventory "no values" item
	 *
	 * @return ItemStack - The "no values" item
	 */
	public ItemStack getNoValuesItem() {
		return this.noValuesItem;
	}

	/**
	 * Gets all the items in the inventory
	 *
	 * @return List<ItemStack> - The items in the inventory
	 */
	public List<ItemStack> getAllItems() {
		return this.inventoryItems;
	}
	
	// Could put in MultipageManager, but it's more clear if you have
	// "Multipage.register()"
	// and not
	// "MultipageManager.register()";

	public static void register(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(new MutltipageClick(), plugin);
		new MultipageManager();
	}

}