package de.vincidev.bungeeban.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.vincidev.bungeeban.Main;
import net.md_5.bungee.BungeeCord;

public class MySQL {
	private String host = "";
	private int port;
	private String username = "";
	private String password = "";
	private String database = "";
	private Connection conn;

	public MySQL(String host, int port, String username, String password, String database) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
	}

	public boolean isConnected() {
		return this.conn != null;
	}

	public void openConnection() {
		if (!isConnected()) {
			try {
				this.conn = DriverManager.getConnection(
						"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
						this.username, this.password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (isConnected()) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(String query) {
		if (isConnected()) {
			BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					try {
						PreparedStatement pst = conn.prepareStatement(query);
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	// Deprecated. New asynchronous method will follow.
	public ResultSet getResult(String query) {
		if (isConnected()) {
			try {
				PreparedStatement pst = this.conn.prepareStatement(query);
				return pst.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Connection getConn() {
		return this.conn;
	}

	public int getPort() {
		return this.port;
	}

	public String getDatabase() {
		return this.database;
	}

	public String getHost() {
		return this.host;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
