package com.ppdai.chatroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.ppdai.chatroom.data.ChatObject;
import com.ppdai.chatroom.data.LoanInfo;
import com.ppdai.common.utils.Constants;
import com.ppdai.common.utils.DBUtil;
import com.ppdai.common.utils.DataParseUtils;
import com.ppdai.common.utils.StringUtils;

public class ChateventListener implements DataListener<ChatObject> {
	// 保存最近的100条信息
	public static List<ChatObject> recentData = new LinkedList<ChatObject>();
	// 保存最近的10条信息
	public static List<ChatObject> pushDatas = new LinkedList<ChatObject>();
	SocketIOServer server;

	public void setServer(SocketIOServer server) {
		this.server = server;
	}

	public void onData(SocketIOClient client, ChatObject data, AckRequest ackSender) throws Exception {
		String ip = client.getRemoteAddress().toString().replace("/", "").split(":")[0];
		data.setIp(ip);
		System.out.println("收到的数据-->"+data);
		if (StringUtils.isNotBlank(data.getContent())) {
			String type = data.getType();
			if (null != type) {
				type = type.trim();
			}
			// type=0，表示发送了借款地址
			if ("0".equals(type)) {
				String loanid = data.getLoanid();
				String loanUrl = Constants.loan_url_pre + loanid.trim();
				LoanInfo loanInfo = DataParseUtils.queryInfo(loanUrl);
				data.setLoanInfo(loanInfo);
			}
			// 发送数据
			this.server.getBroadcastOperations().sendEvent("chatevent", data);
			// 保存最近的100条信息
			if (recentData.size() < 100) {
				ChateventListener.pushDatas.add(data);
			} else {
				ChateventListener.pushDatas.remove(0);
				ChateventListener.pushDatas.add(data);
			}
			// 保存最近的10条信息
			if (recentData.size() < 10) {
				ChateventListener.recentData.add(data);
			} else {
				ChateventListener.recentData.remove(0);
				ChateventListener.recentData.add(data);
			}

			String msg = StringUtils.filterEmoji(data.getContent(), "");
			final List<Object> params = new ArrayList<>();
			params.add(ip);
			params.add(data.getUserType() == null ? 3 : data.getUserType());
			params.add(data.getName());
			params.add(msg);
			params.add(data.getDesc() == null ? "" : data.getDesc());
			new Thread() {
				public void run() {
					String sql = "INSERT INTO user_chat_info (ip,user_type,name,content,msg_desc) VALUES (?,?,?,?,?)";
					System.out.println(sql);
					Connection conn = DBUtil.getConnection();
					try{
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, params.get(0) + "");
					pst.setString(2, params.get(1) + "");
					pst.setString(3, params.get(2) + "");
					pst.setString(4, params.get(3) + "");
					pst.setString(5, params.get(4) + "");
					pst.execute();
					}catch(Exception e){
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
}