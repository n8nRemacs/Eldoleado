// HTTP Request/Response Logger for Avito
// Usage: frida -U -f com.avito.android -l http_logger.js

Java.perform(function() {
    console.log("[*] HTTP Logger for Avito");

    // Hook OkHttp interceptor
    var Interceptor = Java.use('okhttp3.Interceptor');
    var Buffer = Java.use('okio.Buffer');

    var RealInterceptorChain = Java.use('okhttp3.internal.http.RealInterceptorChain');
    RealInterceptorChain.proceed.overload('okhttp3.Request').implementation = function(request) {
        var url = request.url().toString();
        var method = request.method();

        console.log("\n[REQUEST] " + method + " " + url);

        // Log headers
        var headers = request.headers();
        for (var i = 0; i < headers.size(); i++) {
            console.log("  " + headers.name(i) + ": " + headers.value(i));
        }

        // Log body
        var body = request.body();
        if (body !== null) {
            var buffer = Buffer.$new();
            body.writeTo(buffer);
            console.log("[BODY] " + buffer.readUtf8());
        }

        var response = this.proceed(request);
        console.log("[RESPONSE] " + response.code() + " " + response.message());

        return response;
    };

    console.log("[*] HTTP Logger active");
});
