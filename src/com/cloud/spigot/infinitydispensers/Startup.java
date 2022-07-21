package com.cloud.spigot.InfinityDispensers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Startup
{
  private Startup() {}
  
  static Startup instance = new Startup();
  
  public static Startup getInstance() {
    return instance;
  }
  

  Plugin p;
  
  FileConfiguration config;
  File cfile;
  FileConfiguration data;
  File dfile;
  public void setup(Plugin p)
  {
    cfile = new File(p.getDataFolder(), "config.yml");
    config = p.getConfig();
    


    if (!p.getDataFolder().exists()) {
      p.getDataFolder().mkdir();
    }
    
    dfile = new File(p.getDataFolder(), "data.yml");
    
    if (!dfile.exists()) {
      try {
        dfile.createNewFile();
      }
      catch (IOException e) {
        Bukkit.getServer().getLogger().log(Level.SEVERE, "{0}Could not create data.yml!", ChatColor.RED);
      }
    }
    
    data = YamlConfiguration.loadConfiguration(dfile);
  }
  
  public FileConfiguration getData() {
    return data;
  }
  
  public void saveData() {
    try {
      data.save(dfile);
    }
    catch (IOException e) {
      Bukkit.getServer().getLogger().log(Level.SEVERE, "{0}Could not save data.yml!", ChatColor.RED);
    }
  }
  
  public void reloadData() {
    data = YamlConfiguration.loadConfiguration(dfile);
  }
  
  public FileConfiguration getConfig() {
    return config;
  }
  
  public void saveConfig() {
    try {
      config.save(cfile);
    }
    catch (IOException e) {
      Bukkit.getServer().getLogger().log(Level.SEVERE, "{0}Could not save config.yml!", ChatColor.RED);
    }
  }
  
  public void reloadConfig() {
    config = YamlConfiguration.loadConfiguration(cfile);
  }
  
  public org.bukkit.plugin.PluginDescriptionFile getDesc() {
    return p.getDescription();
  }
}