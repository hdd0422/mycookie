package com.hp.error.entity;

public class BigType {
	private int id;// 主键
	private String name, remarks, proPic;// 大类名称,大类描述,大类图片
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProPic() {
		return proPic;
	}
	public void setProPic(String proPic) {
		this.proPic = proPic;
	}
	public BigType() {
		
	}
	public BigType(int id, String name, String remarks, String proPic) {
		super();
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.proPic = proPic;
	}
	@Override
	public String toString() {
		return "BigType [id=" + id + ", name=" + name + ", remarks=" + remarks + ", proPic=" + proPic + "]";
	}
}
