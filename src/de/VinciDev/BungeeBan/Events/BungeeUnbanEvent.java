package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeUnbanEvent extends Event {
	private UUID uuid;
	private String unbannedBy;

	public BungeeUnbanEvent(UUID uuid, String unbannedBy) {
		this.uuid = uuid;
		this.unbannedBy = unbannedBy;
	}
	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

	public String getUnbannedBy() {
		return unbannedBy;
	}

	public UUID getUniqueID() {
		return uuid;
	}
}