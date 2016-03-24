package com.wlhl.hong;

public class Constant {

	/**
	 * sharedpreferences
	 * 
	 * @param args
	 */
	public class SP {
		public final static String SPNAME = "spname";
		public final static String ISFIRST = "isfirst";
		public final static String ACCOUNT = "account";
		public final static String US = "us";
	}

	public class REQUEST_RESULTCODE {
		public final static int RESULTCODE_MYACCOUNT = 1;
		public final static int RESULTCODE_MYBALANCE = 2;
		public final static String NAME_ACCOUNT = "account";
		public final static String NAME_PASSWORD = "password";
	}

	public class MYURL {
		public final static String SESSIONID = "http://192.168.0.23/wlhl/proitem/web-flowalli/writeapi.php?route=common/session/get";
		public final static String SALT = "http://192.168.0.23/wlhl/proitem/web-flowalli/writeapi.php?route=common/user/validate&s=";
		public final static String ROOT = "http://192.168.0.23/wlhl/proitem/web-flowalli/writeapi.php?route=";

		public final static String PHONEAUTH = ROOT
				+ "user/register/getphoneauth";

		public final static String REGISTER = ROOT
				+ "user/register/newRegister";

		public final static String REGISTERPHONE = ROOT
				+ "user/register/submitphone";
		public final static String LOGIN = ROOT + "common/user/checkLogin";
		public final static String FORGET = ROOT + "user/forgetpwd/submit";
		public final static String GETAUTH = ROOT
				+ "user/forgetpwd/getphoneauth";

		public final static String SEOSEARCH = ROOT + "index/seo/search";

		public final static String JPTJ = ROOT + "common/apprecommend/and_jptj";// 精品推荐
		public final static String CSPH = ROOT + "common/apprecommend/ios_csph";// 出售排行
		public final static String ZXWZ = ROOT + "common/apprecommend/ios_zxwz";// 出售排行
		public final static String WEBINFO = ROOT + "website/website/getdetail";// 网站详情
		public final static String WEB_GET = ROOT + "website/category/get";// 网站分类
		public final static String WEBRANKING = ROOT + "index/website/sort";// 网站排行

		public final static String GETBANNER = ROOT + "user/reurl/get";// 获取广告
		public final static String GETSEO = ROOT + "index/seo/search";// 获取广告
		public final static String USER = ROOT + "user/userdata/get";// 获取个人信息
		public final static String PASSWORD = ROOT + "common/user/uppassword";// 修改密码
		public final static String MODIFYPHONEAUTH = ROOT
				+ "user/userdata/getphoneauth";// 修改电话验证码
		public final static String MODIFYPHONE = ROOT + "user/userdata/chphone";// 修改电话
		public final static String BANK = ROOT + "common/bank/get";// 遍历银行列表
		public final static String BANKPIC = "http://192.168.0.23/wlhl/handle/web-flowalli/readimg.php?exec=";// 银行logo
		public final static String SAVEBANK = ROOT + "user/bankcard/append";// 添加银行
		public final static String GETBANK = ROOT + "user/bankcard/get";// 获取我的绑定银行
		public final static String DELETEBANK = ROOT + "user/bankcard/drop";// 删除银行
		public final static String ADDWEBSITE = ROOT + "website/website/append";// 添加网站
		public final static String GETCLASSFICATION = ROOT
				+ "website/category/get";// 获取网站类别
		public final static String DELETEWEB = ROOT + "website/website/drop";// 删除网站
		public final static String EDITWEB = ROOT + "website/website/update";// 编辑网站
		public final static String GETSETTING = ROOT + "user/smssetting/get";// 获取设置
		public final static String SETTING = ROOT + "user/smssetting/update";// 设置
		public final static String FEEDBACK = ROOT + "user/leaveword/add";// 留言
		public final static String SYSTEMMESSAGE = ROOT + "user/sysmessage/get";// 系统消息
		public final static String SYSTEMANNOUN = ROOT + "common/notice/get";// 系统公告

		public final static String MYORDERLIST = ROOT + "payment/order/get";// 我的订单列表
		public final static String UPDATAODER = ROOT + "user/order/updateorder";// 故障申请
		public final static String ORDERLOG = ROOT + "payment/order/orderlog";// 订单日志
		public final static String GOUMAILIIULIANG=ROOT+"payment/order/goumailiuliang";//购买流量
		
		
	}
}
