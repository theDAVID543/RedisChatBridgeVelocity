package thedavid.redischatbridgevelocity.Handler;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;

public class eventListener{
	public eventListener(RedisChatBridgeVelocity plugin){
		this.plugin = plugin;
	}
	public RedisChatBridgeVelocity plugin;
	@Subscribe
	public void onPlayerLogin(LoginEvent e) {
		plugin.publisher.loginPublish(e.getPlayer().getUsername());
	}
	@Subscribe
	public void onPlayerDisconnect(DisconnectEvent e) {
		plugin.publisher.disconnectPublish(e.getPlayer().getUsername());
	}
}
