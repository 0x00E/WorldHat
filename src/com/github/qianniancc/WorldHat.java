package com.github.qianniancc;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldHat extends JavaPlugin implements Listener {
	public void onEnable() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), "config.yml");
		if (!file.exists()) {
			saveDefaultConfig();
		}
		getLogger().info("启动成功！");
		reloadConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		String world = e.getPlayer().getWorld().getName();
		if (getConfig().getStringList("world").contains(world)) {
			ItemStack oldhat = e.getPlayer().getInventory().getHelmet();

			ItemStack block = new ItemStack(
					Integer.parseInt(getConfig().getConfigurationSection("BlockID").getString(world)));
			ItemMeta blockmeta = block.getItemMeta();
			String TextDisplayName = getConfig().getConfigurationSection("DisplayName").getString(world);
			String DisplayName = ChatColor.translateAlternateColorCodes('&', TextDisplayName);
			blockmeta.setDisplayName(DisplayName);
			block.setItemMeta(blockmeta);
			if ((oldhat != null) && (!oldhat.getItemMeta().hasDisplayName())) {
				e.getPlayer().getInventory().addItem(new ItemStack[] { oldhat });
			}
			e.getPlayer().getInventory().setHelmet(block);
		} else {
			ItemStack oldhat = e.getPlayer().getInventory().getHelmet();
			if ((oldhat != null) && (oldhat.getItemMeta().hasDisplayName())) {
				ItemStack air = new ItemStack(Material.AIR);
				e.getPlayer().getInventory().setHelmet(air);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		String world = e.getPlayer().getWorld().getName();
		if (getConfig().getStringList("world").contains(world)) {
			ItemStack oldhat = e.getPlayer().getInventory().getHelmet();

			ItemStack block = new ItemStack(
					Integer.parseInt(getConfig().getConfigurationSection("BlockID").getString(world)));
			ItemMeta blockmeta = block.getItemMeta();
			String TextDisplayName = getConfig().getConfigurationSection("DisplayName").getString(world);
			String DisplayName = ChatColor.translateAlternateColorCodes('&', TextDisplayName);
			blockmeta.setDisplayName(DisplayName);
			block.setItemMeta(blockmeta);
			if ((oldhat != null) && (!oldhat.getItemMeta().hasDisplayName())) {
				e.getPlayer().getInventory().addItem(new ItemStack[] { oldhat });
			} else if ((oldhat != null) && (oldhat.getItemMeta().hasDisplayName())) {
				ItemStack air = new ItemStack(Material.AIR);
				e.getPlayer().getInventory().setHelmet(air);
			}
			blockmeta.setDisplayName(DisplayName);
			block.setItemMeta(blockmeta);
			e.getPlayer().getInventory().setHelmet(block);
		}
	}

	@EventHandler
	public void onHatClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((!p.isOp()) && (!p.hasPermission("wh.noanti")) && (!p.hasPermission("wh.*"))) {
			String world = p.getPlayer().getWorld().getName();
			if ((getConfig().getStringList("world").contains(world))
					&& (e.getSlotType() == InventoryType.SlotType.ARMOR)) {
				e.setCancelled(true);
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("whreload")) {
			if ((!sender.isOp()) && (!sender.hasPermission("wh.reload")) && (!sender.hasPermission("wh.*"))) {
				String TextNotPerm = getConfig().getString("NotPerm");
				String NotPerm = ChatColor.translateAlternateColorCodes('&', TextNotPerm);
				sender.sendMessage(NotPerm);
				return false;
			}
			reloadConfig();
			String TextReloadOK = getConfig().getString("ReloadOK");
			String ReloadOK = ChatColor.translateAlternateColorCodes('&', TextReloadOK);
			sender.sendMessage(ReloadOK);
			return true;
		}
		return false;
	}
}
