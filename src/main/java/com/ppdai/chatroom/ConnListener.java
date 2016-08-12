package com.ppdai.chatroom;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.ppdai.chatroom.data.ChatObject;

public class ConnListener implements ConnectListener {

	SocketIOServer server;

	public void setServer(SocketIOServer server) {
		this.server = server;
	}

	@Override
	public void onConnect(SocketIOClient client) {
		String ip = client.getRemoteAddress().toString().replace("/", "").split(":")[0];
		System.out.println("有一个新的连接:" + ip);
		System.out.println("当前在线人数："+this.server.getAllClients().size());
		for (ChatObject o : ChateventListener.recentData) {
			this.server.getClient(client.getSessionId()).sendEvent("chatevent", o);
		}
	}

}