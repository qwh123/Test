package com.hp.utils;

/**
 * 轰趴常量
 * 
 * @author qwh
 * 
 */
public class hpCantant {

	public static  String UPDATE_URL = ".apk";// 版本更新地址
	public static final String HP_DB_NAME = "hp.db";// 数据库名
	public static final String GETDATA = "hp_data";
	public static final String USERINFO_DATA = "userinfo_data";// 存储用户信息
	public static final String TGJINFO_DATA = "tgjinfo_data";// 存储用户信息

	public static final int LABLE_UUpdate = 1000;// 更新app
	public static final int LABLE_LOGIN = 10000;// 登陆
	public static final int LABLE_UUserinfo = 100001;// 获取用户信息
	public static final int LABLE_PUPLOADIMAGETOKEN = 100001;// 获取七牛token
	public static final int LABLE_REGISTER = 10001;// 注册
	public static final int LABLE_URESETPSW = 100011;// 重置
	public static final int LABLE_UALERTPWD = 100012;// 注册
	public static final int LABLE_PUBLISH = 10002;// 公告
	public static final int LABLE_UINDEX = 10003;// 获取首页数据
	public static final int LABLE_UGOODINFO = 100031;// 商品
	public static final int LABLE_UGOODINFODETAIL = 1000311;// 商品详情
	public static final int LABLE_UFAVORITE = 1000312;// 收藏商品
	public static final int LABLE_UGetPrice = 1000313;// 获取优惠价格
	public static final int LABLE_UFAVORITELIST = 100033;// 获取收藏商品列表
	public static final int LABLE_UCOMMENTLIST = 100034;// 获取商品评论列表
	public static final int LABLE_BANNER = 10004;// 轮播
	public static final int LABLE_LOGOUT = 10005;// 退出登陆
	public static final int LABLE_UALERT = 10009;// 修改用户资料
	public static final int LABLE_UReback = 100091;// 用户反馈
	public static final int LABLE_UORDERCREATE = 10006;// 创建订单
	public static final int LABLE_UORDERINFO = 100061;// 获取订单列表
	public static final int LABLE_UOrderManager = 100064;// 订单管理
	public static final int LABLE_UORDERPAY = 100062;// 为未付款的订单付款
	public static final int LABLE_UGETQRCODE = 100063;// 获取二维码和券码
	public static final int LABLE_UACTIVECREATE = 10007;// 发布活动
	public static final int LABLE_UACTIVE = 100071;// 获取活动信息
	public static final int LABLE_UACTIVEDETAIL = 100072;// 获取活动详情信息
	public static final int LABLE_UACTIVESIGNUP = 100073;// 活动报名
	public static final int LABLE_UDYNAMICCREATE = 10008;// 发布动态
	public static final int LABLE_UDYNAMICLIST = 100081;// 获取动态信息
	public static final int LABLE_UDYNAMICCOMMENTLIST = 100082;// 获取动态详情
	public static final int LABLE_UDYNAMICPRAISE = 100084;// 用户动态点赞
	public static final int LABLE_UDynamicComment = 100083;// 用户动态评价
	public static final int LABLE_INDEXAD = 10010;// 启动页广告
	public static final int LABLE_UCITY = 10011;// 获取城市信息
	public static final int LABLE_UHotKey = 10013;// 获取热词
	public static final int LABLE_UGoodSearch = 10014;// 获取消息列表
	public static final int LABLE_UMessageList = 10014;// 搜索商品
	public static final int LABLE_UJumpBussiness = 10012;// 判断是否是商家

	public static final String TITLE = "category_title";
	public static final String LABLE_HP = "轰趴";// 轰趴
	public static final String LABLE_DS = "单身";// 单身
	public static final String LABLE_XY = "校园";// 校园
	public static final String LABLE_HW = "户外";// 户外
	public static final String LABLE_JR = "节日";// 节日
	public static final String LABLE_SL = "沙龙";// 沙龙
	public static final String LABLE_GY = "公益";// 公益
	public static final String LABLE_QT = "其他";// 其他

