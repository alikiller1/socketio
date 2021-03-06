package com.ppdai.chatroom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import com.corundumstudio.socketio.SocketIOServer;
import com.ppdai.chatroom.data.ChatObject;
import com.ppdai.chatroom.data.LoanInfo;
import com.ppdai.common.utils.DataParseUtils;

public class ProgressLoanPushTimerTask extends TimerTask {
	private SocketIOServer server;
	
	
	public ProgressLoanPushTimerTask(SocketIOServer server) {
		super();
		this.server = server;
	}


	@Override
	public void run() {
		List<LoanInfo> dataList=null;
		try {
		dataList=	DataParseUtils.queryList("http://invest.ppdai.com/loan/list_safe_s1_p1?Rate=0");
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		if(dataList!=null&&dataList.size()>0){
			ChatObject data=new ChatObject();
			data.setName("系统消息");
			data.setType("0");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime=dateFormat.format(new Date());
			data.setCurrenttime(currentTime);
			data.setLoanInfo(dataList.get(0));
			data.setContent("即将满标，快抢吧！");
			data.setUserType("0");
			System.out.println("消息定时推送->"+data);
			this.server.getBroadcastOperations().sendEvent("chatevent", data);
			// 保存最近的10条信息
			if (ChateventListener.recentData.size() < 10) {
				ChateventListener.recentData.add(data);
			} else {
				ChateventListener.recentData.remove(0);
				ChateventListener.recentData.add(data);
			}
		}
	}
	
	public static void main(String[] args) {
		List<String> list=new LinkedList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.remove(0);
		list.add("4");
		System.out.println(list.get(0));
		for(String s:list){
			System.out.println(s);
		}
		System.out.println("------------------------------");
		List<String> list2=new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		list2.remove(0);
		list2.add("4");
		System.out.println(list2.get(0));
		for(String s:list2){
			System.out.println(s);
		}
	}
	
	

}
