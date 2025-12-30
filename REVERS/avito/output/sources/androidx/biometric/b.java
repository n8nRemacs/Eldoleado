package androidx.biometric;

import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.BiometricPrompt$AuthenticationCallback;
import android.os.Build;
import android.security.identity.IdentityCredential;
import androidx.biometric.p;
import androidx.biometric.r;
import androidx.biometric.t;
import androidx.core.hardware.fingerprint.b;
import j.N;
import j.P;
import j.X;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* compiled from: AuthenticationCallbackProvider.java */
/* loaded from: classes.dex */
class b {

    /* renamed from: a, reason: collision with root package name */
    @P
    public BiometricPrompt$AuthenticationCallback f22746a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public b.AbstractC1732b f22747b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final r.a f22748c;

    /* compiled from: AuthenticationCallbackProvider.java */
    @X
    /* renamed from: androidx.biometric.b$b, reason: collision with other inner class name */
    public static class C1523b {
        private C1523b() {
        }

        public static int a(@N BiometricPrompt.AuthenticationResult authenticationResult) {
            return authenticationResult.getAuthenticationType();
        }
    }

    public b(@N r.a aVar) {
        this.f22748c = aVar;
    }

    /* compiled from: AuthenticationCallbackProvider.java */
    public static class c {
        public void b() {
        }

        public void c(@N p.b bVar) {
        }

        public void a(int i12, @P CharSequence charSequence) {
        }
    }

    /* compiled from: AuthenticationCallbackProvider.java */
    @X
    public static class a {
        private a() {
        }

        @N
        public static BiometricPrompt$AuthenticationCallback a(@N c cVar) {
            return new C1522a(cVar);
        }

        /* compiled from: AuthenticationCallbackProvider.java */
        /* renamed from: androidx.biometric.b$a$a, reason: collision with other inner class name */
        public class C1522a extends BiometricPrompt$AuthenticationCallback {

            /* renamed from: a, reason: collision with root package name */
            public final /* synthetic */ c f22749a;

            public C1522a(c cVar) {
                this.f22749a = cVar;
            }

            public void onAuthenticationError(int i12, CharSequence charSequence) {
                this.f22749a.a(i12, charSequence);
            }

            public void onAuthenticationFailed() {
                this.f22749a.b();
            }

            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
                BiometricPrompt.CryptoObject cryptoObject;
                IdentityCredential identityCredentialB;
                p.c cVar = null;
                if (authenticationResult != null && (cryptoObject = authenticationResult.getCryptoObject()) != null) {
                    Cipher cipherD = t.b.d(cryptoObject);
                    if (cipherD != null) {
                        cVar = new p.c(cipherD);
                    } else {
                        Signature signatureF = t.b.f(cryptoObject);
                        if (signatureF != null) {
                            cVar = new p.c(signatureF);
                        } else {
                            Mac macE = t.b.e(cryptoObject);
                            if (macE != null) {
                                cVar = new p.c(macE);
                            } else if (Build.VERSION.SDK_INT >= 30 && (identityCredentialB = t.c.b(cryptoObject)) != null) {
                                cVar = new p.c(identityCredentialB);
                            }
                        }
                    }
                }
                int i12 = Build.VERSION.SDK_INT;
                int iA2 = -1;
                if (i12 >= 30) {
                    if (authenticationResult != null) {
                        iA2 = C1523b.a(authenticationResult);
                    }
                } else if (i12 != 29) {
                    iA2 = 2;
                }
                this.f22749a.c(new p.b(cVar, iA2));
            }

            public void onAuthenticationHelp(int i12, CharSequence charSequence) {
            }
        }
    }
}
