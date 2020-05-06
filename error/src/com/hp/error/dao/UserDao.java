package com.hp.error.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hp.error.entity.User;
import com.hp.error.util.C3P0DataSource;
import com.hp.error.util.DateFormateUtil;



public class UserDao {

	private Connection ct = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 登录方法
	public User check(User user) {
		// TODO Auto-generated method stub
		// 得到连接
		ct = C3P0DataSource.getConnection();
		String sql = "select * from t_user where userName = ? and password = ? and userType=0";
		try {
			ps = ct.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			rs = ps.executeQuery();
			if (rs.next()) {
				User temp = new User(rs.getInt("id"), rs.getString("userName"), rs.getString("trueName"),
						rs.getString("sex"), rs.getString("birthday"), rs.getString("statusID"), rs.getString("phone"),
						rs.getString("address"), rs.getString("email"), rs.getInt("userType"),
						rs.getString("password"));
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return null;
	}

//	// 修改密码方法
//	public int updateUser(User user) {
//		// TODO Auto-generated method stub
//		ct = C3P0DataSource.getConnection();
//		System.out.println(user);
//		String sql = "update t_user set password=? where id=?";
//		try {
//			ps = ct.prepareStatement(sql);
//			ps.setString(1, user.getPassword());
//			ps.setInt(2, user.getId());
//			return ps.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			C3P0DataSource.close(rs, ps, ct);
//		}
//
//		return 0;
//	}

	// 分页
	/**
	 * 
	 * @return
	 */
	public List<User> getAll(int pageNow, int pageSize) {
		List<User> list = new ArrayList<User>();
		try {
			ct = C3P0DataSource.getConnection();
			String sql = "select * from (select t_user.*,rownum rn from t_user where rownum<=? order by id)where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageSize * pageNow);
			ps.setInt(2, pageSize * (pageNow - 1) + 1);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setTrueName(rs.getString(3));

				user.setSex(rs.getString(4));
				user.setBirthday(DateFormateUtil.formatDate(rs.getDate(5)));
				user.setStatusID(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setEmail(rs.getString(9));
				user.setUserType(rs.getInt(10));
				user.setPassword(rs.getString(11));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			C3P0DataSource.close(rs, ps, ct);
			
			
		}
		return list;
	}

	// 总记录数
	public int findCount() {
		int i = 0;
		try {
			ct = C3P0DataSource.getConnection();

			String sql = "select count(*) from t_user";

			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();

			rs.next();
			if (rs != null) {
				i = rs.getInt(1);
			} else {
				i = 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}
		return i;
	}

	public static void main(String[] args) {

		UserDao dao = new UserDao();
		System.out.println("--------------1");
		List<User> list = dao.getAll(1, 10);

		for (User u : list) {
			System.out.println(u);
		}
		System.out.println("--------------");

		int i = dao.findCount();

		System.out.println(i);
	}
	//添加
	public int addUser(User user) {
		// TODO Auto-generated method stub
		ct = C3P0DataSource.getConnection();
		String sql = "insert into t_user values(t_user_seq.nextval,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?) ";
		try {
			ps = ct.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getTrueName());
			ps.setString(3, user.getSex());
			ps.setString(4, user.getBirthday());
			ps.setString(5, user.getStatusID());
			ps.setString(6, user.getPhone());
			ps.setString(7, user.getAddress());
			ps.setString(8, user.getEmail());
			ps.setInt(9, user.getUserType());
			ps.setString(10, user.getPassword());
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return 0;
	}
	//修改
	public int update(User user) {
		// TODO Auto-generated method stub
		ct = C3P0DataSource.getConnection();
		System.out.println(user);
		String sql = "update t_user set userName=?,trueName=?,sex=?,birthday=to_date(?,'yyyy-mm-dd'),statusID=?,phone=?,address=?,email=?,userType=?, password=? where id=?";
		try {
			ps = ct.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getTrueName());
			ps.setString(3, user.getSex());
			ps.setString(4, user.getBirthday());
			ps.setString(5, user.getStatusID());
			ps.setString(6, user.getPhone());
			ps.setString(7, user.getAddress());
			ps.setString(8, user.getEmail());
			ps.setInt(9, user.getUserType());
			ps.setString(10, user.getPassword());
			ps.setInt(11, user.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			C3P0DataSource.close(rs, ps, ct);
		}

		return 0;
	}
	
	// 批量删除用户
		public void deleteAll(List<Integer> ids) {
			try {
				for (Integer id : ids) {
					ct = C3P0DataSource.getConnection();
					String sql = "delete from t_user where id=?";
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
		//模糊查询分页
		
		/**
		 * 
		 * @return
		 */
		public List<User> findAllByName(int pageNow, int pageSize,String userName) {
			List<User> list = new ArrayList<User>();
			try {
				ct = C3P0DataSource.getConnection();
				String sql = "select * from (select t_user.*,rownum rn from (select *from t_user where userName like ? )t_user where rownum<=? order by id)where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(2, pageSize * pageNow);
				ps.setInt(3, pageSize * (pageNow - 1) + 1);
				ps.setString(1, "%" + userName + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt(1));
					user.setUserName(rs.getString(2));
					user.setTrueName(rs.getString(3));

					user.setSex(rs.getString(4));
					user.setBirthday(DateFormateUtil.formatDate(rs.getDate(5)));
					user.setStatusID(rs.getString(6));
					user.setPhone(rs.getString(7));
					user.setAddress(rs.getString(8));
					user.setEmail(rs.getString(9));
					user.setUserType(rs.getInt(10));
					user.setPassword(rs.getString(11));
					list.add(user);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				C3P0DataSource.close(rs, ps, ct);
				
			}
			return list;
		}

		// 总记录数
		public int findAllCount(String userName ) {
			int i = 0;
			try {
				ct = C3P0DataSource.getConnection();

				String sql = "select count(*) from t_user where userName like ?";

				ps = ct.prepareStatement(sql);

				rs = ps.executeQuery();

				rs.next();
				if (rs != null) {
					i = rs.getInt(1);
				} else {
					i = 0;
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				C3P0DataSource.close(rs, ps, ct);
			}
			return i;
		}
	
}