package de.vincidev.bungeeban.events;

import net.md_5.bungee.api.plugin.Event;

public class BungeeMuteIpEvent extends Event {
	private String ip;
	private String mutedBy;
	private String reason;
	private long muteSeconds;
	private boolean permanent;

	public BungeeMuteIpEvent(String ip, String mutedBy, String reason, long muteSeconds) {
		this.ip = ip;
		this.mutedBy = mutedBy;
		this.reason = reason;
		this.muteSeconds = muteSeconds;
		if (muteSeconds == -1L) {
			permanent = true;
		} else {
			permanent = false;
		}
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

	public String getIP() {
		return ip;
	}

	public boolean isPermanent() {
		return permanent;
	}
}