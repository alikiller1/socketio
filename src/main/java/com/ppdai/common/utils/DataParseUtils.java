package com.ppdai.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ppdai.chatroom.data.JoinRecord;
import com.ppdai.chatroom.data.LoanInfo;

public class DataParseUtils {
	public static void main(String[] args) {
		// String url = "http://invest.ppdai.com/loan/list_safe_s1_p1?Rate=0";
		// String s="我的http://www.ppdai.com/list/17025228";
		// System.out.println(filterLoanInfoUrl(s));
		// System.out.println(isLoanInfoUrl("http://www.ppdai.com/list/17025228"));
		// System.out.println(queryInfo(
		// "http://invest.ppdai.com/loan/info?id=41831725"));

		// queryList("http://invest.ppdai.com/loan/listnew?LoanCategoryId=4");
		// https://member.niwodai.com/portal/inteBid/joinRecoredPage.do
		queryJoinRecord("https://member.niwodai.com/portal/inteBid/joinRecoredPage.do",
				"http://www.niwodai.com/portal/getIntebidInfo.do");
		// initKey("http://www.niwodai.com/portal/getIntebidInfo.do");
	}
	//11
	/***
	 * 查询借款列表
	 * 
	 * @throws IOException
	 */
	public static List<LoanInfo> queryList(String url) throws IOException {
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
				// System.out.println(ele);
				Element e = ele.getElementsByTag("p").get(0).getElementsByTag("a").get(0);
				String userName = e.text();
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
		return list;
	}

	/**
	 * 查询借款信息
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static LoanInfo queryInfo(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		if (null == doc) {
			return null;
		}
		Element body = doc.getElementsByTag("body").get(0);
		Element root = body.getElementsByTag("div").select(".newLendDetailbox").get(0);
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setUrl(url);
		String purpose = root.getElementsByTag("span").get(0).text();
		loanInfo.setPurpose(purpose);
		String userName = root.select(".newLendDetailInfoLeft").get(0).getElementsByTag("a").get(0)
				.getElementsByTag("img").get(0).attr("title");
		loanInfo.setUserName(userName.trim());
		Element e1 = root.select(".newLendDetailMoneyLeft").get(0);

		String amount = e1.getElementsByTag("dd").get(0).text();
		// System.out.println(e1);
		amount = amount.replaceAll("¥", "").replace(",", "");
		loanInfo.setAmount(new BigDecimal(amount));

		String rate = e1.getElementsByTag("dd").get(1).text().replace("%", "");
		loanInfo.setRate(new BigDecimal(rate));

		String month = e1.getElementsByTag("dd").get(2).text().replaceAll("[^\\d,]", "");

		loanInfo.setMonth(new Integer(month));

		Element e2 = root.select(".newLendDetailRefundLeft").get(0);
		String percent = e2.getElementsByTag("span").get(1).attr("style");
		percent = percent.split(":")[1].replace("%", "").replace(";", "").trim();
		loanInfo.setPercent(new BigDecimal(percent));
		String bidNumber = e2.getElementsByAttributeValue("class", "item w164").get(0).text();
		bidNumber = bidNumber.replaceAll("[^\\d,]", "").trim();
		loanInfo.setBidNumber(new Integer(bidNumber));
		return loanInfo;
	}

	/**
	 * 是否包含借款URL
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isLoanInfoUrl(String url) {
		if (url.contains("http://www.ppdai.com/list")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 过滤出借款URL
	 * 
	 * @param s
	 * @return
	 */
	public static String filterLoanInfoUrl(String s) {
		Pattern p = Pattern.compile("http://www\\.(ppdai)\\.(com/list/)\\d+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(s);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return null;
		}
	}

