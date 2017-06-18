<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>交易</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="/js/sockjs.min.js"></script>
<script src="/js/stomp.js"></script>
<script src="/js/jquery-1.11.1.min.js"></script>
<script src="/js/layer/layer.js"></script>
<script src="/js/trade.js"></script>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body>
<div style="margin: auto;">
	<ul class="nav nav-pills">
	  <li><a href="?coin=doge">狗币</a></li>
	  <li><a href="?coin=etc">以太经典</a></li>
	  <li><a href="?coin=ltc">莱特</a></li>
	  <li><a href="?coin=eth">以太币</a></li>
	  <li><a href="?coin=BTS">比特股</a></li>
	  <li><a href="?coin=ZEC">ZCash</a></li>
	  <li><a href="?coin=ETP">熵</a></li>
	  <li><a href="?coin=XEM">新经币</a></li>
	  <li><a href="?coin=NXT">未来币</a></li>
	  <li><a href="?coin=XMR">门罗币</a></li>
	  <li><a href="?coin=XPM">质数币</a></li>
	  <li><a href="?coin=PPC">点点币</a></li>
	  <li><a href="?coin=REP">REP</a></li>
	  <li><a href="?coin=DASH">达世币</a></li>
	  <li><a href="?coin=CNC">中国币</a></li>
	  <li><a href="?coin=FTC">羽毛币</a></li>
      <li><a href="?coin=XTC">物联币</a></li>
	  <li><a href="?coin=NMC">域名币</a></li>
	  <li><a href="?coin=XCP">合约币</a></li>
	  <li><a href="?coin=btrx">赎回代币</a></li>
	          
	</ul>
</div>
<div style="margin:auto 5%;">
	<ul class="nav nav-pills">
		<li><div style="margin:6px auto ">请输入（￥）：</div></li>
		<li><input type="text" class="form-control"  id="money"><input type="hidden" id="key"></li>
		<li><div id="myButtons1" class="bs-example"><button type="button" class="btn btn-primary" onclick="order();">买入</button></div></li>
		<li><div id="myButtons1" class="bs-example"><button type="button" class="btn btn-default" onclick="myorder()" >我的订单</button></div></li>
		<li><div id="myButtons1" class="bs-example"><button type="button" class="btn btn-default" id="clear" onclick="clearAll();">清屏</button></div></li>
	</ul>
</div>


	<table class="table table-striped">
		<caption>操作依据</caption>
		<thead>
			<tr>
				<th>买币途径</th>
				<th>入￥</th>
				<th>出￥</th>
				<th>盈亏￥</th>
				<th>花钱得回报</th>
				<th>操作途径</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
</body>
<script>
var coin=new Array();
coin["doge"]="狗币";
coin["etc"]="以太经典";
coin["ltc"]="莱特";
coin["eth"]="以太币";
coin["BTS"]="比特股";
coin["ZEC"]="ZCash";
coin["ETP"]="熵";
coin["XEM"]="新经币";
coin["NXT"]="未来币";
coin["XMR"]="门罗币";
coin["XPM"]="质数币";
coin["PPC"]="点点币";
coin["REP"]="REP";
coin["DASH"]="达世币";
coin["CNC"]="中国币";
coin["FTC"]="羽毛币";
coin["XTC"]="物联币";
coin["NMC"]="域名币";
coin["XCP"]="合约币";
coin["btrx"]="赎回代币";
$("#clear").html("清屏==>"+coin["${coin}"]);
$(function(){
	connect();
});
</script>
</html>
