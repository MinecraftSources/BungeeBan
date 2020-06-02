package de.vincidev.bungeeban.events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeKickEvent extends Event {

	private UUID kicked;
	private String kickedBy;
	private String reason;

	public BungeeKickEvent(UUID kicked, String kickedBy, String reason) {
		this.kicked = kicked;
		this.kickedBy = kickedBy;
		this.reason = reason;
	}
	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(kicked);
	}

	public String getReason() {
		return reason;
	}

	public UUID getKicked() {
		return kicked;
	}

	public String getKickedBy() {
		return kickedBy;
	}

}