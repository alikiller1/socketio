package com.nettysocketio.test;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;

public class ConnListener implements ConnectListener {

    SocketIOServer server;

    public void setServer(SocketIOServer server) {
        this.server = server;
    }

	@Override
	public void onConnect(SocketIOClient client) {
		for(ChatObject o:ChateventListener.oldData){
			this.server.getClient(client.getSessionId()).sendEvent("chatevent", o);
		}
		
	}



    
}