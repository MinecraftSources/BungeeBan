package de.vincidev.bungeeban;

import java.io.IOException;

import com.google.gson.Gson;

import de.vincidev.bungeeban.handlers.BroadcastHandlers;
import de.vincidev.bungeeban.handlers.PlayerConnect;
import de.vincidev.bungeeban.metrics.Metrics;
import de.vincidev.bungeeban.utils.BungeeBanUtils;
import de.vincidev.bungeeban.utils.FileManager;
import de.vincidev.bungeeban.utils.MySQL;
import de.vincidev.bungeeban.utils.UpdateChecker;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeBan extends Plugin {

	static String prefix = "";
	static MySQL mysql;
	static BungeeBan main;
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
				BungeeBanUtils.createTables();
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
		log("§9Disableing BungeeBan.");
	}

	public void register() {
		PluginManager pm = BungeeCord.getInstance().getPluginManager();
		pm.registerListener(this, new PlayerConnect());
		pm.registerListener(this, new BroadcastHandlers());
	}

	@SuppressWarnings("deprecation")
	public static void log(String msg) {
		BungeeCord.getInstance().getConsole().sendMessage(msg);
	}

	public static BungeeBan getInstance() {
		return main;
	}

	public static MySQL getMysql() {
		return mysql;
	}

	public static String getPrefix() {
		return prefix;
	}

	public static void setMysql(MySQL mysql) {
		BungeeBan.mysql = mysql;
	}

	public static void setPrefix(String prefix) {
		BungeeBan.prefix = prefix;
	}

	public static Gson getGson() {
		return gson;
	}
}