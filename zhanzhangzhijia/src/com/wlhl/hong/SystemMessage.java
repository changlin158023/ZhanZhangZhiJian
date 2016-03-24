package com.wlhl.hong;

public class SystemMessage {
	public String id;
	public String info;
	public String date;
	@Override
	public String toString() {
		return "SystemMessage [id=" + id + ", info=" + info + ", date=" + date
				+ "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public SystemMessage(String id, String info, String date) {
		super();
		this.id = id;
		this.info = info;
		this.date = date;
	}
	
}
