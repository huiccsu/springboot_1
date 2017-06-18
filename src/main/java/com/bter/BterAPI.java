package com.bter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BterAPI {
	
	public static  Logger log = LogManager.getLogger(BterAPI.class);
	/**
	 * 买入
	 * @param currencyPair 交易币种对 	ltc_btc
	 * @param rate 价格  	    0.023
	 * @param amount  交易量 	100
	 * @return
	 */
	public static JSONObject buy(String currencyPair,String rate,String amount)
	{
		
		String url = "https://api.bter.com/api2/1/private/buy";
		NameValuePair[] param = { new NameValuePair("currencyPair", currencyPair),
				new NameValuePair("rate", rate),
				new NameValuePair("amount", amount) };
		return   postByUrl(url,param);
	}
	
	public static JSONObject openOrders()
	{
		String url = "https://api.bter.com/api2/1/private/openOrders";
		NameValuePair[] param = { };
		return   postByUrl(url,param);
	}
	
	public static String hasOrders()
	{
		JSONObject jSONObject =openOrders();
		if(jSONObject==null) return "查询订单时失败";
		if(jSONObject.getBoolean("result"))
		{
			JSONArray jSONArray =jSONObject.getJSONArray("orders");
			if(jSONArray==null) return "查询订中没有orders";
			if(jSONArray.size()<=0)
				return "suc";
		}
		else log.error(jSONObject.toString());
		return jSONObject.getString("message");
	}
	/**
	 * 卖出
	 * @param pair 	交易币种对 	ltc_btc
	 * @param rate 价格 	0.023
	 * @param amount 交易量 	100
	 * @return
	 */
	public static JSONObject sell(String pair,String rate,String amount)
	{
		String url = "https://api.bter.com/api2/1/private/buy";
		NameValuePair[] param = { new NameValuePair("pair", pair),
				new NameValuePair("rate", rate),
				new NameValuePair("amount", amount) };
		return   postByUrl(url,param);
	}
	
	/**
	 * 取消下单
	 * @param orderNumber 下单单号 	123456
	 * @param currencyPair 交易币种对 	ltc_btc
	 * @return
	 */
	public static JSONObject cancelOrder(String orderNumber,String currencyPair)
	{
		String url = "https://api.bter.com/api2/1/private/buy";
		NameValuePair[] param = { new NameValuePair("orderNumber", orderNumber),
				new NameValuePair("currencyPair", currencyPair) };
		return   postByUrl(url,param);
	}
	
	public static JSONObject postByUrl(String url,NameValuePair[] param) 
	{
		 HttpClient httpClient = new HttpClient();
		try {
			HttpMethod method = postMethod( url,param);
			httpClient.executeMethod(method);
			String  content=method.getResponseBodyAsString();
			if(content.startsWith("{"))
				return JSONObject.fromObject(content);
			log.error(url+"=>post请求接口返回了错误信息");
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	public static JSONObject getByUrl(String url) 
	{
		 HttpClient httpClient = new HttpClient();
		try {
			HttpMethod method = getMethod(url, null);
			httpClient.executeMethod(method);
			BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
			    stringBuffer.append(str);  
			}  
			String content = stringBuffer.toString(); 
			
			if(content.startsWith("{"))
				return JSONObject.fromObject(content);
			log.error(url+"=>get请求接口返回了错误信息");
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	public static HttpMethod getMethod(String url, String param)
			throws IOException {
		GetMethod get = new GetMethod(url + "?" + param);
		get.setRequestHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
		get.releaseConnection();
		return get;
	}
	//用户首先要通过这个链接获取API接口身份认证用到的Key和Secret。 然后在程序中用Secret作为密码，通过SHA512加密方式签名需要POST给服务器的数据得到Sign，并在HTTPS请求的Header部分传回Key和Sign。请参考以下接口说明和例子程序进行设置。
	private static HttpMethod postMethod(String url,NameValuePair[] param) throws IOException {
		String params="";
		if(param!=null && param.length>0)
		{
			int index=0;
			for (NameValuePair nameValuePair : param) {
				if(index==0)
					params+=nameValuePair.getName()+"="+nameValuePair.getValue();
				else
					params+="&"+nameValuePair.getName()+"="+nameValuePair.getValue();
				index++;
			}
		}
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("KEY",Encrypt.key);
		 post.setRequestHeader("SIGN",Encrypt.sha512Param(params));
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
		post.setRequestBody(param);
		post.releaseConnection();
		return post;
	}
	
	
}
