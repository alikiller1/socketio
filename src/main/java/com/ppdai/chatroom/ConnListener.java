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
		System.out.println("有一个新的连接");
	for(ChatObject o:ChateventListener.recentData){
			this.server.getClient(client.getSessionId()).sendEvent("chatevent", o);
		}
	}
    
}