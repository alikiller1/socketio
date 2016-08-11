package com.nettysocketio.test;

public class ChatObject {
    private String userName;
    private String message;
    private String ip;

    public ChatObject() {
    }

    public ChatObject(String userName, String message,String ip) {
        super();
        this.userName = userName;
        this.message = message;
        this.ip=ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
}
