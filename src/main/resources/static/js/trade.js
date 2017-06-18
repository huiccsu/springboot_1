function clearAll()
{
	$("tbody").empty();	
}
function myorder()
{
	window.location.href="/myorder";
}

function trSelect(ob)
{
	$("#money").val($(ob).data("money"));
	$("#key").val($(ob).data("key"));
	$("tr").css("background-color","");
	$(ob).css("background-color","#ccc");
}
function traindetail(data,type)
{
	var money =parseFloat(data.ino).toFixed(2);
	var html='<tr data-money="'+money+'" data-key="'+data.key+'" onclick=trSelect(this)>';
	if(type==0)
		html+='<td>先入btc,再转'+data.coin+'</td>';
	else
		html+='<td>先入'+data.coin+',再转btc</td>';
	html+='<td>'+parseFloat(data.ino).toFixed(2)+'</td>';
	html+='<td>'+parseFloat(data.out).toFixed(2)+'</td>';
	var last =parseFloat(data.out-data.ino).toFixed(2);
	if(last>0)
		html+='<td ><font color="green">'+last+'</font></td>';
	else
		html+='<td ><font color="red">'+last+'</font></td>';
	
	html+='<th>'+money+'</th>';
	if(type==0)
		html+='<td>'+btc_coin_treen(data)+'</td>';
	else
		html+='<td>'+coin_btc_treen(data)+'</td>';
	html+='</tr>';
	
	$("tbody").prepend(html);
}

function btc_coin_treen(data)
{
	var a1=parseFloat(data.ino/data.btc_price).toFixed(6);
	var a2=parseFloat(data.c_*data.c_price).toFixed(6);
	var html=data.ino+"￥=>btc（"+data.btc_price+"￥）入="+a1+"<br>";
	html+=""+data.c_+"个("+data.c_btc_price+" btc)入 <br>";
	html+=data.c_+"以（"+data.c_price+"￥）出="+a2;
	return html;
}


function coin_btc_treen(data)
{
	var btc =(data.c_*data.c_btc_price);
	var a1=parseFloat(data.ino/data.c_price).toFixed(6);
	var a2=parseFloat(btc*data.c_price).toFixed(6);
	
	var html=data.ino+"￥=>（"+data.c_price+"￥）入="+a1+"<br>";
	html+=""+data.c_+"个("+data.c_btc_price+" btc)入btc="+btc+" <br>";
	html+=btc+"以（"+data.btc_price+"￥）出="+a2;
	return html;
}
function train(data)
{
	//traindetail(data.order,0);
	traindetail(data.reverse,1);
}
var  stompClient ;
function connect() {
    var socket = new SockJS('/endpointSang');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/getResponse', function (response) {
            showResponse(JSON.parse(response.body).responseMessage);
        })
    });
}

 function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log('Disconnected');
}

function showResponse(message) {
   train(jQuery.parseJSON(message));
}
function order()
{
	var money = $("#money").val();
	var key = $("#key").val();
	if(!$.isNumeric(money))
	{
		alertMsg("请输入钱");
		return;
	}
	alertMsg("正在操作中....");
	 $.ajax({
		type : "post",
		url : "/order",
		dataType:"text",
		data:{"money":money,"key":key},
		success : function(data){
			layer.closeAll();
			alert(data);
		}   
	});	
}

function alertMsg(content,calback)
{
  if($.isFunction(calback))
	  layer.open({content: content,btn: '我知道了',shadeClose: false,
		  yes: function(index, layero){calback();}});
  else
	  layer.open({content: content, btn: '我知道了',shadeClose: false});
}