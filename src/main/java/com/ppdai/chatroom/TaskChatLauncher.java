package com.ppdai.chatroom;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.ppdai.chatroom.data.ChatObject;
import com.ppdai.chatroom.data.TaskChatVo;

  
public class TaskChatLauncher {  
    private static Logger logger =LoggerFactory.getLogger(TaskChatLauncher.class);
      
    private static Configuration config ;  
    public static SocketIOServer server ;  
    private long currentUserId;  
  
    public TaskChatLauncher(long currentUserId){  
        this.currentUserId=currentUserId;  
    }  
      
    public void startLisener(){  
         try {  
                //防止多次启动  
                if(server == null){  
                	InetAddress addr = InetAddress.getLocalHost();
                	String ip=addr.getHostAddress().toString();//获得本机IP
            		String address=addr.getHostName().toString();//获得本机名称
            		System.out.println(ip+":"+address);
                    config = new Configuration();  
                    config.setHostname(ip);  
                    config.setPort(9091);  
                    server = new SocketIOServer(config);  
                    server.start();  
                }  
                  
                String namespace ="/"+ currentUserId;//构建命名空间  
                newNamespanceLisener(namespace);  
                  
            } catch (Exception e) { 
            	System.out.println();
                server.stop();  
                e.printStackTrace();  
            }  
          
           
    }  
  
    private void newNamespanceLisener(final String namespace) {  
       System.out.println("监听实时对话 namespace:"+namespace);  
        final SocketIONamespace newChatnsp = server.addNamespace(namespace); //设置命名空间  
           
         //监听连接上的时候动作  
        newChatnsp.addConnectListener(new ConnectListener() {  
                @Override  
                public void onConnect(SocketIOClient arg0) {  
                   System.out.println("namespace："+namespace+"连接上！");  
                }  
         });  
           
         //用namespace 来发送消息       
        newChatnsp.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {  
             @Override  
             public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) { 
                 newChatnsp.getBroadcastOperations().sendEvent("chatevent", data);  
             }  
  
         });  
          
    }  
    public static void main(String[] args) {
    	TaskChatLauncher t=new TaskChatLauncher(0);
    	t.startLisener();
    	TaskChatLauncher t1=new TaskChatLauncher(1);
    	t1.startLisener();
    	TaskChatLauncher t2=new TaskChatLauncher(2);
    	t2.startLisener();
	}
  
}  