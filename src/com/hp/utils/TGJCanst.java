package com.hp.utils;

/**
 * 管理商家版常量
 * 
 * @author qwh
 * 
 */
public class TGJCanst {

	public static int LABLE_LOGIN = 10001;//
	public static int LABLE_BORDERINFO = 10002;// 获取新订单
	public static int LABLE_BORDERINFODETAIL = 100021;// 获取新订单详情
	public static int LABLE_BGETMONEY = 10003;// 获取商家收入
	public static int LABLE_BRequestMoney = 100031;// 申请提现
	public static int LABLE_BCodeValidate = 100022;// 扫码验证

	public static final String URL = "http://120.27.97.232:801";
	public static final String LOGIN_URL = "/BusinessApi/BLogin.aspx";// 登录信息
	public static final String BORDERINFO_URL = "/BusinessApi/BOrderinfoList.aspx";// 获取新订单链接
	public static final String BORDERINFODETAIL_URL = "/BusinessApi/BOrderinfoDetail.aspx";// 获取新订单详情
	public static final String BGETMONEY_URL = "/BusinessApi/BGetMoney.aspx";// 获取收入
	public static final String BRequestMoney_URL = "/BusinessApi/BRequestMoney.aspx";// 申请提现
	public static final String BCodeValidate_URL = "/BusinessApi/BCodeValidate.aspx";// 扫码验证
}
