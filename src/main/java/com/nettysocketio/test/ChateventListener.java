package com.nettysocketio.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class ChateventListener implements DataListener<ChatObject> {
	public static List<ChatObject> oldData = new LinkedList<ChatObject>();
	SocketIOServer server;

	public void setServer(SocketIOServer server) {
		this.server = server;
	}

	public void onData(SocketIOClient client, ChatObject data, AckRequest ackSender) throws Exception {
		String ip = client.getRemoteAddress().toString().replace("/", "").split(":")[0];
		data.setIp(ip);
		if (StringUtils.isNotBlank(data.getMessage())) {
			this.server.getBroadcastOperations().sendEvent("chatevent", data);
			if (oldData.size() < 10) {
				ChateventListener.oldData.add(data);
			} else {
				ChateventListener.oldData.remove(0);
				ChateventListener.oldData.add(data);
			}
			String msg = StringUtils.filterEmoji(data.getMessage(), "");
			final List<Object> params = new ArrayList<>();
			params.add(ip);
			params.add(data.getUserType() == null ? 1 : 2);
			params.add(msg);
			new Thread() {
				public void run() {
					String sql = "insert into user_chat_info (ip,type,message) values ('"+params.get(0)+"','"+params.get(1)+"','"+params.get(2)+"')";
					//String sql = "INSERT INTO user_chat_info (ip,type,message) VALUES (?,?,?)";
					System.out.println(sql);
					try {
						Connection conn=DBUtil.getConnection();
						PreparedStatement pst = conn.prepareStatement(sql);
					/*	pst.setString(1, params.get(0) + "");
					 	pst.setString(2, params.get(1) + "");
						pst.setString(3, params.get(2) + "");*/
						pst.executeUpdate(sql);
						DBUtil.closeCon(null, null, pst, conn);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}

	}
}