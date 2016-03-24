package com.wlhl.hong;

import android.graphics.Bitmap;
public class SelectBank {
	public String id;
	public String bankname;
	public Bitmap bankpic;
	
	public SelectBank(String id, String bankname, Bitmap bankpic) {
		super();
		this.id = id;
		this.bankname = bankname;
		this.bankpic = bankpic;
	}

	@Override
	public String toString() {
		return "MyBank [id=" + id + ", bankname=" + bankname + ", bankpic="
				+ bankpic + "]";
	}
	
}
