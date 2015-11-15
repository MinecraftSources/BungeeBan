package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeUnwarnEvent extends Event {
	private UUID uuid;
	private String unwarnedBy;

	public BungeeUnwarnEvent(UUID uuid, String unwarnedBy) {
		this.uuid = uuid;
		this.unwarnedBy = unwarnedBy;
	}

	public String getUnwarnedBy() {
		return unwarnedBy;
	}

	public UUID getUniqueID() {
		return uuid;
	}

	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

}