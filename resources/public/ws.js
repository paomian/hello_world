function getk(name) {
    var cookie = document.cookie;
    name += "=";
    var pos = cookie.indexOf(name);
    if(pos != -1) {
        var start = pos + name.length;
        var end = cookie.indexOf(";",start);
        if(end == -1) end = cookie.length;
        return cookie.substring(start,end);
    }
    return "";
}
window.onload = function () {
    //var nick = prompt("Enter your nickname");
    var input = document.getElementById('input');
    input.focus();
    var socket = new WebSocket("ws://" + window.location.host +"/ws");
    socket.onmessage = function (event) {
        var msg = JSON.parse(event.data);
        if (msg.statu) {
            console.log(msg.statu);
        } 
        else {
            //var test = msg.length - 1;
            //console.log(msg[test]);
            console.log(msg);
            var node = document.createTextNode(msg.user + " : " + msg.msg);
            var li = document.createElement("li");
            li.appendChild(node);
            var div = document.getElementById('mydiv');
            div.insertBefore(li,input);
            input.scrollIntoView();
        }
    };
    input.onchange = function () {
        var msg = input.value;
        socket.send(JSON.stringify({"user":decodeURI(getk("nickname")),"msg":msg}));
        input.value = "";
    };
    setInterval(function() {
        var now = new Date;
        socket.send(JSON.stringify({"now":now}));
    }, 10000)
};
