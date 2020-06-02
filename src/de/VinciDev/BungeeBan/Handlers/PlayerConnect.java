package de.vincidev.bungeeban.handlers;

import de.vincidev.bungeeban.utils.BungeeBanUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerConnect implements Listener {
	@EventHandler
	public void onConnected(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		BungeeBanUtils.pushPlayer(p);
	}
}