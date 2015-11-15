package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeMuteEvent extends Event {
	private UUID uuid;
	private String mutedBy;
	private String reason;
	private long muteSeconds;
	private boolean permanent;

	public BungeeMuteEvent(UUID uuid, String mutedBy, String reason, long muteSeconds) {
		this.uuid = uuid;
		this.mutedBy = mutedBy;
		this.reason = reason;
		this.muteSeconds = muteSeconds;
		if (muteSeconds == -1L) {
			permanent = true;
		} else {
			permanent = false;
		}
	}

	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getUniqueID());
	}

	public String getMutedBy() {
		return mutedBy;
	}

	public long getMuteSeconds() {
		return muteSeconds;
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