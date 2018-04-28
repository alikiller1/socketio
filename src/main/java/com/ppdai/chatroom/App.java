package com.ppdai.chatroom;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.ppdai.chatroom.data.ChatObject;

public class App {
	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		Configuration config = new Configuration();
	//	String ip=InetAddress.getLocalHost().getAddress();
		InetAddress addr = InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();//鑾峰緱鏈満IP
		String address=addr.getHostName().toString();//鑾峰緱鏈満鍚嶇О
		System.out.println(ip+":"+address);
		config.setHostname(ip);
		config.setPort(9092);
		SocketIOServer server = new SocketIOServer(config);
		ChateventListener listner = new ChateventListener();
		listner.setServer(server);

		ConnListener cl = new ConnListener();
		cl.setServer(server);
		// chatevent涓轰簨浠跺悕绉�
		server.addEventListener("chatevent", ChatObject.class, listner);
		server.addConnectListener(cl);
		// 鍚姩鏈嶅姟
		server.start();

		/*// 鍚姩瀹氭椂娑堟伅鎺ㄩ�佸嵆灏嗘弧鏍囩殑鍊熸
		ProgressLoanPushTimerTask task1 = new ProgressLoanPushTimerTask(server);
		long intevalPeriod = 60 * 1000;
		long delay = intevalPeriod;
		Timer timer1 = new Timer();
		timer1.scheduleAtFixedRate(task1, delay, intevalPeriod);

		// 鍚姩瀹氭椂娑堟伅鎺ㄩ�侀珮鏀剁泭鐨勬爣
		HighrateLoanPushTimerTask task2 = new HighrateLoanPushTimerTask(server);
		Timer timer2 = new Timer();
		timer2.scheduleAtFixedRate(task2, delay + 5000, intevalPeriod);*/
		//test
		Thread.sleep(Integer.MAX_VALUE);
		server.stop();
	}
}