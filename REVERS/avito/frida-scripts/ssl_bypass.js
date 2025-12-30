// SSL Pinning Bypass for Avito
// Usage: frida -U -f com.avito.android -l ssl_bypass.js

Java.perform(function() {
    console.log("[*] SSL Pinning Bypass for Avito");

    // TrustManager bypass
    var TrustManagerImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
    TrustManagerImpl.verifyChain.implementation = function(untrustedChain, trustAnchorChain, host, clientAuth, ocspData, tlsSctData) {
        console.log("[+] Bypassing SSL for: " + host);
        return untrustedChain;
    };

    // OkHttp CertificatePinner bypass
    try {
        var CertificatePinner = Java.use('okhttp3.CertificatePinner');
        CertificatePinner.check.overload('java.lang.String', 'java.util.List').implementation = function(hostname, peerCertificates) {
            console.log("[+] OkHttp bypass for: " + hostname);
            return;
        };
    } catch(e) {
        console.log("[-] OkHttp not found");
    }

    console.log("[*] SSL Bypass active");
});
