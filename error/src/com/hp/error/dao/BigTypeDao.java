package com.hp.error.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hp.error.entity.BigType;
import com.hp.error.util.C3P0DataSource;


// 商品大类dao层
public class BigTypeDao {

	private Connection ct = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private BigType temp = null;

	// 全查询
	// 返回总记录数
	public int getTotal() {
		// TODO Auto-generated method stub
		try {
			// 得到连接
			ct = C3P0DataSource.getConnection();
			// 得到ps
			String sql = "select count(*) from  t_bigType";
			ps = ct.prepareStatement(sql);
			// 执行语句
			rs = ps.executeQuery();
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
		return 0;
	}

	// 返回每页记录数
	public List<BigType> getBigTypeByPage(int pageSize, int pageNow) {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			// 得到连接
			ct = C3P0DataSource.getConnection();
			// 得到ps
			String sql = "select *from(select al.*,rownum rn from(select * from  t_bigType order by id) al where rownum<="
					+ pageSize * pageNow + ") where rn>=" + (pageSize * (pageNow - 1) + 1);
			ps = ct.prepareStatement(sql);
			// 执行语句
			rs = ps.executeQuery();

			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return al;
	}

	// 模糊查询
	// 返回总记录数
	public int getTotal(String name) {
		// TODO Auto-generated method stub
		try {
			// 得到连接
			ct = C3P0DataSource.getConnection();
			// 得到ps
			String sql = "select count(*) from  t_bigType where name like ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			// 执行语句
			rs = ps.executeQuery();
			rs.next();// 游标下移,一定不能忘
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
		return 0;
	}

	// 返回每页记录数
	public List<BigType> getBigTypeByPage(int pageSize, int pageNow, String name) {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			// 得到连接
			ct = C3P0DataSource.getConnection();
			// 得到ps
			String sql = "select *from(select al.*,rownum rn from(select * from  t_bigType where name like ? order by id) al where rownum<="
					+ pageSize * pageNow + ") where rn>=" + (pageSize * (pageNow - 1) + 1);
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			// 执行语句
			rs = ps.executeQuery();

			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return al;
	}

	// 添加
	public int addBigType(BigType bigType) {
		// TODO Auto-generated method stub
		ct = C3P0DataSource.getConnection();
		String sql = "insert into t_bigType values(t_bigType_seq.nextval,?,?,?) ";
		try {
			ps = ct.prepareStatement(sql);
			ps.setString(1, bigType.getName());
			ps.setString(2, bigType.getRemarks());
			ps.setString(3, bigType.getProPic());

			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return 0;
	}

	// 根据id查询
	public BigType findBigType(int id) {
		// TODO Auto-generated method stub
		ct = C3P0DataSource.getConnection();
		String sql = "select * from t_bigType where id=? ";
		try {
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
			}
			return temp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
		return null;
	}

	// 修改
	public int update(BigType bigType) {
		// TODO Auto-generated method stub
		ct = C3P0DataSource.getConnection();
		String sql = "update t_bigType set name=?,remarks=?,proPic=? where id=? ";
		try {
			ps = ct.prepareStatement(sql);
			ps.setString(1, bigType.getName());
			ps.setString(2, bigType.getRemarks());
			ps.setString(3, bigType.getProPic());
			ps.setInt(4, bigType.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
		return 0;
	}

	public void deleteAll(List<Integer> ids) {
		// TODO Auto-generated method stub
		try {
			for (Integer id : ids) {
				ct = C3P0DataSource.getConnection();
				String sql = "delete from t_bigType where id=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
	}

	//商品大类信息全查询
	public List<BigType> selectAllBigType() {
		// TODO Auto-generated method stub
		List<BigType> al = new ArrayList<BigType>();

		try {
			// 得到连接
			ct = C3P0DataSource.getConnection();
			// 得到ps
			String sql = "select * from  t_bigType order by id";
			ps = ct.prepareStatement(sql);
			// 执行语句
			rs = ps.executeQuery();

			while (rs.next()) {
				// 循环读取数据,封装到BigType对象--存放到ArrayList集合【二次封装】
				temp = new BigType(rs.getInt("id"), rs.getString("name"), rs.getString("remarks"),
						rs.getString("proPic"));
				// 添加到集合里
				al.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return al;
	}
}
