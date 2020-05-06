package com.hp.error.entity;


import java.io.Serializable;

 public class User implements Serializable {
	private int id;
	private String userName, trueName, sex, birthday, statusID, phone, address, email;
	private int userType;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 默认构造方法
	public User() {

	}

	// 为登录生成构造方法
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	// 为数据库封装数据生成构造方法
	public User(int id, String userName, String trueName, String sex, String birthday, String statusID, String phone,
			String address, String email, int userType, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.trueName = trueName;
		this.sex = sex;
		this.birthday = birthday;
		this.statusID = statusID;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.userType = userType;
		this.password = password;
	}

	// 生成toString方法
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", trueName=" + trueName + ", sex=" + sex + ", birthday="
				+ birthday + ", statusID=" + statusID + ", phone=" + phone + ", address=" + address + ", email=" + email
				+ ", userType=" + userType + ", password=" + password + "]";
	}

}