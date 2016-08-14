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
		String ip=addr.getHostAddress().toString();//获得本机IP
		String address=addr.getHostName().toString();//获得本机名称
		System.out.println(ip+":"+address);
		config.setHostname(ip);
		config.setPort(9092);
		SocketIOServer server = new SocketIOServer(config);
		ChateventListener listner = new ChateventListener();
		listner.setServer(server);

		ConnListener cl = new ConnListener();
		cl.setServer(server);
		// chatevent为事件名称
		server.addEventListener("chatevent", ChatObject.class, listner);
		server.addConnectListener(cl);
		// 启动服务
		server.start();

		/*// 启动定时消息推送即将满标的借款
		ProgressLoanPushTimerTask task1 = new ProgressLoanPushTimerTask(server);
		long intevalPeriod = 60 * 1000;
		long delay = intevalPeriod;
		Timer timer1 = new Timer();
		timer1.scheduleAtFixedRate(task1, delay, intevalPeriod);

		// 启动定时消息推送高收益的标
		HighrateLoanPushTimerTask task2 = new HighrateLoanPushTimerTask(server);
		Timer timer2 = new Timer();
		timer2.scheduleAtFixedRate(task2, delay + 5000, intevalPeriod);*/

		Thread.sleep(Integer.MAX_VALUE);
		server.stop();
	}
}