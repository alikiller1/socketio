package com.ppdai.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ppdai.chatroom.data.LoanInfo;

public class DataParseUtils{
	public static void main(String[] args) throws IOException {
		String url = "http://invest.ppdai.com/loan/list_safe_s1_p1?Rate=0";
		String s="我的http://www.ppdai.com/list/17025228";
		System.out.println(filterLoanInfoUrl(s));
		System.out.println(isLoanInfoUrl("http://www.ppdai.com/list/17025228"));
		System.out.println(queryInfo( "http://www.ppdai.com/list/17025228"));
	}
	
	/***
	 * 查询借款列表
	 * @throws IOException
	 */
	public static void queryList(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements root = doc.getElementsByAttributeValue("class", "clearfix");
		Iterator<Element> es = root.iterator();
		int count = 0;
		List<LoanInfo> list = new ArrayList<LoanInfo>();
		while (es.hasNext()) {
			Element ele = es.next();
			String tagName = ele.tagName();
			if ("ol".equalsIgnoreCase(tagName)) {
				LoanInfo loanInfo = new LoanInfo();
				Elements e1 = ele.select(".ell");
				for (Element element : e1) {
					String purpose = element.attr("title");
					loanInfo.setPurpose(purpose);
					String loanUrl = element.attr("href");
					loanInfo.setUrl(loanUrl);
					break;
				}
				String userName = ele.select(".userInfo").get(0).getElementsByTag("a").get(0).text();
				loanInfo.setUserName(userName);

				String rete = ele.select(".brate").get(0).text().trim().replace("%", "");
				loanInfo.setRate(new BigDecimal(rete));

				String amont = ele.select(".sum").get(0).text();
				amont = amont.substring(1, amont.length());
				loanInfo.setAmount(new BigDecimal(amont.replaceAll(",", "")));

				String percent = ele.select(".process").get(0).getElementsByTag("p").get(0).text();
				percent = percent.replaceAll("\\s", ",");
				percent = percent.replaceAll("[^\\d,]", "");
				String[] ss = percent.trim().split(",");
				loanInfo.setBidNumber(new Integer(ss[0]));
				loanInfo.setPercent(new BigDecimal(ss[1]));

				String month = ele.select(".limitTime").get(0).text();
				month = month.trim().replaceAll("[^\\d,]", "");
				loanInfo.setMonth(new Integer(month));
				count++;
				list.add(loanInfo);
			}
		}
		for (LoanInfo loanInfo : list) {
			System.out.println(loanInfo);
		}
		System.out.println("count=" + count);
	}
	
	/**
	 * 查询借款信息
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static LoanInfo queryInfo(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		if(null==doc){
			return null;
		}
		Element body=doc.getElementsByTag("body").get(0);
		Element root=body.getElementsByTag("div").select(".newLendDetailbox").get(0);
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setUrl(url);
		String purpose=root.getElementsByTag("span").get(0).text();
		loanInfo.setPurpose(purpose);
		String userName=root.select(".newLendDetailInfoLeft").get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("title");
		loanInfo.setUserName(userName.trim());
		Element e1= root.select(".newLendDetailMoneyLeft").get(0);
		
		String amount=e1.getElementsByTag("dd").get(0).text();
		//System.out.println(e1);
		 amount=amount.replaceAll("¥", "").replace(",", "");
		 loanInfo.setAmount(new BigDecimal(amount));
		 
		 String rate=e1.getElementsByTag("dd").get(1).text().replace("%", "");
		 loanInfo.setRate(new BigDecimal(rate));
		 
		 String month=e1.getElementsByTag("dd").get(2).text().replaceAll("[^\\d,]", "");
		 
		 loanInfo.setMonth(new Integer(month));
		 
		 Element e2= root.select(".newLendDetailRefundLeft").get(0);
		 String percent=e2.getElementsByTag("span").get(1).attr("style");
		 percent= percent.split(":")[1].replace("%", "").replace(";","").trim();
		 loanInfo.setPercent(new BigDecimal(percent));
		String bidNumber= e2.getElementsByAttributeValue("class", "item w164").get(0).text();
		bidNumber=bidNumber.replaceAll("[^\\d,]", "").trim();
		loanInfo.setBidNumber(new Integer(bidNumber));
		return loanInfo;
	}
	/**
	 * 是否包含借款URL
	 * @param url
	 * @return
	 */
	public static boolean isLoanInfoUrl(String url){
		if(url.contains("http://www.ppdai.com/list")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 过滤出借款URL
	 * @param s
	 * @return
	 */
	public static  String filterLoanInfoUrl(String s){
		Pattern p=Pattern.compile("http://www\\.(ppdai)\\.(com/list/)\\d+", Pattern.CASE_INSENSITIVE);
		 Matcher matcher = p.matcher(s);  
	        if(matcher.find()){
	        	 return   matcher.group();  
	        }else{
	        	return null;
	        }
	}
}
