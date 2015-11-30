package de.vincidev.bungeeban.util;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class BungeeUtil {

	public static void sendPlayer(ProxiedPlayer p, String servername) {
		if (p != null) {
			ServerInfo server = BungeeCord.getInstance().getServerInfo(servername);
			if (server != null) {
				p.connect(server);
			} else {
				throw new IllegalArgumentException("The server " + servername + " could not be found.");
			}
		}
	}

	public static String getServername(ProxiedPlayer p) {
		if (p != null) {
			return p.getServer().getInfo().getName();
		} else {
			throw new IllegalArgumentException("The player is not online. So the string could not be returned.");
		}
	}

	public static Server getServer(ProxiedPlayer p) {
		if (p != null) {
			return p.getServer();
		} else {
			throw new IllegalArgumentException("The player is not online. So the string could not be returned.");
		}
	}

	public static ServerInfo getServerInfo(ProxiedPlayer p) {
		if (p != null) {
			return p.getServer().getInfo();
		} else {
			throw new IllegalArgumentException("The player is not online. So the string could not be returned.");
		}
	}

	@SuppressWarnings("deprecation")
	public static void kickPlayer(String name, String message) {
		ProxiedPlayer target = BungeeCord.getInstance().getPlayer(name);
		if (target != null) {
			target.disconnect(message);
		}
	}

	@SuppressWarnings("deprecation")
	public static void kickPlayer(UUID uuid, String message) {
		ProxiedPlayer target = BungeeCord.getInstance().getPlayer(uuid);
		if (target != null) {
			target.disconnect(message);
		}
	}

}