package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeUnmuteEvent extends Event {
	private UUID uuid;
	private String unmutedBy;

	public BungeeUnmuteEvent(UUID uuid, String unmutedBy) {
		this.uuid = uuid;
		this.unmutedBy = unmutedBy;
	}

	public String getUnmutedBy() {
		return unmutedBy;
	}
	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

	public UUID getUniqueID() {
		return uuid;
	}
}