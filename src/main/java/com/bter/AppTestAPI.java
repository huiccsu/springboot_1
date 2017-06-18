package com.bter;

import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

public class AppTestAPI{
	JSONArray btc_1 = new JSONArray();
	JSONArray btc_2 = new JSONArray();
	JSONArray center_1 = new JSONArray();
	JSONArray center_2 = new JSONArray();
	JSONArray rmb_1 = new JSONArray();
	JSONArray rmb_2 = new JSONArray();

	public void init() {
		Random ranom = new Random();
		btc_1.add(0, ranom.nextInt(20000));
		btc_1.add(1, 0.6317);
		
		btc_2.add(0, ranom.nextInt(20000));
		btc_2.add(1, 0.1);
		
		center_1.add(0, 0.00349);
		center_1.add(1, 100);
		
		center_2.add(0, 0.003);
		center_2.add(1, 2);
		
		rmb_1.add(0, ranom.nextInt(100));
		rmb_1.add(1, 81.109);
		
		rmb_2.add(0, ranom.nextInt(100));
		rmb_2.add(1, 179.15569);
	}

	public  JSONObject testsuite() {
		 init() ;
		String fomBtc_Ymb[]= new String[]{btc_1.getString(0),btc_1.getString(1),center_1.getString(0),center_1.getString(1),rmb_2.getString(1),rmb_2.getString(0)};
		Map<String,String> result1 = BterJsonData.fombtc_sanzai(fomBtc_Ymb);
		
		//c d  h i g j               c                         d                    G                     h                    i                 j
		fomBtc_Ymb= new String[]{center_2.getString(0),center_2.getString(1), rmb_1.getString(1),rmb_1.getString(0),btc_2.getString(0),btc_2.getString(1)};
		Map<String,String> result2 =   BterJsonData.fomsanzai_btc(fomBtc_Ymb);
		 JSONObject result = new JSONObject();
		 result.put("order", result1);
		 result.put("reverse", result2);
		return result;
	}
}