	public static final String TAG = "com.hp";
	public static final String URL = "http://120.27.97.232/UserApi";
	// 表示连接成功
	public static final String SUCCESS_CODE = "100";
	// 连接失败
	public static final String ERROR_CODE = "101";
	// 帐号或密码错误
	public static final String ERROR_COUNTORPSW = "102";
	// 连接失败
	public static final String ERROR_6 = "服务器断开连接";
	// 更新app
	public static String UUpdate_URL = "/UUpdate.aspx";
	// 登陆地址
	public static String ULOGIN_URL = "/ULogin.aspx";
	// 获取用户信息
	public static String UUserinfo_URL = "/UUserinfo.aspx";
	// 注册地址
	public static String UREGISTER_URL = "/URegister.aspx";
	// 重置
	public static String URESETPWD_URL = "/UResetPwd.aspx";
	// 修改密码
	public static String UALERTPWD_URL = "/UAlertPwd.aspx";
	// 获取首页轮播数据
	public static String UBANNER_URL = "/UBanner.aspx";
	// 获取首页轮播数据
	public static String UBannerDetail_URL = "/UBannerDetail.aspx?id=";
	// 获取首页商品信息列表
	public static String UGOODINFO_URL = "/UGoodinfoList.aspx";
	// 获取首页商品详情更多
	public static String UGoodDetail_URL = "/UGoodDetail.aspx?id=";
	// 获取首页商品详情信息
	public static String UGOODINFODETAIL_URL = "/UGoodinfoDetail.aspx";
	// 获取首页商品详情的评论列表
	public static String UCOMMENTLIST_URL = "/UCommentList.aspx";
	// 获取首页公告
	public static String UPUBLIC_URL = "/UPublish.aspx";
	// 获取首页公告
	public static String UPublishDetail_URL = "/UPublishDetail.aspx?id=";
	// 获取启动页广告
	public static String UINDEXAD_URL = "/UIndexAd.aspx";
	// 获取城市列表xml
	public static String UCITIES_URL = "/UCities.aspx";
	// 获取搜索热词
	public static String UHotKey_URL = "/UHotKey.aspx";
	// 搜索商品
	public static String UGoodSearch_URL = "/UGoodSearch.aspx";
	// 退出登陆
	public static String ULOGOUT_URL = "/ULogout.aspx";
	// 用户反馈
	public static String UReback_URL = "/UReback.aspx";
	// 获取订单列表
	public static String UORDERINFOLIST_URL = "/UOrderinfoList.aspx";
	// 订单管理
	public static String UOrderManager_URL = "/UOrderManager.aspx";
	// 获取二维码和券码
	public static String UGETQRCODE_URL = "/UGetCode.aspx";
	// 为未付款的订单付款
	public static String UORDERPAY_URL = "/UOrderPay.aspx";
	// 发布活动
	public static String UACTIVECREATE_URL = "/UActiveCreate.aspx";
	// 获取活动列表
	public static String UACTIVE_URL = "/UActiveList.aspx";
	// 活动报名
	public static String UACTIVESIGNUP_URL = "/UActivSignup.aspx";
	// 获取活动详情
	public static String UACTIVEDETAIL_URL = "/UActiveDetail.aspx";
	// 发布动态UDynamicCreate
	public static String UDYNAMICCREATE_URL = "/UDynamicCreate.aspx";
	// 获取动态 列表
	public static String UDYNAMIC_URL = "/UDynamicList.aspx";
	// 用户动态点赞
	public static String UDYNAMICPRAISE_URL = "/UDynamicPraise.aspx";
	// 用户动态评论
	public static String UDynamicComment_URL = "/UDynamicComment.aspx";
	// 获取动态详情表
	public static String UDYNAMICCOMMENTLIST_URL = "/UDynamicCommentList.aspx";
	// 收藏商品
	public static String UFAVORITE_URL = "/UFavorite.aspx";
	// 获取优惠价格
	public static String UGetPrice_URL = "/UGetPrice.aspx";
	// 获取收藏商品列表
	public static String UFAVORITELIST_URL = "/UFavoriteList.aspx";
	// 获取消息列表
	public static String UMessageList_URL = "/UMessageList.aspx";
	// 订单创建
	public static String UORDERCREATE_URL = "/UOrderCreate.aspx";
	// 修改用户资料
	public static String UALERTUSERINFO_URL = "/UAlertUserinfo.aspx";
	// 获取七牛Token
	public static String PUPLOADIMAGETOKEN_URL = "/PublicApi/PUploadImageToken.aspx";
	// 获取首页数据
	public static String UINDEX_URL = "/UIndex.aspx";

	// 判断是否是商家
	public static String UJumpBussiness = "/UJumpBussiness.aspx";

}
