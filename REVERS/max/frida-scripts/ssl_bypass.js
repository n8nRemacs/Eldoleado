// SSL Pinning Bypass for MAX (VK Teams)
// Usage: frida -U -f ru.mail.myteam -l ssl_bypass.js

Java.perform(function() {
    console.log("[*] SSL Pinning Bypass for MAX");

    // TrustManager bypass
    var X509TrustManager = Java.use('javax.net.ssl.X509TrustManager');
    var SSLContext = Java.use('javax.net.ssl.SSLContext');

    var TrustManager = Java.registerClass({
        name: 'com.custom.TrustManager',
        implements: [X509TrustManager],
        methods: {
            checkClientTrusted: function(chain, authType) {},
            checkServerTrusted: function(chain, authType) {},
            getAcceptedIssuers: function() { return []; }
        }
    });

    // OkHttp bypass
    try {
        var CertificatePinner = Java.use('okhttp3.CertificatePinner');
        CertificatePinner.check.overload('java.lang.String', 'java.util.List').implementation = function(hostname, peerCertificates) {
            console.log("[+] OkHttp bypass for: " + hostname);
            return;
        };
    } catch(e) {}

    // Conscrypt bypass
    try {
        var TrustManagerImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
        TrustManagerImpl.verifyChain.implementation = function(untrustedChain, trustAnchorChain, host, clientAuth, ocspData, tlsSctData) {
            console.log("[+] Conscrypt bypass for: " + host);
            return untrustedChain;
        };
    } catch(e) {}

    console.log("[*] SSL Bypass active");
});
