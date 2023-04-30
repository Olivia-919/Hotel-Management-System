package com.yanzhen.util;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * 对数据库操作底层封装
 * @author zhouk
 *
 */
public class JdbcUtil {
//	// 成员变量  连接数据库的url
//	public static String url="jdbc:mysql://localhost:3306/news";
//	public static String username="root";//用户名
//	public static String pwd="root";//密码
//	/**
//	 * 连接数据库
//	 */
//	public Connection getCoon() {
//		Connection con=null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			//获取连接
//			try {
//				con=DriverManager.getConnection(url, username, pwd);
//			} catch (SQLException e) {
//				System.out.println("连接数据库异常");
//			}
//		} catch (ClassNotFoundException e) {
//			System.out.println("不好意思，你的驱动包没找到");
//		}
//		return con;
//	}

	/**
	 * 查询 （通用查询）
	 * @param sql  通用的sql语句
	 */
	public static ResultSet  querySql(String sql) {
		//连接数据库
		Connection con= C3P0Utils.getConnection();
		Statement st=null;
		ResultSet rs=null;
		//创建执行对象
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("创建statement对象有问题");
			e.printStackTrace();
		}
		return rs;
	}


	/**
	 * 新增  修改 删除 公用方法
	 */
	public static int insertOrUpdateOrDeleteInfo(String sql) {
		Connection con=C3P0Utils.getConnection();
		Statement st=null;
		int num=0;
		try {
			st=con.createStatement();
			num=st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}


	/**
	 * 关闭连接
	 * @param con  连接对象
	 * @param st  执行sql对象
	 * @param rs  查询返回的一个结果集
	 */
	public void closeDB(Connection con,
						Statement st,
						ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("返回无结果集");
			}
		}

		if(st!=null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println("创建执行对象不存在");
			}
		}

		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("连接对象不存在，关闭有误");
			}
		}

	}
}
