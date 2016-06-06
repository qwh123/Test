package com.hp.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

/**
 * 助手类 利用 PULL 解析xml文件
 * 
 * @author qwh
 */

public class PULLParserCityContentHandler {

	private Object javaBean = null;
	private String tagName; // 标签名称
	List<Object> list = null;

	/**
	 * 用 PullX 解析xml文件
	 * 
	 * @param strUrl
	 *            网络xml地址
	 * @param javaBean
	 *            实体类对象
	 * @return List 返回实体类的一个list集合
	 * @throws Exception
	 */

	public List<Object> parseReadXml(String strUrl, Object javaBean,
			String nodeName) throws Exception {
		// 要从网络上获取xml，要定义一个URL类对象

		URL url = new URL(strUrl);

		// 打开连接,。会返回HttpsURLConnection 对象

		HttpURLConnection httpsURLConnection = (HttpURLConnection) url

		.openConnection();

		// 现在是使用HTTP协议请求服务器的数据

		// 所以要设置请求方式

		httpsURLConnection.setRequestMethod("GET");

		// 设置 超时时间 为5秒
		httpsURLConnection.setConnectTimeout(5 * 1000);
		// 通过输入流获取网络xml内容
		// 取得输入流

		InputStream inputStream = httpsURLConnection.getInputStream();

		// *********************************************************
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(inputStream, "UTF-8");

		// 助手类 仿照 SAX
		list = new ArrayList<Object>();

		int eventType = pullParser.getEventType();// 解析后执行事件的Code
		// 取得实体类的 全限 名称
		String clazz = javaBean.getClass().getName();
		// 一直循环，直到文档结束
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				tagName = pullParser.getName();
				// 如果是目标标签（Model）开始，则说明需要实例化对象了
				if (tagName.equalsIgnoreCase(nodeName)) {
					try {
						javaBean = Class.forName(clazz).newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (javaBean != null) {
					Field[] fields = javaBean.getClass().getDeclaredFields();
					for (int j = 0; j < fields.length; j++) {
						if (tagName.equalsIgnoreCase(fields[j].getName())) {
							setAttribute(javaBean, fields[j].getName(),
									pullParser.nextText(),
									new Class[] { fields[j].getType() });
						}
					}

				}

				break;

			case XmlPullParser.END_TAG:

				// 如果遇到目标标签（Model）结束，则把目标标签（Model）对象添加进集合中
				// Field[] fields =javaBean.getClass().getDeclaredFields();
				if (pullParser.getName().equalsIgnoreCase(nodeName)
						&& javaBean != null) {
					list.add(javaBean);
					javaBean = null;
				}
				break;

			default:
				break;
			}

			// 如果xml没有结束，则导航到下一个目标标签（Model）节点
			eventType = pullParser.next();

		}

		return list;

	}

	/**
	 * 
	 * 
	 * @param object
	 * 
	 *            类
	 * 
	 * @param setName
	 * 
	 *            方法名
	 * 
	 * @param setValue
	 * 
	 *            方法设置
	 * 
	 * @param obj
	 * 
	 *            属性类型
	 * 
	 * @throws Exception
	 */

	public void setAttribute(Object object, String setName, String setValue,

	Class[] obj) {

		Method method;

		try {

			method = object.getClass().getMethod("set" + updateStr(setName),

			obj[0]);

			if (obj[0].equals(Integer.class) || obj[0].equals(int.class)) {

				method.invoke(object, new Integer(setValue));

			}

			if (obj[0].equals(Float.class) || obj[0].equals(float.class)) {

				method.invoke(object, new Float(setValue));

			}

			if (obj[0].equals(Short.class) || obj[0].equals(short.class)) {

				method.invoke(object, new Short(setValue));

			}

			if (obj[0].equals(Byte.class) || obj[0].equals(byte.class)) {

				method.invoke(object, new Byte(setValue));

			}

			if (obj[0].equals(Double.class) || obj[0].equals(double.class)) {

				method.invoke(object, new Double(setValue));

			}

			if (obj[0].equals(Date.class)) {

				method.invoke(object, new Date(new Long(setValue)));

			}

			if (obj[0].equals(java.util.Date.class)) {

				method.invoke(object, new java.util.Date(setValue));

			}

			if (obj[0].equals(Long.class) || obj[0].equals(long.class)) {

				method.invoke(object, new Long(setValue));

			}

			if (obj[0].equals(Boolean.class) || obj[0].equals(boolean.class)) {

				method.invoke(object, new Boolean(setValue));

			}

			if (obj[0].equals(String.class)) {

				method.invoke(object, setValue);

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * 把字符串的首字母改为大写
	 * 
	 * 
	 * @param str
	 * 
	 *            :
	 * 
	 * @return String
	 */

	public String updateStr(String str) {

		char c = (char) (str.charAt(0) - 32);

		String s = str.substring(1, str.length());

		return c + s;

	}

	/**
	 * 
	 * 把字符串的首字母改为小写
	 * 
	 * 
	 * @param str
	 * 
	 *            :
	 * 
	 * @return String
	 */

	public String updateSmall(String str) {

		char c = (char) (str.charAt(0) + 32);

		String s = str.substring(1, str.length());

		return c + s;

	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

}