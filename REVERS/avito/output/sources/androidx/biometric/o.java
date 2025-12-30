package androidx.biometric;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import androidx.biometric.y;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.lang.reflect.Method;

/* compiled from: BiometricManager.java */
/* loaded from: classes.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    @N
    public final d f22765a;

    /* renamed from: b, reason: collision with root package name */
    @P
    public final BiometricManager f22766b;

    /* renamed from: c, reason: collision with root package name */
    @P
    public final androidx.core.hardware.fingerprint.b f22767c;

    /* compiled from: BiometricManager.java */
    @X
    public static class a {
        private a() {
        }

        public static int a(@N BiometricManager biometricManager) {
            return biometricManager.canAuthenticate();
        }

        @P
        public static BiometricManager b(@N Context context) {
            return (BiometricManager) context.getSystemService(BiometricManager.class);
        }

        @P
        public static Method c() {
            try {
                return BiometricManager.class.getMethod("canAuthenticate", BiometricPrompt.CryptoObject.class);
            } catch (NoSuchMethodException unused) {
                return null;
            }
        }
    }

    /* compiled from: BiometricManager.java */
    @X
    public static class b {
        private b() {
        }

        public static int a(@N BiometricManager biometricManager, int i12) {
            return biometricManager.canAuthenticate(i12);
        }
    }

    /* compiled from: BiometricManager.java */
    public interface c {
    }

    /* compiled from: BiometricManager.java */
    public static class d implements e {

        /* renamed from: a, reason: collision with root package name */
        @N
        public final Context f22768a;

        public d(@N Context context) {
            this.f22768a = context.getApplicationContext();
        }

        public final boolean a() {
            KeyguardManager keyguardManagerA = y.b.a(this.f22768a);
            if (keyguardManagerA == null) {
                return false;
            }
            return y.b.b(keyguardManagerA);
        }
    }

    /* compiled from: BiometricManager.java */
    @k0
    public interface e {
    }

    @k0
    public o(@N d dVar) {
        this.f22765a = dVar;
        int i12 = Build.VERSION.SDK_INT;
        Context context = dVar.f22768a;
        this.f22766b = i12 >= 29 ? a.b(context) : null;
        this.f22767c = i12 <= 29 ? new androidx.core.hardware.fingerprint.b(context) : null;
    }

    @N
    public static o c(@N Context context) {
        return new o(new d(context));
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int a(int r13) throws java.lang.IllegalAccessException, javax.crypto.NoSuchPaddingException, android.content.res.Resources.NotFoundException, java.security.NoSuchAlgorithmException, java.io.IOException, java.security.InvalidKeyException, java.security.KeyStoreException, java.security.cert.CertificateException, java.lang.IllegalArgumentException, java.security.NoSuchProviderException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 297
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.biometric.o.a(int):int");
    }

    public final int b() {
        androidx.core.hardware.fingerprint.b bVar = this.f22767c;
        if (bVar == null) {
            return 1;
        }
        Context context = bVar.f44787a;
        FingerprintManager fingerprintManagerB = androidx.core.hardware.fingerprint.b.b(context);
        if (fingerprintManagerB == null || !fingerprintManagerB.isHardwareDetected()) {
            return 12;
        }
        FingerprintManager fingerprintManagerB2 = androidx.core.hardware.fingerprint.b.b(context);
        return (fingerprintManagerB2 == null || !fingerprintManagerB2.hasEnrolledFingerprints()) ? 11 : 0;
    }
}
