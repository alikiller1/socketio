package com.nettysocketio.test;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class App {
    public static void main(String[] args) throws InterruptedException
    {
        Configuration config = new Configuration();
        config.setHostname("10.5.4.231");
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
        Thread.sleep(Integer.MAX_VALUE) ;
        server.stop();
    }
}