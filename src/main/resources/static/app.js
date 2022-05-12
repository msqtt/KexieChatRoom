var wsObj = null;
var wsUri = null;
var userId = -1;




function createWebSocket() {
    var host = window.location.host;

    //userId = GetQueryString("userId");
    userId = $("#usr").val();
    wsUri = "ws://" + host + "/ws/" + userId;

    try {
        wsObj = new WebSocket(wsUri);

        initWsEventHandle();

    } catch (e) {
        writeToScrean("关闭事件，开始重新连接");
    }
}

function initWsEventHandle() {
    try {
        wsObj.onopen = function (evt) {
            onWsOpen(evt);
        };

        wsObj.onmessage = function (evt) {
            onWsMessage(evt);
        }

        wsObj.onclose = function (evt) {
            writeToScrean("连接关闭");

            onWsClose(evt);
        }

        wsObj.onerror = function (evt) {
            writeToScrean("error事件, 开始重连");
            onWsError(evt);
        }

    } catch (e){
        writeToScrean("绑定事件没有成功");
    }
}

function writeToScrean(message) {
    if(DEBUG_FLAG)
    {
        $("#debuggerInfo").val($("#debuggerInfo").val() + "\n" + message);
    }
}
function onWsOpen(evt) {
}

function onWsClose(evt) {
    writeToScrean("disconnected");
}

function onWsError(evt) {
    writeToScrean(evt.data);
}

function onWsMessage(evt) {
    var jsonStr = evt.data;
    writeToScrean(jsonStr);
}

