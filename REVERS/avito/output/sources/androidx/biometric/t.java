package androidx.biometric;

import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.security.identity.IdentityCredential;
import android.security.keystore.KeyGenParameterSpec;
import androidx.biometric.p;
import j.N;
import j.P;
import j.X;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;

/* compiled from: CryptoObjectUtils.java */
/* loaded from: classes.dex */
class t {

    /* compiled from: CryptoObjectUtils.java */
    @X
    public static class a {
        private a() {
        }

        @N
        public static KeyGenParameterSpec a(@N KeyGenParameterSpec.Builder builder) {
            return builder.build();
        }

        @N
        public static KeyGenParameterSpec.Builder b(@N String str, int i12) {
            return new KeyGenParameterSpec.Builder(str, i12);
        }

        public static void c(@N KeyGenerator keyGenerator, @N KeyGenParameterSpec keyGenParameterSpec) {
            keyGenerator.init(keyGenParameterSpec);
        }

        public static void d(@N KeyGenParameterSpec.Builder builder) {
            builder.setBlockModes("CBC");
        }

        public static void e(@N KeyGenParameterSpec.Builder builder) {
            builder.setEncryptionPaddings("PKCS7Padding");
        }
    }

    /* compiled from: CryptoObjectUtils.java */
    @X
    public static class b {
        private b() {
        }

        @N
        public static BiometricPrompt.CryptoObject a(@N Signature signature) {
            return new BiometricPrompt.CryptoObject(signature);
        }

        @N
        public static BiometricPrompt.CryptoObject b(@N Cipher cipher) {
            return new BiometricPrompt.CryptoObject(cipher);
        }

        @N
        public static BiometricPrompt.CryptoObject c(@N Mac mac) {
            return new BiometricPrompt.CryptoObject(mac);
        }

        @P
        public static Cipher d(@N BiometricPrompt.CryptoObject cryptoObject) {
            return cryptoObject.getCipher();
        }

        @P
        public static Mac e(@N BiometricPrompt.CryptoObject cryptoObject) {
            return cryptoObject.getMac();
        }

        @P
        public static Signature f(@N BiometricPrompt.CryptoObject cryptoObject) {
            return cryptoObject.getSignature();
        }
    }

    /* compiled from: CryptoObjectUtils.java */
    @X
    public static class c {
        private c() {
        }

        @N
        public static BiometricPrompt.CryptoObject a(@N IdentityCredential identityCredential) {
            return new BiometricPrompt.CryptoObject(identityCredential);
        }

        @P
        public static IdentityCredential b(@N BiometricPrompt.CryptoObject cryptoObject) {
            return cryptoObject.getIdentityCredential();
        }
    }

    @P
    @X
    public static BiometricPrompt.CryptoObject a(@P p.c cVar) {
        IdentityCredential identityCredential;
        if (cVar == null) {
            return null;
        }
        Cipher cipher = cVar.f22773b;
        if (cipher != null) {
            return b.b(cipher);
        }
        Signature signature = cVar.f22772a;
        if (signature != null) {
            return b.a(signature);
        }
        Mac mac = cVar.f22774c;
        if (mac != null) {
            return b.c(mac);
        }
        if (Build.VERSION.SDK_INT < 30 || (identityCredential = cVar.f22775d) == null) {
            return null;
        }
        return c.a(identityCredential);
    }
}
