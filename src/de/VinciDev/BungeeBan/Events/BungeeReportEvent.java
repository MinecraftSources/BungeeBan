package de.VinciDev.BungeeBan.Events;

import java.util.UUID;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class BungeeReportEvent extends Event {

	private UUID reported;
	private UUID reportedBy;
	private String reason;
	private String servername;

	public BungeeReportEvent(UUID reported, UUID reportedBy, String reason, String servername) {
		this.reported = reported;
		this.reportedBy = reportedBy;
		this.reason = reason;
		this.servername = servername;
	}

	public ProxiedPlayer getPlayer() {
		return BungeeCord.getInstance().getPlayer(getReported());
	}

	public String getReason() {
		return reason;
	}

	public UUID getReported() {
		return reported;
	}

	public UUID getReportedBy() {
		return reportedBy;
	}

	public String getServername() {
		return servername;
	}

}