package com.bter;

import net.sf.json.JSONObject;

public class TestJSON {
	
	public static JSONObject getOrdersJson(String orderNum)
	{
		String orders="{\"result\":\"true\",\"orders\":[{\"orderNumber\":\""+orderNum+"\",\"type\":\"sell\",\"rate\":0.0098,\"amount\":\"100000\",\"total\":\"980\",\"currencyPair\":\"doge_cny\",\"timestamp\":\"1470648164\",\"status\":\"open\"},{\"orderNumber\":\"0\",\"type\":\"sell\",\"rate\":0.00000204,\"amount\":\"1000204\",\"total\":\"2.04041616\",\"currencyPair\":\"doge_btc\",\"timestamp\":\"1470648850\",\"status\":\"open\"}],\"message\":\"Success\"}";
		return JSONObject.fromObject(orders);
	}
	
	public static JSONObject  getBuyedJson()
	{
		String orders="{\"result\":\"true\",\"orderNumber\":\"123456\",\"message\":\"Success\"}";
		return JSONObject.fromObject(orders);
	}
}
