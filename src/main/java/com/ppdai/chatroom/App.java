package com.ppdai.chatroom;

import java.util.Timer;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.ppdai.chatroom.data.ChatObject;

public class App {
    public static void main(String[] args) throws InterruptedException
    {
        Configuration config = new Configuration();
       // config.setHostname("172.20.10.2");
       // config.setHostname("192.168.1.101");
        config.setHostname("10.5.3.148");
        config.setPort(9092);
        SocketIOServer server = new SocketIOServer(config);
        ChateventListener listner = new ChateventListener();
        listner. setServer(server);
        
        ConnListener cl=new ConnListener();
        cl.setServer(server);
        // chatevent为事件名称
        server.addEventListener("chatevent", ChatObject.class, listner);
        server.addConnectListener(cl);
        //启动服务
        server.start();
        
        //启动定时消息推送
        DataPushTimerTask task=new DataPushTimerTask(server);
	    long intevalPeriod = 10 * 1*1000;  
	    long delay =intevalPeriod;  
	    Timer timer = new Timer();  
	    timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
	    
        Thread.sleep(Integer.MAX_VALUE) ;
        server.stop();
    }
}