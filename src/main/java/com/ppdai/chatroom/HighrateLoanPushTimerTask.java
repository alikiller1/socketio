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

public class HighrateLoanPushTimerTask extends TimerTask {
	private SocketIOServer server;
	
	
	public HighrateLoanPushTimerTask(SocketIOServer server) {
		super();
		this.server = server;
	}


	@Override
	public void run() {
		List<LoanInfo> dataList=null;
		try {
		dataList=	DataParseUtils.queryList("http://invest.ppdai.com/loan/list_riskhigh_s3_p1?Rate=0");
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
			data.setContent("高收益，低风险，来来来！");
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
	
	
	
	

}
