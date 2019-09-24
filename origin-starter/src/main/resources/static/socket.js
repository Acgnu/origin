var socket = new WebSocket("ws:localhost:8080/wbskt/websocket/listener");
socket.onopen = function(){
	console.log("start connect");
	setInterval(function(){
		socket.send("throw your lem");
	}, 2000);
};
socket.onmessage = function(data){
	console.log("message", data);
};
socket.onclose = function(data){
	console.log("close", data);
};
socket.onerror = function(data){
	console.log("error", data);
};