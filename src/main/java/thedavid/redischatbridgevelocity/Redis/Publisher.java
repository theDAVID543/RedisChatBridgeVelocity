package thedavid.redischatbridgevelocity.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;

public class Publisher{
	public Publisher(RedisChatBridgeVelocity plugin){
		this.plugin = plugin;
		jedisPool = plugin.redisManager.jedisPool;
		loginBridgeChannel = plugin.redisManager.loginBridgeChannel;
		disconnectBridgeChannel = plugin.redisManager.disconnectBridgeChannel;
	}
	public RedisChatBridgeVelocity plugin;
	private final JedisPool jedisPool;
	private final String loginBridgeChannel;
	private final String disconnectBridgeChannel;
	public void loginPublish(String message){
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.publish(loginBridgeChannel, message);
			RedisChatBridgeVelocity.logger.info("publish message: " + message + ",to channel: " + loginBridgeChannel);
		}
	}
	public void disconnectPublish(String message){
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.publish(disconnectBridgeChannel, message);
			RedisChatBridgeVelocity.logger.info("publish message: " + message + ",to channel: " + loginBridgeChannel);
		}
	}
}
