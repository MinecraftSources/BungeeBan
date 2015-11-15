package de.VinciDev.BungeeBan.Events;

import net.md_5.bungee.api.plugin.Event;

public class BungeeBanIpEvent extends Event {
	private String ip;
	private String bannedBy;
	private String reason;
	private long banSeconds;
	private boolean permanent;

	public BungeeBanIpEvent(String ip, String bannedBy, String reason, long banSeconds) {
		this.ip = ip;
		this.bannedBy = bannedBy;
		this.reason = reason;
		this.banSeconds = banSeconds;
		if (banSeconds == -1L) {
			permanent = true;
		} else {
			permanent = false;
		}
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

	public String getIP() {
		return ip;
	}

	public boolean isPermanent() {
		return permanent;
	}
}