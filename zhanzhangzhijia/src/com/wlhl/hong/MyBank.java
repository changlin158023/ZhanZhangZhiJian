package com.wlhl.hong;


public class MyBank {
	public String myname;
	public String bankname;
	public String myid;
	public String cardnum;
	
	public MyBank(String myname, String bankname, String myid,
			String cardnum) {
		super();
		this.myname = myname;
		this.bankname = bankname;
		this.myid = myid;
		this.cardnum = cardnum;
	}

	@Override
	public String toString() {
		return "DisplayBank [myname=" + myname + ", bankname=" + bankname
				+ ", myid=" + myid + ", cardnum=" + cardnum + "]";
	}
	
}
