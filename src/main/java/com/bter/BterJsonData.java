package com.bter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BterJsonData {
	public static  Logger log = LogManager.getLogger(BterJsonData.class); 
	static DecimalFormat df = new DecimalFormat("#.0000");
	static String coin="qtum";
	
	
	public static Object getBtc_rmb(){
		String url = "http://data.bter.com/api2/1/orderBook/btc_cny";
		JSONObject btc =BterAPI.getByUrl(url);
		if(btc==null) return null;
		JSONArray _json=btc.getJSONArray("asks");
		JSONArray btc_1 =(JSONArray) _json.get(_json.size()-1);
		JSONArray btc_2 =(JSONArray)btc.getJSONArray("bids").get(0);
		 url = "http://data.bter.com/api2/1/orderBook/"+coin+"_btc";
		 btc =BterAPI.getByUrl(url);
		 if(btc==null) return null;
		 _json=btc.getJSONArray("asks");
		 JSONArray center_1 =(JSONArray) _json.get(_json.size()-1);
		 JSONArray  center_2 =(JSONArray)btc.getJSONArray("bids").get(0);
			
		url = "http://data.bter.com/api2/1/orderBook/"+coin+"_cny";
		btc =BterAPI.getByUrl(url);
		 if(btc==null) return null;
		_json=btc.getJSONArray("asks");
		JSONArray rmb_1 =(JSONArray) _json.get(_json.size()-1);
		JSONArray rmb_2 =(JSONArray)btc.getJSONArray("bids").get(0);
		//走比特币没得机会
		//                                      A                B                        C                D                     E                     F
		//String fomBtc_Ymb[]= new String[]{btc_1.getString(0),btc_1.getString(1),center_1.getString(0),center_1.getString(1),rmb_2.getString(1),rmb_2.getString(0)};
		//Map<String,String> result1 = fombtc_sanzai(fomBtc_Ymb);
		//                         K                       L                    G                     h                    i                 j
		String fomBtc_Ymb[]= new String[]{center_2.getString(0),center_2.getString(1), rmb_1.getString(1),rmb_1.getString(0),btc_2.getString(0),btc_2.getString(1)};
		 Map<String,String> result2 =   fomsanzai_btc(fomBtc_Ymb);
		 JSONObject result = new JSONObject();
		// result.put("order", result1);
		 result.put("reverse", result2);
		 return result;
	}
	
	public  static Map<String,String>  fombtc_sanzai(String array[])
	{
		// [A,B] [I,J]       中间量=  min( B/C  D  E)
		// [C,D] [K,L]			投入money  4352.98158=min(C*中间量*A ,F*中间量)
		// [H,G] [F,E]               3772   4352.98158/A/C*F=3772.274635030319
		//B 1
		//C 2
		//D3
		//E4
		//F5
		Map<String,String> result = new HashMap<String,String>();
		String[] tem= new String[]{chufa(array[1],array[2]),array[3] , array[4]};
		String centerTotal=  min(tem);
		result.put("btc_price", array[0]);
		result.put("c_", centerTotal);//可买入中间币
		result.put("c_btc_price", array[2]);//中间币兑换比特币的价格
		result.put("c_price", array[5]);//最后卖出的中间币价格
		result.put("coin", coin);
		String[] moeny_must= new String[2];
		moeny_must[0]= chenfa(chenfa(array[0],centerTotal),array[2]);
		moeny_must[1]=chenfa(array[5],centerTotal);
		String money=min(moeny_must);
		String _1=chufa(money,array[0]);
		_1=chufa(_1,array[2]);
		_1=chenfa(_1,array[5]);
		String[] a =  new String[2];
		a[0]=money;
		a[1]=_1;
		result.put("ino", money);//入
		result.put("out", _1);//入
		String key =RandomStringUtils.randomAlphanumeric(20);
		result.put("key", key);
		if(Double.valueOf(_1)>Double.valueOf(money))
		{
			HashMap<String,Object> hashMap =new HashMap<String,Object>();
			hashMap.put(key, result);
			AutoBuyCoin.setOrder(hashMap);
		}
		return result;
	}
	public  static Map<String,String>  fomsanzai_btc(String array[])  
	{
		// [A,B] [I,J]  中间量= min(G L  J/K)=28.65
		// [C,D] [K,L] 投入money=min(中间量*K*I,中间量*H=2323.77285)=1680.007776885
		// [H,G] [F,E]  		   产出的钱=2990.5897599：1680.007776885%H*K*I=2990.5897599
		//K:0 
		//L:1 
		//G 2 
		//H :3
		//i :4 
		//J :5
		Map<String,String> result = new HashMap<String,String>();
		String[] tem= new String[]{array[2],array[1],chufa(array[5],array[0])};
		String centerTotal=  min(tem);
		result.put("btc_price", array[4]);
		result.put("c_", centerTotal);//可买入中间币
		result.put("c_btc_price", array[0]);//中间币兑换比特币的价格
		result.put("c_price", array[3]);//最后卖出的中间币价格
		result.put("coin", coin);
		String[] moeny_must= new String[2];
		moeny_must[0]= chenfa(chenfa(centerTotal, array[0]),array[4]);
		moeny_must[1]=chenfa(centerTotal, array[3]);
		String money=min(moeny_must);
		String _1=chenfa(money,array[0]);
		_1=chenfa(_1,array[4]);
		_1=chufa(_1,array[3]);
		result.put("ino", money);//入
		result.put("out", _1);//入
		String key =RandomStringUtils.randomAlphanumeric(20);
		result.put("key", key);
		if(true)//Double.valueOf(_1)>Double.valueOf(money))
		{
			HashMap<String,Object> hashMap =new HashMap<String,Object>();
			hashMap.put(key, result);
			AutoBuyCoin.setReverse(hashMap);
		}
		return result;
	}
	
	public static  String  chufa(String a1,String a2)
	{
		BigDecimal bigDecimal1 = new BigDecimal(a1);
		BigDecimal bigDecimal2 = new BigDecimal(a2);
		return bigDecimal1.divide(bigDecimal2,10,BigDecimal.ROUND_HALF_EVEN).toPlainString();
	}
	public static String chenfa(String a1,String a2)
	{
		BigDecimal bigDecimal1 = new BigDecimal(a1);
		BigDecimal bigDecimal2 = new BigDecimal(a2);
		return bigDecimal1.multiply(bigDecimal2).setScale(10,BigDecimal.ROUND_HALF_EVEN).toPlainString();
	}
	
	
	/**
	 * 只买90%的量
	 * @param a
	 * @return
	 */
	public static String min(String a[])
	{
		String min= Double.MAX_VALUE+"";
		for (int i = 0; i < a.length; i++) {
			if(Double.valueOf(min)>Double.valueOf(a[i])) min=a[i];
		}
		return Double.valueOf(min)*0.9+"";
	}
	
}
