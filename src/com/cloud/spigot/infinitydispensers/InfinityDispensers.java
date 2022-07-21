package com.cloud.spigot.InfinityDispensers;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InfinityDispensers extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
{

  public InfinityDispensers(){
}
    Startup settings = Startup.getInstance();
  ArrayList<Player> er = new ArrayList();
  
  @Override
  public void onEnable() {
    org.bukkit.Bukkit.getServer().getPluginManager().registerEvents(this, this);
    settings.setup(this);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("adddispenser")) {
      if ((sender instanceof org.bukkit.command.ConsoleCommandSender)) {
        sender.sendMessage(ChatColor.RED + "I am truly sorry, but I think, that you are unable to click a dispenser :(");
        return true;
      }
      Player p = (Player)sender;
      if (p.hasPermission("inf.create")) {
        er.add(p);
        p.sendMessage(ChatColor.GREEN + "Please left/right click on a dispenser, to make it unlimited!");
      }
    }
    return true;
  }
  
  @EventHandler
  public void onBlockDamage(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    Block b = e.getClickedBlock();
    if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && 
      (er.contains(p)) && 
      (e.getClickedBlock().getType() == Material.DISPENSER)) {
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".world", b.getLocation().getWorld().getName());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".x", b.getLocation().getX());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".y", b.getLocation().getY());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".z", b.getLocation().getZ());
      settings.saveData();
      p.sendMessage(ChatColor.GREEN + "Successfull created infinity dispenser!");
      er.remove(p);
      e.setCancelled(true);
      return;
    }
    

    if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && 
      (er.contains(p))) {
      if (p.getGameMode().equals(GameMode.CREATIVE)) return;
      if (e.getClickedBlock().getType() == Material.DISPENSER) {
        settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".world", b.getLocation().getWorld().getName());
        settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".x", b.getLocation().getX());
        settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".y", b.getLocation().getY());
        settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".z", b.getLocation().getZ());
        settings.saveData();
        p.sendMessage(ChatColor.GREEN + "Successfull created infinity dispenser!");
        er.remove(p);
      }
    }
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent e)
  {
    Block b = e.getBlock();
    Player p = e.getPlayer();
    if ((p.getPlayer().getGameMode().equals(GameMode.CREATIVE)) && 
      (er.contains(p)) && 
      (b.getType() == Material.DISPENSER)) {
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".world", b.getLocation().getWorld().getName());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".x", b.getLocation().getX());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".y", b.getLocation().getY());
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ() + ".z", b.getLocation().getZ());
      settings.saveData();
      p.sendMessage(ChatColor.GREEN + "Successfully created infinity dispenser!");
      er.remove(p);
      e.setCancelled(true);
      return;
    }
    

    if (settings.getData().getConfigurationSection("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ()) != null && p.hasPermission("inf.remove")){
      p.sendMessage(ChatColor.RED + "You have removed the infinity dispenser!");
      settings.getData().set("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ(), null);
      settings.saveData();
    }
  }
  
  @EventHandler
  public void ondisp(BlockDispenseEvent e) {
    Block b = e.getBlock();
    InventoryHolder d = (InventoryHolder)e.getBlock().getState();
    if (settings.getData().getConfigurationSection("dispenser." + b.getLocation().getX() + b.getLocation().getY() + b.getLocation().getZ()) != null) {
      ItemStack i = e.getItem();
      if (i.getType() == Material.LAVA_BUCKET) {
        return;
      }
      if (i.getType() == Material.WATER_BUCKET) {
        return;
      }
      if (i.getType() == Material.BUCKET) {
        Block a = b;
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX() - 1, a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX() + 1, a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY() - 1, a.getLocation().getBlockZ()).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY() + 1, a.getLocation().getBlockZ()).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ() - 1).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ() + 1).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.WATER) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX() - 1, a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX() + 1, a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY() - 1, a.getLocation().getBlockZ()).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY() + 1, a.getLocation().getBlockZ()).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ() - 1).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ() + 1).getType() == Material.LAVA) {
          return;
        }
        if (a.getWorld().getBlockAt(a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()).getType() == Material.LAVA) {
          return;
        }
      }
      d.getInventory().addItem(new ItemStack[] { i });
    }
  }
}