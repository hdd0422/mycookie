package com.hp.error.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSource {
	private static final String driverName = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin://127.0.0.1:1521:orcl";
	private static final String name = "scott";
	private static final String pwd = "123456";
	private static ComboPooledDataSource ds = null;
	static {
		// 设置C3P0数据源
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driverName);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds.setJdbcUrl(url);
		ds.setUser(name);
		ds.setPassword(pwd);
		ds.setInitialPoolSize(5);
		ds.setMaxPoolSize(20);
		ds.setMinPoolSize(3);
	}

	// 得到连接
	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 关闭资源
	public static void close(ResultSet rs, PreparedStatement ps, Connection ct) {
		closeResultSet(rs);
		closePreparedStatement(ps);
		closeConnection(ct);

	}

	private static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void closePreparedStatement(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void closeConnection(Connection ct) {
		try {
			if (ct != null)
				ct.close();// 这里会把链接归还给dbcp连接池，并不是真正的断开链接.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}