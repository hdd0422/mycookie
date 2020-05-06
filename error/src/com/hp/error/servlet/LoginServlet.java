package com.hp.error.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hp.error.dao.UserDao;
import com.hp.error.entity.User;






public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String vCode = (String) session.getAttribute("vCode");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String jyzm = request.getParameter("jyzm");
		if (vCode.equalsIgnoreCase(jyzm)) {
			User user = new User(username, password);
			UserDao userDao = new UserDao();
			User back = userDao.check(user);
			boolean isSuccess = back == null ? false : true;
			if (isSuccess) {
				session.setAttribute("user", back);
				request.setAttribute("stateOK", 0);
			} else {
				request.setAttribute("stateOK", 2);
			}

		} else {
			request.setAttribute("stateOK", 1);
		}

		request.getRequestDispatcher("/admin/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
