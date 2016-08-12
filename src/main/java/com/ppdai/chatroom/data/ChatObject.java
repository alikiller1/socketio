package com.ppdai.chatroom.data;

public class ChatObject {
    private String name;
    private String content;
    private String ip;
    private String type;
    private String status;
    private String currenttime;
    private String desc;
    private String loanid;
    private LoanInfo loanInfo;
    private String userType;
	public ChatObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChatObject(String name, String content, String ip, String type, String status, String currenttime,
			String desc, String loanid, LoanInfo loanInfo, String userType) {
		super();
		this.name = name;
		this.content = content;
		this.ip = ip;
		this.type = type;
		this.status = status;
		this.currenttime = currenttime;
		this.desc = desc;
		this.loanid = loanid;
		this.loanInfo = loanInfo;
		this.userType = userType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLoanid() {
		return loanid;
	}
	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}
	public LoanInfo getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "ChatObject [name=" + name + ", content=" + content + ", ip=" + ip + ", type=" + type + ", status="
				+ status + ", currenttime=" + currenttime + ", desc=" + desc + ", loanid=" + loanid + ", loanInfo="
				+ loanInfo + ", userType=" + userType + "]";
	}
    
	
    
}
