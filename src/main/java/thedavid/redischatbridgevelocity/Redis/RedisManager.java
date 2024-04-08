package thedavid.redischatbridgevelocity.Redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;

public class RedisManager{
	public RedisChatBridgeVelocity plugin;
	public RedisManager(RedisChatBridgeVelocity plugin){
		this.plugin = plugin;
	}
	public JedisPool jedisPool;
	public String loginBridgeChannel = "LoginBridge";
	public String disconnectBridgeChannel = "DisconnectBridge";
	public String syncCommandChannel = "SyncCommand";
	public SyncCommandSubThread syncCommandSubThread;
	public void initialize(){
		if(plugin.connectionInformation.getUsername() == null && plugin.connectionInformation.getPassword() == null){
			jedisPool = new JedisPool(new JedisPoolConfig(),
					plugin.connectionInformation.getIp(),
					plugin.connectionInformation.getPort());
		}else{
			jedisPool = new JedisPool(new JedisPoolConfig(),
					plugin.connectionInformation.getIp(),
					plugin.connectionInformation.getPort(),
					plugin.connectionInformation.getUsername(),
					plugin.connectionInformation.getPassword());
		}
		RedisChatBridgeVelocity.logger.info(String.format("redis pool is starting, redis ip %s, redis port %d", plugin.connectionInformation.getIp(), plugin.connectionInformation.getPort()));
		syncCommandSubThread = new SyncCommandSubThread(jedisPool, plugin);
		syncCommandSubThread.start();
	}
}
