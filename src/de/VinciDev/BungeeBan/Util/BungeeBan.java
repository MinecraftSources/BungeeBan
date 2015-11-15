package de.VinciDev.BungeeBan.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.VinciDev.BungeeBan.Main;
import de.VinciDev.BungeeBan.Events.BungeeBanEvent;
import de.VinciDev.BungeeBan.Events.BungeeBanIpEvent;
import de.VinciDev.BungeeBan.Events.BungeeReportEvent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @version 2.6;
 * @author Luca Vinciguerra;
 * @category API [Ban API / Banning methods]
 * 
 * @description This class contains all the important banning, muting,
 *              reporting, warning and kicking methods. This class is either
 *              used by the BungeeBan plugin itself or by developers using the
 *              API. If you need help using this class and its implemented
 *              methods, don't hesitate to contact VinciDev in Skype.
 */
public class BungeeBan {

	public static final String tablePrefix = "BungeeBan_";

	/**
	 * Creates the tables in the MySQL database in case they didn't exist yet.
	 */
	public static void createTables() {
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix
				+ "PlayerCache(Playername VARCHAR(16), UUID VARCHAR(100), FirstOnline LONG, LastOnline LONG, LastIP VARCHAR(100))");
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix
				+ "Bans(UUID VARCHAR(100), BanReason VARCHAR(256), BannedOn LONG, BanEnd LONG, BannedBy VARCHAR(100))");
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix
				+ "IPBans(IP VARCHAR(100), BanReason VARCHAR(256), BannedOn LONG, BanEnd LONG, BannedBy VARCHAR(100))");
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix
				+ "Mutes(UUID VARCHAR(100), MuteReason VARCHAR(256), MutedOn LONG, MuteEnd LONG, MutedBy VARCHAR(100))");
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix
				+ "IPMutes(IP VARCHAR(100), MuteReason VARCHAR(256), MutedOn LONG, MuteEnd LONG, MutedBy VARCHAR(100))");
		Main.getMysql().update("CREATE TABLE IF NOT EXISTS " + tablePrefix + "Warns(UUID VARCHAR(100), WarnLevel INT)");
		Main.getMysql()
				.update("CREATE TABLE IF NOT EXISTS " + tablePrefix + "Logs(Date VARCHAR(100), Message VARCHAR(1024))");
	}

	/**
	 * This methods calls the report Event. You need to supply the UUID of the
	 * player who reported, the UUID of the player who got reported, a reason
	 * and the server of the reported player as a String.
	 * 
	 * @return nothing
	 */
	public static void report(UUID reported, UUID reportedBy, String reason, String servername) {
		Main.getInstance().getProxy().getPluginManager()
				.callEvent(new BungeeReportEvent(reported, reportedBy, reason, servername));
	}

	/**
	 * This methods updates or synchronises the new gained playerdata with the
	 * MySQL Database
	 *
	 * @param net.md_5.api.connecton.ProxiedPlayer
	 *            the Player who should be pushed
	 */
	public static void pushPlayer(ProxiedPlayer p) {
		ResultSet rs = Main.getMysql().getResult(
				"SELECT * FROM " + tablePrefix + "PlayerCache WHERE UUID='" + p.getUniqueId().toString() + "'");
		try {
			if (rs.next()) {
				Main.getMysql()
						.update("UPDATE " + tablePrefix + "PlayerCache SET Playername='" + p.getName()
								+ "', LastOnline='" + System.currentTimeMillis() + "', LastIP='"
								+ p.getAddress().getAddress().getHostAddress() + "' WHERE UUID='"
								+ p.getUniqueId().toString() + "'");
			} else {
				Main.getMysql().update("INSERT INTO " + tablePrefix
						+ "PlayerCache(Playername, UUID, FirstOnline, LastOnline, LastIP) VALUES('" + p.getName()
						+ "', '" + p.getUniqueId().toString() + "', '" + System.currentTimeMillis() + "', '"
						+ System.currentTimeMillis() + "', '" + p.getAddress().getAddress().getHostAddress() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Queries the MySQL database to get the UUID of a playername
	 *
	 * @param playername
	 *            is the name of the player who you want to get the uuid of.
	 * @return UUID corresponding to the playername if the player exists, or
	 *         "null" if he doesn't.
	 */
	public static UUID getUniqueID(String playername) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "PlayerCache WHERE Playername='" + playername + "'");
		try {
			if (rs.next()) {
				return UUID.fromString(rs.getString("UUID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Queries the MySQL database to get the playername of a UUID
	 *
	 * @param UUID
	 *            is the UUID of the player who you want to get the uuid of.
	 * @return String corresponding to the UUID if the UUID exists, or "null" if
	 *         he doesn't.
	 */
	public static String getPlayername(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "PlayerCache WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("Playername");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Queries the MySQL database to get the UUID of a playername
	 *
	 * @param playername
	 *            is the name of the player who you want to get the uuid of.
	 * @return UUID as a String corresponding to the playername if the player
	 *         exists, or "null" if he doesn't.
	 */
	public static String getUUID(String playername) {
		UUID uuid = getUniqueID(playername);
		if (uuid != null) {
			return uuid.toString();
		}
		return null;
	}

	/**
	 * Queries the MySQL database to get the first "TimeMilli" when the player
	 * was online
	 *
	 * @param UUID
	 *            of the player who you want to get the first online time of.
	 * @return Long as timemilli corresponding to the playername if the player
	 *         exists, or "0" if he doesn't.
	 */
	public static long firstOnline(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "PlayerCache WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("FirstOnline");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Queries the MySQL database to get the last "TimeMilli" when the player
	 * was online
	 *
	 * @param UUID
	 *            of the player who you want to get the last online time of.
	 * @return Long as timemilli corresponding to the playername if the player
	 *         exists, or "0" if he doesn't.
	 */
	public static long lastOnline(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "PlayerCache WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("LastOnline");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Queries the MySQL database to get the last IP of the player when he was
	 * online
	 *
	 * @param UUID
	 *            of the player who you want to get the IP of.
	 * @return Long as timemilli corresponding to the playername if the player
	 *         exists, or "0" if he doesn't.
	 */
	public static String getLastIp(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "PlayerCache WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("LastIP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks whether the player corresponding to the UUID is banned
	 *
	 * @param UUID
	 *            of the player who you want to check is banned.
	 * @return true if the player is banned, false if the player is not banned.
	 */
	public static boolean isBanned(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Bans WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether the ip which you supplied is banned
	 *
	 * @param IP
	 *            which you want to check is banned
	 * @return true if the player is banned, false if the player is not banned.
	 */
	public static boolean isBanned(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPBans WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether the player corresponding to the UUID is muted
	 *
	 * @param UUID
	 *            of the player who you want to check is muted.
	 * @return true if the player is muted, false if the player is not muted.
	 */
	public static boolean isMuted(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Mutes WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether the ip you supplied is muted
	 *
	 * @param IP
	 *            which you want to check is muted
	 * @return true if the IP is muted, false if the IP is not muted.
	 */
	public static boolean isMuted(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPMutes WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks whether the player corresponding to the UUID is warned
	 *
	 * @param UUID
	 *            of the player who you want to check is warned.
	 * @return true if the player is warned, false if the player is not warned.
	 */
	public static boolean isWarned(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Warns WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets the reason why a banned player is banned.
	 *
	 * @param UUID
	 *            of the player who you want to get the ban reason of
	 * @return The ban reason if the player is banned and "null" if the player
	 *         is not banned
	 */
	public static String getBanReason(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Bans WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("BanReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the reason why a banned IP is banned.
	 *
	 * @param IP
	 *            which you want to get the ban reason of
	 * @return The ban reason if the IP is banned and "null" if the IP is not
	 *         banned
	 */
	public static String getBanReason(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPBans WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getString("BanReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the reason why a banned player is muted.
	 *
	 * @param UUID
	 *            of the player who you want to get the mute reason of
	 * @return The mute reason if the player is muted and "null" if the player
	 *         is not muted
	 */
	public static String getMuteReason(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Mutes WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("MuteReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the reason why a banned IP is muted.
	 *
	 * @param IP
	 *            which you want to get the mute reason of
	 * @return The mute reason if the IP is muted and "null" if the IP is not
	 *         muted
	 */
	public static String getMuteReason(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPMutes WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getString("MuteReason");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the name of the person who banned the player with the corresponding
	 * UUID
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The name of the person who banned the player as a String and null
	 *         if the player is not banned
	 */
	public static String getWhoBanned(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Bans WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("BannedBy");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the name of the person who banned the given IP
	 *
	 * @param IP
	 *            of the player you want to check on
	 * @return The name of the person who banned the IP as a String and null if
	 *         the IP is not banned
	 */
	public static String getWhoBanned(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPBans WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getString("BannedBy");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the name of the person who muted the player with the corresponding
	 * UUID
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The name of the person who muted the player as a String and null
	 *         if the player is not muted
	 */
	public static String getWhoMuted(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Mutes WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getString("MutedBy");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the name of the person who muted the given IP
	 *
	 * @param IP
	 *            of the player you want to check on
	 * @return The name of the person who muted the IP as a String and null if
	 *         the IP is not muted
	 */
	public static String getWhoMuted(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPMutes WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getString("MutedBy");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the time when the player has been banned
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The time when the player has been banned and 0 if he isnt banned
	 */
	public static long getBannedOn(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Bans WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("BannedOn");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the IP has been banned
	 *
	 * @param IP
	 *            of the player you want to check on
	 * @return The time when the IP has been banned and 0 if it isnt banned
	 */
	public static long getBannedOn(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPBans WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getLong("BannedOn");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the player has been muted
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The time when the player has been muted and 0 if he isnt muted
	 */
	public static long getMutedOn(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Mutes WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("MutedOn");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the IP has been muted
	 *
	 * @param IP
	 *            of the player you want to check on
	 * @return The time when the IP has been muted and 0 if it isnt muted
	 */
	public static long getMutedOn(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPMutes WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getLong("MutedOn");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the ban time of the player will run out
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The time when the player will be ubanned, 0 if he isnt banned and
	 *         -1 if he is permanently banned
	 */
	public static long getBanEnd(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Bans WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("BanEnd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the mute time of the player will run out
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The time when the UUID will be unmuted, 0 if he isnt muted and -1
	 *         if he is permanently muted
	 */
	public static long getMuteEnd(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPMutes WHERE UUID='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getLong("MuteEnd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the mute time of the player will run out
	 *
	 * @param UUID
	 *            of the player you want to check on
	 * @return The time when the UUID will be unmuted, 0 if he isnt muted and -1
	 *         if he is permanently muted
	 */
	public static long getMuteEnd(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Mutes WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getLong("MuteEnd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Gets the time when the ban time of the player will run out
	 *
	 * @param IP
	 *            of the player you want to check on
	 * @return The time when the IP will be ubanned, 0 if he isnt banned and -1
	 *         if he is permanently banned
	 */
	public static long getBanEnd(String ip) {
		ResultSet rs = Main.getMysql().getResult("SELECT * FROM " + tablePrefix + "IPBans WHERE IP='" + ip + "'");
		try {
			if (rs.next()) {
				return rs.getLong("BanEnd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * Logs the supplied String to the MySQL Database
	 * 
	 * @param The
	 *            message which you want to be logged
	 */
	public static void log(String message) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		if (Main.getMysql() != null) {
			Main.getMysql().update("INSERT INTO " + tablePrefix + "Logs(Date, Message) VALUES('"
					+ dateFormat.format(date) + "', '" + message + "')");
		}
	}

	/**
	 * Get the current amount of warns (Or warnlevel) of a player
	 * 
	 * @param The
	 *            uuid of the player you want to get the warnlevel of
	 * @return The warnlevel as an Integer if the player has ever been warned
	 *         and -1 if he hasn't.
	 */
	public static int getWarnLevel(UUID uuid) {
		ResultSet rs = Main.getMysql()
				.getResult("SELECT * FROM " + tablePrefix + "Warns WHERE UUID='" + uuid.toString() + "'");
		try {
			if (rs.next()) {
				return rs.getInt("WarnLevel");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Sets the warnlevel of the player with the given uuid to the level you
	 * supplied
	 * 
	 * @param UUID
	 *            of the player and the warnlevel as int
	 */
	public static void setWarnLevel(UUID uuid, int warnlevel) {
		if (getWarnLevel(uuid) != -1) {
			Main.getMysql().update("UPDATE " + tablePrefix + "Warns SET WarnLevel='" + warnlevel + "' WHERE UUID='"
					+ uuid.toString() + "'");
		} else {
			Main.getMysql().update("INSERT INTO " + tablePrefix + "Warns(UUID, WarnLevel) VALUES('" + uuid.toString()
					+ "', '" + warnlevel + "')");
		}
	}

	/**
	 * Converts the end of the punishment to a string with information about the
	 * time which remains
	 * 
	 * @param long
	 *            which is the end of the punishment
	 * @return Permanent if the punishment is permanent and otherwise it returns
	 *         a string in the format [days] day(s), [hours] hour(s), [minutes]
	 *         minute(s) and [seconds] second(s)
	 */
	public static String getRemainingTime(long end) {
		if (end == -1L) {
			return "Permanent";
		} else {

			long current = System.currentTimeMillis();
			long remaining = end - current;

			int seconds = 0;
			int minutes = 0;
			int hours = 0;
			int days = 0;

			while (remaining >= 1000) {
				seconds = seconds + 1;
				remaining = remaining - 1000;
			}
			while (seconds >= 60) {
				minutes = minutes + 1;
				seconds = seconds - 60;
			}
			while (minutes >= 60) {
				hours = hours + 1;
				minutes = minutes - 60;
			}
			while (hours >= 24) {
				days = days + 1;
				hours = hours - 24;
			}
			return days + " day(s), " + hours + " hour(s), " + minutes + " minute(s) and " + seconds + " second(s)";
		}
	}

	/**
	 * Bans a player with a given uuid, time in seconds reason and someone who
	 * banned
	 * 
	 * @param UUID
	 *            of the player, who banned the player as a string, The amount
	 *            of time in seconds as a long and a reason as the string.
	 */
	@SuppressWarnings("deprecation")
	public static void banPlayer(UUID uuid, String bannedBy, String banReason, long seconds) {
		if (!isBanned(uuid)) {
			// Calculating ban end
			long end = seconds;
			if (end != -1L) {
				end = System.currentTimeMillis() + (seconds * 1000);
			}
			// MySQL Insertion
			Main.getMysql()
					.update("INSERT INTO " + tablePrefix + "Bans(UUID, BanReason, BannedOn, BanEnd, BannedBy) VALUES('"
							+ uuid.toString() + "', '" + banReason + "', '" + System.currentTimeMillis() + "', '" + end
							+ "', '" + bannedBy + "')");
			// Kicking player if online
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(uuid);
			if (target != null) {
				String msg = "";
				for (String data : FileManager.getBanKickMessage(uuid)) {
					msg = msg + "\n" + data;
				}
				target.disconnect(msg);
			}
			// Calling the event
			BungeeCord.getInstance().getPluginManager()
					.callEvent(new BungeeBanEvent(uuid, bannedBy, banReason, seconds));
		}
	}

	/**
	 * Bans a player with a given uuid, time in seconds reason and someone who
	 * banned
	 * 
	 * @param IP
	 *            of the player, who banned the player as a string, The amount
	 *            of time in seconds as a long and a reason as the string.
	 */
	@SuppressWarnings("deprecation")
	public static void banPlayer(String ip, String bannedBy, String banReason, long seconds) {
		if (!isBanned(ip)) {
			// Calculating ban end
			long end = seconds;
			if (end != -1L) {
				end = System.currentTimeMillis() + (seconds * 1000);
			}
			// MySQL Insertion
			Main.getMysql()
					.update("INSERT INTO " + tablePrefix + "IPBans(IP, BanReason, BannedOn, BanEnd, BannedBy) VALUES('"
							+ ip.toString() + "', '" + banReason + "', '" + System.currentTimeMillis() + "', '" + end
							+ "', '" + bannedBy + "')");
			// Kicking player if online
			ProxiedPlayer target = null;
			for (ProxiedPlayer onl : BungeeCord.getInstance().getPlayers()) {
				if (onl.getAddress().getAddress().toString().equalsIgnoreCase(ip)) {
					target = onl;
				}
			}
			if (target != null) {
				String msg = "";
				for (String data : FileManager.getBanKickMessage(ip)) {
					msg = msg + "\n" + data;
				}
				target.disconnect(msg);
			}
			// Call the event
			BungeeCord.getInstance().getPluginManager()
					.callEvent(new BungeeBanIpEvent(ip, bannedBy, banReason, seconds));
		}
	}
}