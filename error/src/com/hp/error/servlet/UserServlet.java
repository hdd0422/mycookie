package com.hp.error.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hp.error.dao.UserDao;
import com.hp.error.entity.User;
import com.hp.error.util.ResponseUtil;

import net.sf.json.JSONObject;

public class UserServlet extends HttpServlet {
	private static UserDao userDao = new UserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		User user = (User) request.getSession().getAttribute("user");
		int back = 0;
		JSONObject jsonObject = new JSONObject();

		/*
		 * if (user.getPassword().equals(oldPassword)) { //修改密码
		 * user.setPassword(newPassword); back = userDao.update(user);
		 * 
		 * if (back > 0) { jsonObject.put("success", true);
		 * 
		 * } else { jsonObject.put("error", true); }
		 * response.getWriter().print(jsonObject);
		 * request.getRequestDispatcher("/admin/main.jsp"); }
		 */

		if (type.equals("findAll")) {

			String userName = request.getParameter("s_userName");
			// 查询 所有
			System.out.println("分页查询");

			// 页面上分页的数据
			String pageNow = request.getParameter("page");// 当面显示的页码

			String pageSize = request.getParameter("rows");// 每页显示的条数

			System.out.println(pageSize);
			List<User> list = null;
			int count = 0;
			if (userName != null) {
				// 模糊查询
				System.out.println("模糊查询");
				list = userDao.findAllByName(Integer.parseInt(pageNow), Integer.parseInt(pageSize), userName);
				count = userDao.findAllCount(userName);
			} else {
				list = userDao.getAll(Integer.parseInt(pageNow), Integer.parseInt(pageSize));
				count = userDao.findCount();
			}
			System.out.println(count);

			JSONObject result = new JSONObject();

			Gson gson = new Gson();

			result.put("total", count);

			result.put("rows", gson.toJson(list));

			ResponseUtil.write(response, result);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
