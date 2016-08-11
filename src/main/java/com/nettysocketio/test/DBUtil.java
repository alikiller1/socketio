package com.nettysocketio.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	private static Connection connection;
	public static void main(String[] args) throws SQLException  {
			PreparedStatement pst = getConnection().prepareStatement("select * from user_chat_info where type=?");
			pst.setString(1, "a");
			ResultSet rs =pst.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString(1) + "-->" + rs.getString(2)+ "-->" + rs.getString(3));
			}
			DBUtil.closeCon(rs, pst, null, connection);

	}
	public static Connection getConnection(){
		if(null==connection){
			try {
				connection = DBUtil.getConnection(
						"jdbc:mysql://localhost:3306/testdb", "root", "123");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
		 
	}
	public static Connection getConnection(String url, String user, String pw)
			throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pw);
			return con;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void closeCon(ResultSet rs, Statement st,
			PreparedStatement pst, Connection con) {
		try {
			if (st != null) {
				st.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
