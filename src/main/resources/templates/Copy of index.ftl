<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>交易</title>
    <script src="/js/sockjs.min.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/jquery-1.11.1.min.js"></script>
</head>
<body onload="connect()">
<div>
 <p id="response"></p>
</div>
<script type="text/javascript">
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
    
    function showResponse(message) {
        $("#response").append(message).append("<br>");
    }
</script>
</body>
</html>