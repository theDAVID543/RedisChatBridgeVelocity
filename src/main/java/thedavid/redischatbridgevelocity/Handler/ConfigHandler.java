package thedavid.redischatbridgevelocity.Handler;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import thedavid.redischatbridgevelocity.RedisChatBridgeVelocity;
import thedavid.redischatbridgevelocity.impl.ConnectionInformation;

import java.io.File;
import java.io.IOException;

public class ConfigHandler{
	private RedisChatBridgeVelocity plugin;
	public ConfigHandler(RedisChatBridgeVelocity plugin){
		this.plugin = plugin;
	}
	File configFile = new File("plugins/RedisChatBridgeVelocity/config.conf");
	HoconConfigurationLoader loader;
	CommentedConfigurationNode root;
	public void createCustomConfig() {
		configFile.getParentFile().mkdirs();
		loader = HoconConfigurationLoader.builder().setPath(configFile.toPath()).build();
		try {
			root = loader.load();
		} catch (IOException e) {
			System.err.println("An error occurred while loading this configuration: " + e.getMessage());
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			}
			return;
		}
		saveConfig();
	}
	public void saveConfig(){
		try {
			loader.save(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to save your messages configuration! Sorry! " + e.getMessage());
		}
	}
	public ConnectionInformation getConnectionInformation(){
		String host = root.getNode("Redis", "Host").getString();
		int port = root.getNode("Redis", "Port").getInt();
		String username = root.getNode("Redis", "Username").getString();
		String password = root.getNode("Redis", "Password").getString();
		if(host == null || port == 0 || username == null || password == null){
			root.getNode("Redis", "Host").setValue("127.0.0.1");
			root.getNode("Redis", "Port").setValue(6379);
			root.getNode("Redis", "Username").setValue("");
			root.getNode("Redis", "Password").setValue("");
			saveConfig();
			return null;
		}
		return new ConnectionInformation(host, port, username, password);
	}
}
