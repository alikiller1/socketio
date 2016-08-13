package com.ppdai.chatroom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		}
		if(!dataList.isEmpty()){
			ChatObject data=new ChatObject();
			data.setName("系统消息");
			data.setType("0");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime=dateFormat.format(new Date());
			data.setCurrenttime(currentTime);
			data.setLoanInfo(dataList.get(0));
			data.setContent("即将满标，快抢吧！");
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
	
	
	
	

}
