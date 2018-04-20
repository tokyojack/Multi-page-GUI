package package;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		List<ItemStack> items = new ArrayList<ItemStack>();

		for (int i = 0; i < 100; i++) {
			items.add(new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]));
		}

		// Note, the List above "items" is just testing data. Change it to your item list.
		new Multipage("Pg %page%", items).openInventory(event.getPlayer());
	}

}