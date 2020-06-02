package de.vincidev.bungeeban.events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeWarnEvent extends Event {
	private UUID uuid;
	private String warnedBy;
	private String reason;
	private int warnLevel;

	public BungeeWarnEvent(UUID uuid, String warnedBy, String reason, int warnlevel) {
		this.uuid = uuid;
		this.warnedBy = warnedBy;
		this.reason = reason;
		this.warnLevel = warnlevel;
	}

	public String getReason() {
		return reason;
	}

	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

	public UUID getUniqueID() {
		return uuid;
	}

	public String getWarnedBy() {
		return warnedBy;
	}

	public int getWarnLevel() {
		return warnLevel;
	}
}