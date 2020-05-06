package com.hp.error.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.hp.error.dao.BigTypeDao;
import com.hp.error.entity.BigType;
import com.hp.error.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class BigTypeServlet
 */
@WebServlet("/BigTypeServlet")
public class BigTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BigTypeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BigType bigType = null;
		BigTypeDao bigTypeDao = new BigTypeDao();
		JSONObject jsonObject = new JSONObject();
		int back = 0;
		String type = request.getParameter("type");
		if ("findAll".equals(type)) {// 分页查询
			String name = request.getParameter("name");
			int pageNow = Integer.parseInt(request.getParameter("page")); // 获取当前页码，easyui默认传到后台
			int pageSize = Integer.parseInt(request.getParameter("rows")); // 获取每页显示多少行，easyui默认传到后台
			int pageCount = 0;
			List<BigType> list = null;
			if (name != null) {
				// 模糊查询
				pageCount = bigTypeDao.getTotal(name);
				list = bigTypeDao.getBigTypeByPage(pageSize, pageNow, name);
			} else {
				// 全查询
				pageCount = bigTypeDao.getTotal();
				list = bigTypeDao.getBigTypeByPage(pageSize, pageNow);
			}
			Gson gson = new Gson();
			String jsonList = gson.toJson(list);
			jsonObject.put("total", pageCount);
			jsonObject.put("rows", jsonList);
			ResponseUtil.write(response, jsonObject);
		} else if ("addBigType".equals(type)) {// 添加
			bigType = new BigType();
			// 文件上传
			// 判断前台的form是否有 mutipart属性
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem fileItem = (FileItem) iterator.next();
						System.out.println(fileItem);
						// getFieldName()是获取表单字段名
						String fieldName = fileItem.getFieldName();
						// 判断前台字段 是普通form表单字段(sno sname)，还是文件字段
						if (fileItem.isFormField()) {// 表单字段
							if (fieldName.equals("name")) {// 根据name属性 判断item是name remarks 还是proPic?
								bigType.setName(fileItem.getString("UTF-8"));
							} else if (fieldName.equals("remarks")) {
								bigType.setRemarks(fileItem.getString("UTF-8"));
							}
						} else {// 文件上传
							// 获取文件名 getName()是获取文件名
							String fileName = fileItem.getName();
							// 限制文件格式,必须是gif，png,jpg
							// String limit = fileName.substring(fileName.indexOf(".") + 1);
//										if (!(limit.equalsIgnoreCase("png") || limit.equalsIgnoreCase("gif")
//												|| limit.equalsIgnoreCase("jpg"))) {
//											System.out.println("照片格式不对");
//											return;
//										}
							// 定义文件路径，指定上传位置(服务器路径)
							// 获取项目路径
							String path = "D:\\newworkspace\\zhaiShop\\WebContent\\images\\bigTypeImg";
							// 设置缓冲区 10MB DiskFileItemFactory
							factory.setSizeThreshold(10 * 1024 * 1024);
							// 限制文件大小 20MB,ServletFileUpload
							upload.setFileSizeMax(20 * 1024 * 1024);
							File file = new File(path, fileName);
							fileItem.write(file);// 上传
							System.out.println("上传成功" + fileName);
							// 地址存入实体类
							bigType.setProPic("images/bigTypeImg/" + fileName);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// ------------数据封装完毕-------------
			System.out.println(bigType);
			// 执行添加操作
			back = bigTypeDao.addBigType(bigType);
			if (back > 0) {
				jsonObject.put("success", true);

			} else {
				jsonObject.put("error", true);
			}
			response.getWriter().print(jsonObject);
		} else if ("updateBigType".equals(type)) {// 修改
			bigType = new BigType();
			bigType.setId(Integer.parseInt(request.getParameter("id")));
			// 文件上传
			// 判断前台的form是否有 mutipart属性
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem fileItem = (FileItem) iterator.next();
						// getFieldName()是获取表单字段名
						String fieldName = fileItem.getFieldName();
						// 判断前台字段 是普通form表单字段(sno sname)，还是文件字段
						if (fileItem.isFormField()) {// 表单字段
							if (fieldName.equals("name")) {// 根据name属性 判断item是name remarks 还是proPic?
								bigType.setName(fileItem.getString("UTF-8"));
							} else if (fieldName.equals("remarks")) {
								bigType.setRemarks(fileItem.getString("UTF-8"));

							}
						} else {
							// 文件上传
							// 获取文件名 getName()是获取文件名
							String fileName = fileItem.getName();
							// 判断文件是否上传
							if (fileName.equals("")) {
								continue;
							}
							// 限制文件格式,必须是gif，png,jpg
							String limit = fileName.substring(fileName.indexOf(".") + 1);
							if (!(limit.equalsIgnoreCase("png") || limit.equalsIgnoreCase("gif")
									|| limit.equalsIgnoreCase("jpg"))) {
								System.out.println("照片格式不对");
								return;
							}
							// 获取文件内容
							// 定义文件路径，指定上传位置(服务器路径)
							// 获取项目路径
							String path = "C:\\Users\\lenovo\\Desktop\\zhaiShop\\WebContent\\images\\bigTypeImg";
							// 设置缓冲区 10MB DiskFileItemFactory
							factory.setSizeThreshold(10 * 1024 * 1024);
							// 限制文件大小 20MB,ServletFileUpload
							upload.setFileSizeMax(20 * 1024 * 1024);
							File file = new File(path, fileName);
							fileItem.write(file);// 上传
							System.out.println("上传成功" + fileName);
							// 地址存入实体类
							bigType.setProPic("images/bigTypeImg/" + fileName);
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (bigType.getProPic() == null) {
					BigType temp = bigTypeDao.findBigType(bigType.getId());
					bigType.setProPic(temp.getProPic());
				}
			}
			// ------------数据封装完毕-------------
			// 执行修改操作
			back = bigTypeDao.update(bigType);
			if (back > 0) {
				jsonObject.put("success", true);

			} else {
				jsonObject.put("error", true);
			}
			response.getWriter().print(jsonObject);
		} else if ("deleteBigType".equals(type)) {// 批量删除
			String[] s_ids = request.getParameterValues("ids");
			String[] temp = s_ids[0].split(",");
			List<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < temp.length; i++) {
				ids.add(Integer.parseInt(temp[i]));
			}
			bigTypeDao.deleteAll(ids);
			jsonObject.put("success", true);
			response.getWriter().print(jsonObject);
		} else if ("selectList".equals(type)) {// 查询所有大类信息
			List<BigType> list = bigTypeDao.selectAllBigType();
			JSONArray array = new JSONArray();
			jsonObject.put("id", "");
			jsonObject.put("name", "请选择-----");
			array.add(jsonObject);
			array.addAll(JSONArray.fromObject(list));
			response.getWriter().print(array);
			/*
			 * Gson gson = new Gson(); String json = gson.toJson(list);
			 * response.getWriter().print(json);
			 */

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
