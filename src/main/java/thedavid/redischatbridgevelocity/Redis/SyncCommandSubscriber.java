package thedavid.redischatbridgevelocity.Redis;

import redis.clients.jedis.JedisPubSub;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;


public class SyncCommandSubscriber extends JedisPubSub{
	private final RedisChatBridgeVelocity plugin;
	public SyncCommandSubscriber(RedisChatBridgeVelocity plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onMessage(String channel, String message) {       //收到消息會調用
		RedisChatBridgeVelocity.logger.info("receive redis published message, channel " + channel + ", message " + message);
		RedisChatBridgeVelocity.server.getCommandManager().executeAsync(RedisChatBridgeVelocity.server.getConsoleCommandSource(), message);
	}
	
//	@Override
//	public void onSubscribe(String channel, int subscribedChannels) {    //訂閱頻道會調用
//		Bukkit.getLogger().info("subscribe redis channel success, channel " + channel + ", subscribedChannels " + subscribedChannels);
//	}
//
//	@Override
//	public void onUnsubscribe(String channel, int subscribedChannels) {   //取消訂閱會調用
//		Bukkit.getLogger().info("unsubscribe redis channel, channel " + channel + ", subscribedChannels " + subscribedChannels);
//	}
}
