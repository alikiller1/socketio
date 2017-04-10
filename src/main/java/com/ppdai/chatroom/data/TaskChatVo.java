package com.ppdai.chatroom.data;

import java.io.Serializable;

public class TaskChatVo implements Serializable{  
	  
    private static final long serialVersionUID = 1L;  
  
    /** 接收人 */  
    private long receiveUserId;  
      
    /** 发送人 */  
    private long sendUserId;  
  
    /** 内容 */  
    private String content;  
      
      
    public TaskChatVo(){}  
      
      
    public TaskChatVo(long id,long sendUserId,String content){  
        this.receiveUserId=id;  
        this.sendUserId=sendUserId;  
        this.content=content;  
    }  
  
    public long getReceiveUserId() {  
        return receiveUserId;  
    }  
  
    public void setReceiveUserId(long receiveUserId) {  
        this.receiveUserId = receiveUserId;  
    }  
  
    public long getSendUserId() {  
        return sendUserId;  
    }  
  
    public void setSendUserId(long sendUserId) {  
        this.sendUserId = sendUserId;  
    }  
  
    public String getContent() {  
        return content;  
    }  
  
    public void setContent(String content) {  
        this.content = content;  
    }  
}  