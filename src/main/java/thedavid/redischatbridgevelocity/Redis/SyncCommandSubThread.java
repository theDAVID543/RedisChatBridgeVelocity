package thedavid.redischatbridgevelocity.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;

public class SyncCommandSubThread extends Thread{
	private final JedisPool jedisPool;
	private final SyncCommandSubscriber syncCommandSubscriber;
	
	private final String channel;
	private final RedisChatBridgeVelocity plugin;
	
	public SyncCommandSubThread(JedisPool jedisPool, RedisChatBridgeVelocity plugin) {
		this.jedisPool = jedisPool;
		this.plugin = plugin;
		channel = plugin.redisManager.syncCommandChannel;
		syncCommandSubscriber = new SyncCommandSubscriber(plugin);
	}
	
	@Override
	public void run() {
		// 注意：subscribe是一個阻塞的方法，在取消訂閱該頻道前，thread會一直阻塞在這，無法執行會後續的Code
		RedisChatBridgeVelocity.logger.info("subscribe redis, channel: " + channel);
		
		try(Jedis jedis = jedisPool.getResource()){
			/* 取出一个連線*/
			jedis.subscribe(syncCommandSubscriber, channel);    //通過subscribe 的api去訂閱，傳入參數為訂閱者和頻道名
		}catch(Exception e){
			RedisChatBridgeVelocity.logger.info("subscribe channel error: "+ e);
		}
	}
	@Override
	public void interrupt() {
		syncCommandSubscriber.unsubscribe();
		super.interrupt();
	}
}