	/**
	 * 抓取你我贷掀风投标信息
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static List<JoinRecord> queryJoinRecord(String url, String url2) {
		Document doc = null;
		Connection conn = null;
		BigDecimal sum = new BigDecimal(0);
		List<JoinRecord> records = new ArrayList<JoinRecord>();
		try{
		List<String> keys = initKey(url2);
		for (String item : keys) {
			BigDecimal sum2 = new BigDecimal(0);
			List<JoinRecord> record2 = new ArrayList<JoinRecord>();
			for (int i = 1; i < 100; i++) {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// System.out.println("i="+i);
				conn = Jsoup.connect(url).timeout(5000);
				conn.data("pageNo", String.valueOf(i));
				// 540//ADZUNlYwVTkFY1RgUDReYAo6VWUCZAJgBTMFMQBvU2I=
				// 365//ADZUNlYwVTkFY1RgUDReYAo6VWUCZAJgBTMFNgBlU2Y=
				// 180//ADZUNlYwVTkFY1RgUDReYAo6VWUCZAJgBTQFPABiU2Y=
				// 90//ADZUNlYwVTkFY1RgUDReYAo6VWUCZAJgBTQFPQBiU24=
				// 月月升//ADZUNlYwVTkFY1RgUDReYAo6VWUCZAJgBTQFNgBuU2A=
				conn.data("period_Id", item);
				doc = conn.post();
				int count = 0;
				Element body = doc.getElementsByTag("body").get(0);
				Element root = body.getElementsByTag("div").select("table").get(0).select("tbody").get(0);
				Elements es = root.select("tr");
				Iterator<Element> it = es.iterator();
				while (it.hasNext()) {
					Element e = it.next();
					int count2 = 0;
					if (count >= 1) {
						JoinRecord jr = new JoinRecord();
						Elements td = e.select("td");
						Iterator<Element> tdIt = td.iterator();
						while (tdIt.hasNext()) {
							Element e2 = tdIt.next();
							if (count2 == 0) {
								jr.setName(e2.html());
							} else if (count2 == 1) {
								BigDecimal amount = new BigDecimal(e2.html().replaceAll(",", "").replaceAll("元", ""));
								jr.setAmount(amount);
								sum = sum.add(amount);
								sum2 = sum2.add(amount);
								break;
							}
							count2++;
						}
						// System.out.println(jr);
						records.add(jr);
						record2.add(jr);
					}
					count++;

				}
				if (count < 11) {
					break;
				}
				// System.out.println("--------------");
			}
			System.out.println("size=" + record2.size());
			System.out.println("sum2=" + sum2);
			System.out.println("avg="
					+ (sum2.divide(new BigDecimal(String.valueOf(record2.size())), 0, BigDecimal.ROUND_HALF_UP)));
		}
		System.out.println("sum size=" + records.size());
		System.out.println("sum=" + sum);
		System.out.println(
				"sum avg=" + (sum.divide(new BigDecimal(String.valueOf(records.size())), 0, BigDecimal.ROUND_HALF_UP)));

		add(records.size(), sum);
		return records;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void add(int size, BigDecimal sum) throws Exception {
		java.sql.Connection conn = DBUtil.getConnection();
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(now);
		PreparedStatement pst = conn.prepareStatement("delete from invest_info where date=?");
		pst.setString(1, date);
		pst.execute();
		pst = conn.prepareStatement("INSERT INTO invest_info VALUES(?,?,?)");
		pst.setInt(1, size);
		pst.setBigDecimal(2, sum);
		pst.setString(3, date);
		pst.execute();
		DBUtil.closeCon(null, null, pst, conn);
	}

	public static List<String> initKey(String url) throws Exception {
		List<String> result = new ArrayList<String>();
		Document doc = null;
		Connection conn = null;
		conn = Jsoup.connect(url).timeout(5000);
		doc = conn.post();
		// Elements root=doc.getElementsByTag("div");
		Elements root = doc.select("div[class=index_main w1180 clearfix]").get(1).select("a").select("[href^=https]");
		Iterator<Element> it = root.iterator();
		while (it.hasNext()) {
			Element e = it.next();
			String item = e.attr("href").split("\\?")[1].split("\\&")[0].substring(10);
			result.add(item);
			System.out.println(item);
		}
		return result;

	}
}
