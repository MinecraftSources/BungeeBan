package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeBanEvent extends Event {
	private UUID uuid;
	private String bannedBy;
	private String reason;
	private long banSeconds;
	private boolean permanent;

	public BungeeBanEvent(UUID uuid, String bannedBy, String reason, long banSeconds) {
		this.uuid = uuid;
		this.bannedBy = bannedBy;
		this.reason = reason;
		this.banSeconds = banSeconds;
		if (banSeconds == -1L) {
			permanent = true;
		} else {
			permanent = false;
		}
	}

	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

	public String getBannedBy() {
		return bannedBy;
	}

	public long getBanSeconds() {
		return banSeconds;
	}

	public String getReason() {
		return reason;
	}

	public UUID getUniqueID() {
		return uuid;
	}

	public boolean isPermanent() {
		return permanent;
	}
}