package de.VinciDev.BungeeBan.Commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CustomCommand extends Command {

	private String reason;
	private long seconds;
	private PunishmentType punishmentType;

	public CustomCommand(String name, long seconds, String reason, PunishmentType punishmentType) {
		super(name);
		this.seconds = seconds;
		this.reason = reason;
		this.punishmentType = punishmentType;
	}

	public PunishmentType getPunishmentType() {
		return punishmentType;
	}

	public String getReason() {
		return reason;
	}

	public long getSeconds() {
		return seconds;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("BungeeBan." + getName())) {
			
		} else {
			
		}
	}

	public enum PunishmentType {
		BAN, MUTE, WARN;
	}

}