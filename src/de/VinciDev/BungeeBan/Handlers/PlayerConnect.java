package de.VinciDev.BungeeBan.Handlers;

import de.VinciDev.BungeeBan.Util.BungeeBan;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerConnect implements Listener {
	@EventHandler
	public void onConnected(ServerConnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		BungeeBan.pushPlayer(p);
	}
}