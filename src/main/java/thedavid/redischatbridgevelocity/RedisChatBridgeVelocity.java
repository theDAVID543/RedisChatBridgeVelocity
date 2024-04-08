package thedavid.redischatbridgevelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import thedavid.redischatbridgevelocity.Handler.ConfigHandler;
import thedavid.redischatbridgevelocity.Handler.eventListener;
import thedavid.redischatbridgevelocity.Redis.Publisher;
import thedavid.redischatbridgevelocity.Redis.RedisManager;
import thedavid.redischatbridgevelocity.impl.ConnectionInformation;

@Plugin(
		id = "redischatbridgevelocity",
		name = "RedisChatBridgeVelocity",
		version = "1.0-SNAPSHOT"
)
public class RedisChatBridgeVelocity{
	public static Logger logger;
	public static ProxyServer server;
	public RedisManager redisManager;
	public Publisher publisher;
	public ConfigHandler configHandler;
	public ConnectionInformation connectionInformation;
	
	@Inject
	public RedisChatBridgeVelocity(ProxyServer server, Logger logger) {
		RedisChatBridgeVelocity.server = server;
		RedisChatBridgeVelocity.logger = logger;
	}
	
	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event){
		configHandler = new ConfigHandler(this);
		configHandler.createCustomConfig();
		connectionInformation = configHandler.getConnectionInformation();
		if(connectionInformation == null){
			logger.error("Connection information is null, please check your config.yml");
			return;
		}
		redisManager = new RedisManager(this);
		redisManager.initialize();
		publisher = new Publisher(this);
		server.getEventManager().register(this, new eventListener(this));
	}
	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event){
		redisManager.syncCommandSubThread.interrupt();
	}
}
