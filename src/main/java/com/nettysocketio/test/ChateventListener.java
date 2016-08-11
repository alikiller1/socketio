package com.nettysocketio.test;


import java.util.LinkedList;
import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class ChateventListener implements DataListener<ChatObject> {
	public static List<ChatObject> oldData=new LinkedList<ChatObject>();
    SocketIOServer server;

    public void setServer(SocketIOServer server) {
        this.server = server;
    }

    public void onData(SocketIOClient client, ChatObject data,
            AckRequest ackSender) throws Exception {
    	data.setIp(client.getRemoteAddress().toString().replace("/", "").split(":")[0]);
    	if(StringUtils.isNotBlank(data.getMessage())){
    		  this.server.getBroadcastOperations().sendEvent("chatevent", data);
    		  if(oldData.size()<10){
    			  ChateventListener.oldData.add(data);
    		  }else{
    			  ChateventListener.oldData.remove(0);
    			  ChateventListener.oldData.add(data);
    		  }
    		  
    	}
    	//data.setMessage(StringUtils.filterEmoji(data.getMessage(), ""));
        // chatevent为 事件的名称， data为发送的内容
    }
}