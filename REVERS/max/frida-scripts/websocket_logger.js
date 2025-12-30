// WebSocket Message Logger for MAX
// Usage: frida -U -f ru.mail.myteam -l websocket_logger.js

Java.perform(function() {
    console.log("[*] WebSocket Logger for MAX");

    // Hook OkHttp WebSocket
    try {
        var RealWebSocket = Java.use('okhttp3.internal.ws.RealWebSocket');

        RealWebSocket.send.overload('java.lang.String').implementation = function(text) {
            console.log("\n[WS SEND TEXT] " + text);
            return this.send(text);
        };

        RealWebSocket.send.overload('okio.ByteString').implementation = function(bytes) {
            console.log("\n[WS SEND BINARY] " + bytes.hex());
            return this.send(bytes);
        };

        console.log("[+] OkHttp WebSocket hooked");
    } catch(e) {
        console.log("[-] OkHttp WebSocket not found: " + e);
    }

    // Hook WebSocketListener
    try {
        var WebSocketListener = Java.use('okhttp3.WebSocketListener');

        WebSocketListener.onMessage.overload('okhttp3.WebSocket', 'java.lang.String').implementation = function(ws, text) {
            console.log("\n[WS RECV TEXT] " + text);
            return this.onMessage(ws, text);
        };

        WebSocketListener.onMessage.overload('okhttp3.WebSocket', 'okio.ByteString').implementation = function(ws, bytes) {
            console.log("\n[WS RECV BINARY] " + bytes.hex());
            return this.onMessage(ws, bytes);
        };

        console.log("[+] WebSocketListener hooked");
    } catch(e) {
        console.log("[-] WebSocketListener not found: " + e);
    }

    console.log("[*] WebSocket Logger active");
});
