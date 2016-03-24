package com.wlhl.hong;


public class MyHomeData {
	public String product;
	public String web;
	public String weight;
	public String pr;
	public String price;
	@Override
	public String toString() {
		return "MyHomeData [product=" + product + ", web=" + web + ", weight="
				+ weight + ", pr=" + pr + ", price=" + price + "]";
	}
	public MyHomeData(String product, String web, String weight, String pr,
			String price) {
		super();
		this.product = product;
		this.web = web;
		this.weight = weight;
		this.pr = pr;
		this.price = price;
	}
}
