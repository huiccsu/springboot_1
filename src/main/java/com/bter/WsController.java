package com.bter;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WsController {

	@RequestMapping("")
	public String websoket(String coin,Map<String,String> map) {
		if(StringUtils.isNoneBlank(coin))
			BterJsonData.coin=coin;
		else BterJsonData.coin="qtum";
		map.put("coin", BterJsonData.coin);
		return "/index";
	}
	
	@RequestMapping("order")
	@ResponseBody
	public Object order(double money,String key) {
		return AutoBuyCoin.btc_c(money,key);
	}
	
	@RequestMapping("myorder")
	public Object myorder(Map<String,Object> map) {
		JSONObject json = BterAPI.openOrders();
		if(json!=null)
			map.put("orders", json.get("orders"));
		return "/myorder";
	}
}