package de.vincidev.bungeeban.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.vincidev.bungeeban.BungeeBan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileManager {
	public static File ConfigFile;
	public static Configuration ConfigConfiguration;
	public static File LanguageFile;
	public static Configuration LanguageConfiguration;

	public static void loadFiles() {
		if (!BungeeBan.getInstance().getDataFolder().exists()) {
			BungeeBan.getInstance().getDataFolder().mkdir();
		}
		ConfigFile = new File(BungeeBan.getInstance().getDataFolder().getPath(), "config.yml");
		if (!ConfigFile.exists()) {
			try {
				ConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			ConfigConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(ConfigFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		LanguageFile = new File(BungeeBan.getInstance().getDataFolder().getPath(), "lang.yml");
		if (!LanguageFile.exists()) {
			try {
				LanguageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			LanguageConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(LanguageFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void setDefaultConfig() {
		if (ConfigConfiguration.get("MySQL") == null) {
			ConfigConfiguration.set("MySQL.Hostname", "127.0.0.1");
			ConfigConfiguration.set("MySQL.Port", 3306);
			ConfigConfiguration.set("MySQL.Username", "BungeeBanUtils");
			ConfigConfiguration.set("MySQL.Password", "walrus");
			ConfigConfiguration.set("MySQL.Database", "BungeeBanUtils");
			ConfigConfiguration.set("WebPanel.Username", "admin");
			ConfigConfiguration.set("WebPanel.Password", "admin");
			ConfigConfiguration.set("Report.CoolDownActivated", true);
			ConfigConfiguration.set("Report.CoolDown", 30);
			List<String> CommandAliases = new ArrayList<>();
			CommandAliases.add("/hax|ban|20|days|Hacking / Use of unallowed client modifications");
			CommandAliases.add("/spam|mute|45|mins|Spamming");
			ConfigConfiguration.set("CommandAliases", CommandAliases);
			saveConfig(ConfigConfiguration, ConfigFile);
		}
		createLangFile();
	}

	public static void readConfig() {
		/**
		 * MYSQL
		 */
		String hostname = ConfigConfiguration.getString("MySQL.Hostname");
		int port = ConfigConfiguration.getInt("MySQL.Port");
		String username = ConfigConfiguration.getString("MySQL.Username");
		String password = ConfigConfiguration.getString("MySQL.Password");
		String database = ConfigConfiguration.getString("MySQL.Database");
		BungeeBan.setMysql(new MySQL(hostname, port, username, password, database));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createLangFile() {
		if (LanguageConfiguration.get("Prefix") == null) {
			// Prefixes
			LanguageConfiguration.set("Prefix", "&8[&cBungeeBanUtils&8] ");
			// Errors
			LanguageConfiguration.set("Messages.NoPermissions", "&cYou are not allowed to use this.");
			LanguageConfiguration.set("Messages.Cooldown", "&cPlease wait a bit before using this again.");
			LanguageConfiguration.set("Messages.NoPlayer", "&cYou have to be a Player to use this.");
			LanguageConfiguration.set("Messages.PlayerNotFound", "&cThis player could not be found in the database.");
			LanguageConfiguration.set("Messages.NoPermissions", "&cYou are not allowed to use this.");
			LanguageConfiguration.set("Messages.CannotReportThisPlayer",
					"&cYou are not allowed to report this player.");
			LanguageConfiguration.set("Messages.PlayerOffline", "&cThis player is offline.");
			// Syntax
			LanguageConfiguration.set("Messages.Syntax.Ban", "&e/ban <Playername> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.IPBan", "&e/ipban <IP> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.Kick", "&e/kick <Playername> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.Mute", "&e/mute <Playername> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.IPMute", "&e/ipmute <IP> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.Report", "&e/report <Playername> <Reason>");
			LanguageConfiguration.set("Messages.Syntax.Unban", "&e/unban <Playername>");
			LanguageConfiguration.set("Messages.Syntax.Unmute", "&e/unmute <Playername>");
			LanguageConfiguration.set("Messages.Syntax.Unwarn", "&e/unwarn <Playername>");
			LanguageConfiguration.set("Messages.Syntax.Warn", "&e/ban <Playername> <Level>");
			// Ban
			LanguageConfiguration.set("Messages.Ban.Banner", "&aYour ban has been created.");
			List<String> BanBroadcastList = new ArrayList();
			BanBroadcastList.add("&3Banned: &e[Banned]");
			BanBroadcastList.add("&3By: &e[By]");
			BanBroadcastList.add("&3Reason: &e[Reason]");
			BanBroadcastList.add("&3Time: &e[Time]");
			LanguageConfiguration.set("Messages.Ban.Broadcast", BanBroadcastList);
			// Unban
			List<String> UnBanBroadcastList = new ArrayList();
			UnBanBroadcastList.add("&3Unbanned: &e[Unbanned]");
			UnBanBroadcastList.add("&3By: &e[By]");
			LanguageConfiguration.set("Messages.UnBan.Broadcast", UnBanBroadcastList);
			LanguageConfiguration.set("Messages.UnBan.Unbanner", "&aPlayer unbanned.");
			// Bankick
			List<String> BanKickList = new ArrayList();
			BanKickList.add("&cYou are banned from the Server");
			BanKickList.add("&cReason: &e[Reason]");
			BanKickList.add("&cBy: &e[By]");
			BanKickList.add("&cEnd: &e[End]");
			LanguageConfiguration.set("Messages.Ban.Kick", BanKickList);
			// Mute
			LanguageConfiguration.set("Messages.Mute.Muter", "&aYour mute has been created.");
			// Mutemessage
			List<String> MuteBroadcastList = new ArrayList();
			MuteBroadcastList.add("&3Muted: &e[Muted]");
			MuteBroadcastList.add("&3By: &e[By]");
			MuteBroadcastList.add("&3Reason: &e[Reason]");
			MuteBroadcastList.add("&3Time: &e[Time]");
			LanguageConfiguration.set("Messages.Mute.Broadcast", MuteBroadcastList);
			// Unmute
			List<String> UnMuteBroadcastList = new ArrayList();
			UnMuteBroadcastList.add("&3Unmuted: &e[Unmuted]");
			UnMuteBroadcastList.add("&3By: &e[By]");
			LanguageConfiguration.set("Messages.UnMute.Broadcast", UnMuteBroadcastList);
			LanguageConfiguration.set("Messages.UnMute.Unmuter", "&aPlayer unmuted.");
			// MuteMessage
			List<String> MuteMessageList = new ArrayList();
			MuteMessageList.add("&cYou are muted from the chat.");
			MuteMessageList.add("&cReason: &e[Reason]");
			MuteMessageList.add("&cBy: &e[By]");
			MuteMessageList.add("&cEnd: &e[End]");
			LanguageConfiguration.set("Messages.Mute.MuteMessage", MuteMessageList);
			// Report
			LanguageConfiguration.set("Messages.Report.Reporter",
					"&3The player has been reported. There are currently &e[0] &3staffmembers online to see it.");
			List<String> ReportBroadcastList = new ArrayList();
			ReportBroadcastList.add("&cPlayer: &e[Reported]");
			ReportBroadcastList.add("&cServer: &e[Server]");
			ReportBroadcastList.add("&cReason: &e[Reason]");
			ReportBroadcastList.add("&cBy: &e[By]");
			LanguageConfiguration.set("Messages.Report.Broadcast", ReportBroadcastList);
			LanguageConfiguration.set("Messages.Report.CannotReportSelf", "&cYou can not report yourself.");
			List<String> KickBroadcastList = new ArrayList();
			KickBroadcastList.add("&cPlayer &e[Kicked] &c has been kicked.");
			KickBroadcastList.add("&cReason: &e[Reason]");
			KickBroadcastList.add("&cBy: &e[By]");
			// Kick
			LanguageConfiguration.set("Messages.Kick.Broadcast", KickBroadcastList);
			LanguageConfiguration.set("Messages.Kick.Kicker", "&aPlayer kicked.");
			List<String> KickKickList = new ArrayList();
			KickKickList.add("&cYou have been kicked from the Server!");
			KickKickList.add("&cReason: &e[Reason]");
			KickKickList.add("&cBy: &e[By]");
			LanguageConfiguration.set("Messages.Kick.Kick", KickKickList);
			// Warns
			LanguageConfiguration.set("Messages.Warn.Warns", "&7You currently have &e[Warns] &7warns.");
			LanguageConfiguration.set("Messages.Warn.UnWarned", "&7You unwarned the player &e[Player]&7.");
			LanguageConfiguration.set("Messages.Warn.Warned",
					"&7You warned the player &e[Player] &7with the level &a[Level] &7.");
			// Check
			LanguageConfiguration.set("Messages.Check.isBanned",
					"&cBanned for &e[Remaining] &cbecause of &e[Reason]&c.");
			LanguageConfiguration.set("Messages.Check.isNotBanned", "&cNot banned.");
			LanguageConfiguration.set("Messages.Check.isMuted", "&cMuted for &e[Remaining] &cbecause of &e[Reason]&c.");
			LanguageConfiguration.set("Messages.Check.isNotMuted", "&cNot muted.");
			LanguageConfiguration.set("Messages.Check.isWarned", "&cWarned for &e[WarnLevel] times &c.");
			LanguageConfiguration.set("Messages.Check.isNotBanned", "&cNot banned.");
			saveConfig(LanguageConfiguration, LanguageFile);
		}
	}

	public static String getTranslation(String path) {
		return ChatColor.translateAlternateColorCodes('&', LanguageConfiguration.getString(path));
	}

	public static List<String> getBanKickMessage(UUID uuid) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(uuid)) {
			String playername = BungeeBanUtils.getPlayername(uuid);
			String reason = BungeeBanUtils.getBanReason(uuid);
			String by = BungeeBanUtils.getWhoBanned(uuid);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getBanEnd(uuid));
			messages = LanguageConfiguration.getStringList("Messages.Ban.Kick");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[End]", end);
				message = message.replace("[Playername]", playername);
			}
		}
		return messages;
	}

	public static List<String> getBanBroadcastMessage(UUID uuid) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(uuid)) {
			String playername = BungeeBanUtils.getPlayername(uuid);
			String reason = BungeeBanUtils.getBanReason(uuid);
			String by = BungeeBanUtils.getWhoBanned(uuid);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getBanEnd(uuid));
			messages = LanguageConfiguration.getStringList("Messages.Ban.Broadcast");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[Time]", end);
				message = message.replace("[Banned]", playername);
			}
		}
		return messages;
	}

	public static List<String> getBanBroadcastMessage(String ip) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(ip)) {
			String reason = BungeeBanUtils.getBanReason(ip);
			String by = BungeeBanUtils.getWhoBanned(ip);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getBanEnd(ip));
			messages = LanguageConfiguration.getStringList("Messages.Ban.Broadcast");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[Time]", end);
				message = message.replace("[Banned]", ip);
			}
		}
		return messages;
	}

	public static List<String> getBanKickMessage(String ip) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(ip)) {
			String reason = BungeeBanUtils.getBanReason(ip);
			String by = BungeeBanUtils.getWhoBanned(ip);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getBanEnd(ip));
			messages = LanguageConfiguration.getStringList("Messages.Ban.Kick");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[End]", end);
			}
		}
		return messages;
	}

	public static List<String> getMuteKickMessage(UUID uuid) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(uuid)) {
			String playername = BungeeBanUtils.getPlayername(uuid);
			String reason = BungeeBanUtils.getMuteReason(uuid);
			String by = BungeeBanUtils.getWhoMuted(uuid);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getMuteEnd(uuid));
			messages = LanguageConfiguration.getStringList("Messages.Mute.MuteMessage");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[End]", end);
				message = message.replace("[Playername]", playername);
			}
		}
		return messages;
	}

	public static List<String> getMuteKickMessage(String ip) {
		List<String> messages = new ArrayList<>();
		if (BungeeBanUtils.isBanned(ip)) {
			String reason = BungeeBanUtils.getMuteReason(ip);
			String by = BungeeBanUtils.getWhoMuted(ip);
			String end = BungeeBanUtils.getRemainingTime(BungeeBanUtils.getMuteEnd(ip));
			messages = LanguageConfiguration.getStringList("Messages.Mute.MuteMessage");
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				message = message.replace("[Reason]", reason);
				message = message.replace("[By]", by);
				message = message.replace("[End]", end);
			}
		}
		return messages;
	}

	public static void saveConfig(Configuration config, File file) {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}