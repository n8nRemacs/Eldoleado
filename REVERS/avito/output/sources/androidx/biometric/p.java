package androidx.biometric;

import android.content.res.Resources;
import android.os.Build;
import android.security.identity.IdentityCredential;
import android.text.TextUtils;
import androidx.biometric.BiometricFragment;
import androidx.fragment.app.ActivityC22955m;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.H;
import androidx.view.InterfaceC22982O;
import androidx.view.InterfaceC23042i0;
import androidx.view.Lifecycle;
import com.avito.android.R;
import j.N;
import j.P;
import j.X;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* compiled from: BiometricPrompt.java */
/* loaded from: classes.dex */
public class p {

    /* renamed from: a, reason: collision with root package name */
    @P
    public FragmentManager f22769a;

    /* compiled from: BiometricPrompt.java */
    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public final c f22770a;

        /* renamed from: b, reason: collision with root package name */
        public final int f22771b;

        public b(c cVar, int i12) {
            this.f22770a = cVar;
            this.f22771b = i12;
        }
    }

    /* compiled from: BiometricPrompt.java */
    public static class d {

        /* renamed from: a, reason: collision with root package name */
        @N
        public final String f22776a;

        /* renamed from: b, reason: collision with root package name */
        @P
        public final String f22777b;

        /* renamed from: c, reason: collision with root package name */
        @P
        public final String f22778c;

        /* renamed from: d, reason: collision with root package name */
        @P
        public final String f22779d;

        /* renamed from: e, reason: collision with root package name */
        public final boolean f22780e;

        /* renamed from: f, reason: collision with root package name */
        public final int f22781f;

        /* compiled from: BiometricPrompt.java */
        public static class a {

            /* renamed from: a, reason: collision with root package name */
            @P
            public String f22782a = null;

            /* renamed from: b, reason: collision with root package name */
            @P
            public String f22783b = null;

            /* renamed from: c, reason: collision with root package name */
            @P
            public String f22784c = null;

            /* renamed from: d, reason: collision with root package name */
            @P
            public String f22785d = null;

            /* renamed from: e, reason: collision with root package name */
            public boolean f22786e = true;

            /* renamed from: f, reason: collision with root package name */
            public int f22787f = 0;

            @N
            public final d a() {
                if (TextUtils.isEmpty(this.f22782a)) {
                    throw new IllegalArgumentException("Title must be set and non-empty.");
                }
                if (!androidx.biometric.c.c(this.f22787f)) {
                    StringBuilder sb2 = new StringBuilder("Authenticator combination is unsupported on API ");
                    sb2.append(Build.VERSION.SDK_INT);
                    sb2.append(": ");
                    int i12 = this.f22787f;
                    sb2.append(i12 != 15 ? i12 != 255 ? i12 != 32768 ? i12 != 32783 ? i12 != 33023 ? String.valueOf(i12) : "BIOMETRIC_WEAK | DEVICE_CREDENTIAL" : "BIOMETRIC_STRONG | DEVICE_CREDENTIAL" : "DEVICE_CREDENTIAL" : "BIOMETRIC_WEAK" : "BIOMETRIC_STRONG");
                    throw new IllegalArgumentException(sb2.toString());
                }
                int i13 = this.f22787f;
                boolean zB2 = i13 != 0 ? androidx.biometric.c.b(i13) : false;
                if (TextUtils.isEmpty(this.f22785d) && !zB2) {
                    throw new IllegalArgumentException("Negative text must be set and non-empty.");
                }
                if (TextUtils.isEmpty(this.f22785d) || !zB2) {
                    return new d(this.f22787f, this.f22782a, this.f22783b, this.f22784c, this.f22785d, this.f22786e);
                }
                throw new IllegalArgumentException("Negative text must not be set if device credential authentication is allowed.");
            }
        }

        public d(int i12, @N String str, @P String str2, @P String str3, @P String str4, boolean z12) {
            this.f22776a = str;
            this.f22777b = str2;
            this.f22778c = str3;
            this.f22779d = str4;
            this.f22780e = z12;
            this.f22781f = i12;
        }
    }

    /* compiled from: BiometricPrompt.java */
    public static class e implements InterfaceC22982O {
        @InterfaceC23042i0(Lifecycle.Event.ON_DESTROY)
        public void resetCallback() {
            throw null;
        }
    }

    public final void a(@N d dVar, @N c cVar) throws Resources.NotFoundException {
        int iA2 = androidx.biometric.c.a(dVar, cVar);
        if ((iA2 & 255) == 255) {
            throw new IllegalArgumentException("Crypto-based authentication is not supported for Class 2 (Weak) biometrics.");
        }
        if (Build.VERSION.SDK_INT < 30 && androidx.biometric.c.b(iA2)) {
            throw new IllegalArgumentException("Crypto-based authentication is not supported for device credential prior to API 30.");
        }
        FragmentManager fragmentManager = this.f22769a;
        if (fragmentManager == null || fragmentManager.V()) {
            return;
        }
        FragmentManager fragmentManager2 = this.f22769a;
        BiometricFragment biometricFragment = (BiometricFragment) fragmentManager2.H("androidx.biometric.BiometricFragment");
        if (biometricFragment == null) {
            biometricFragment = new BiometricFragment();
            H hE2 = fragmentManager2.e();
            hE2.j(0, biometricFragment, "androidx.biometric.BiometricFragment", 1);
            hE2.f();
            fragmentManager2.E();
        }
        ActivityC22955m activityC22955mL1 = biometricFragment.l1();
        if (activityC22955mL1 == null) {
            return;
        }
        biometricFragment.f22728g0.f22790K = dVar;
        androidx.biometric.c.a(dVar, cVar);
        biometricFragment.f22728g0.f22791L = cVar;
        if (biometricFragment.d5()) {
            biometricFragment.f22728g0.f22795P = biometricFragment.getString(R.string.confirm_device_credential_password);
        } else {
            biometricFragment.f22728g0.f22795P = null;
        }
        if (biometricFragment.d5() && o.c(activityC22955mL1).a(255) != 0) {
            biometricFragment.f22728g0.f22798S = true;
            biometricFragment.f5();
        } else if (biometricFragment.f22728g0.f22800U) {
            biometricFragment.f22727f0.postDelayed(new BiometricFragment.g(biometricFragment), 600L);
        } else {
            biometricFragment.j5();
        }
    }

    /* compiled from: BiometricPrompt.java */
    public static class c {

        /* renamed from: a, reason: collision with root package name */
        @P
        public final Signature f22772a;

        /* renamed from: b, reason: collision with root package name */
        @P
        public final Cipher f22773b;

        /* renamed from: c, reason: collision with root package name */
        @P
        public final Mac f22774c;

        /* renamed from: d, reason: collision with root package name */
        @P
        public final IdentityCredential f22775d;

        public c(@N Signature signature) {
            this.f22772a = signature;
            this.f22773b = null;
            this.f22774c = null;
            this.f22775d = null;
        }

        public c(@N Cipher cipher) {
            this.f22772a = null;
            this.f22773b = cipher;
            this.f22774c = null;
            this.f22775d = null;
        }

        public c(@N Mac mac) {
            this.f22772a = null;
            this.f22773b = null;
            this.f22774c = mac;
            this.f22775d = null;
        }

        @X
        public c(@N IdentityCredential identityCredential) {
            this.f22772a = null;
            this.f22773b = null;
            this.f22774c = null;
            this.f22775d = identityCredential;
        }
    }

    /* compiled from: BiometricPrompt.java */
    public static abstract class a {
        public void b(@N b bVar) {
        }

        public void a(int i12, @N CharSequence charSequence) {
        }
    }
}
