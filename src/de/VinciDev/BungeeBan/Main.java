package de.VinciDev.BungeeBan;

import java.io.IOException;

import com.google.gson.Gson;

import de.VinciDev.BungeeBan.Handlers.PlayerConnect;
import de.VinciDev.BungeeBan.Metrics.Metrics;
import de.VinciDev.BungeeBan.Util.BungeeBan;
import de.VinciDev.BungeeBan.Util.FileManager;
import de.VinciDev.BungeeBan.Util.MySQL;
import de.VinciDev.BungeeBan.Util.UpdateChecker;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {

	static String prefix = "";
	static MySQL mysql;
	static Main main;
	static Gson gson;
	public static final String rsId = "5924";

	@Override
	public void onEnable() {
		main = this;
		gson = new Gson();
		/**
		 * UPDATE CHECKER
		 */
		if (UpdateChecker.updateAvailable(this, rsId)) {
			log("§a§lA new version of §eBungeeBan §ais out! Download it at §ehttps://www.spigotmc.org/resources/bungeeban.5924/§a.");
		} else {
			log("§9BungeeBan is up to date. Enabling now.");
		}
		/**
		 * FILES
		 */
		FileManager.loadFiles();
		FileManager.setDefaultConfig();
		FileManager.readConfig();
		register();
		/**
		 * MYSQL
		 */
		log("§9Establishing mysql connection.");
		if (getMysql() != null) {
			mysql.openConnection();
			if (mysql.isConnected()) {
				log("§9Connection successful. Tables will be created if they do not already exist.");
				BungeeBan.createTables();
			} else {
				log("§cThe mysql connection was not successful. Please recheck your config.yml");
			}
		} else {
			log("§cThe mysql connection was not successful. Please recheck your config.yml");
		}
		/**
		 * METRICS
		 */
		try {
			Metrics metrics = new Metrics(this);
			metrics.enable();
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		mysql.closeConnection();
	}

	public void register() {
		PluginManager pm = BungeeCord.getInstance().getPluginManager();
		pm.registerListener(this, new PlayerConnect());
	}

	@SuppressWarnings("deprecation")
	public static void log(String msg) {
		BungeeCord.getInstance().getConsole().sendMessage(msg);
	}

	public static Main getInstance() {
		return main;
	}

	public static MySQL getMysql() {
		return mysql;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setMysql(MySQL mysql) {
		Main.mysql = mysql;
	}

	public static void setPrefix(String prefix) {
		Main.prefix = prefix;
	}

	public static Gson getGson() {
		return gson;
	}
}