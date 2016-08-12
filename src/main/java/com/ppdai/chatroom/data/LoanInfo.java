package com.ppdai.chatroom.data;

import java.math.BigDecimal;

public class LoanInfo {
	/**用途*/
	private String purpose;
	/**投票地址*/
	private String url;
	/**借款用户*/
	private String userName;
	/**贷款金额*/
	private BigDecimal amount;
	/**期限*/
	private Integer month;
	/**年化收益率*/
	private BigDecimal rate;
	/**完成进度*/
	private BigDecimal percent;
	/**已投标人数*/
	private Integer bidNumber;
	
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getBidNumber() {
		return bidNumber;
	}
	public void setBidNumber(Integer bidNumber) {
		this.bidNumber = bidNumber;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "LoanInfo [purpose=" + purpose + ", url=" + url + ", userName="
				+ userName + ", amount=" + amount + ", month=" + month
				+ ", rate=" + rate + ", percent=" + percent + ", bidNumber="
				+ bidNumber + "]";
	}
}
