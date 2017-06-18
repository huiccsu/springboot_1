package com.bter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class AutoBuyCoin {
	
	 //btc_price:买入比特币的价格
	//c_可买入中间币的数量
	//c_btc_price中间币兑换比特币的价格
	//c_price最后卖出的中间币价格
	//coin操作的是哪一种中间币
	public static Map<String,Object> order = new HashMap<String,Object>();
	public static Map<String,Object> reverse = new HashMap<String,Object>();
	static int maxtotal=100;
	public static Map<String, Object> getOrder() {
		return order;
	}

	public static void setOrder(Map<String, Object> order) {
		if(AutoBuyCoin.order.keySet().size()>maxtotal)
			order.clear();
		AutoBuyCoin.order.putAll(order);
	}

	public static Map<String, Object> getReverse() {
		return reverse;
	}

	public static void setReverse(Map<String, Object> reverse) {
		if(AutoBuyCoin.reverse.keySet().size()>maxtotal)
			AutoBuyCoin.reverse .clear();
		AutoBuyCoin.reverse.putAll(reverse);
	}

	/**
	 * 先入比特，再入中间币，最后卖出中间币获得人民币
	 * @param money
	 */
	@SuppressWarnings("unchecked")
	public static String   btc_c(double money,String key)
	{
		Map<String,String> myOrder = (Map<String,String>) reverse.get(key);
		if(myOrder==null || myOrder.isEmpty()) return "没有单可下";
		BigDecimal  bigDecimal = new BigDecimal(money);
		String btc_total = bigDecimal.divide(new BigDecimal(myOrder.get("btc_price")),4,BigDecimal.ROUND_HALF_EVEN).toPlainString();
		if(Double.valueOf(btc_total)<0.001)  return "最少得0.001个比特币，相当于人民币"+(Double.valueOf(btc_total)*Double.valueOf(myOrder.get("btc_price")));
		String center_total=myOrder.get("c_");
		String queryorder =BterAPI.hasOrders();
		 if(!"suc".equals(queryorder))
			 return queryorder;
		
		JSONObject json =  BterAPI.buy("btc_cny",myOrder.get("btc_price"),btc_total+"");
		if(json==null) return "买入第一步操作出错";
		if(json.containsKey("result"))
		{
			//如果买比特币下单成功
			if(json.getBoolean("result"))
			{
				boolean buyed =true;
				//是否已经买成功
				while(buyed)
				{
					queryorder = BterAPI.hasOrders();
					//如果没有买成功，则明继续
					if(!"suc".equals(queryorder)) continue;
					//已经买入成功
					buyed=false;
					boolean c_buyed=true;
					//开始买中间币
					buycenter( myOrder, center_total);

					while(c_buyed)
					{
						queryorder = BterAPI.hasOrders();
						//如果没有买成功，则明继续
						if(!"suc".equals(queryorder)) continue;
						c_buyed=false;
						boolean c_last=true;
						while(c_last)
						{
							json =BterAPI.sell(BterJsonData.coin+"_cny", myOrder.get("c_price"), center_total);
							if(json.getBoolean("result")) break;
						}
					}
				
				}
				return "操作成功";
			}
			return json.getString("message");
		}
		return "操作失败";
	}
	
	public static boolean buycenter(Map<String,String> myOrder,String center_total)
	{
		JSONObject json =  BterAPI.buy(BterJsonData.coin+"_btc",myOrder.get("c_btc_price"),center_total);
		if(json==null || !json.getBoolean("result"))  buycenter(myOrder,center_total);
		return true;
	}
}
