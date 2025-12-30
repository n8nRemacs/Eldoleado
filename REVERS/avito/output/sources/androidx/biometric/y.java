package androidx.biometric;

import android.app.KeyguardManager;
import android.content.Context;
import j.N;
import j.P;
import j.X;

/* compiled from: KeyguardUtils.java */
/* loaded from: classes.dex */
class y {

    /* compiled from: KeyguardUtils.java */
    @X
    public static class a {
        private a() {
        }
    }

    /* compiled from: KeyguardUtils.java */
    @X
    public static class b {
        private b() {
        }

        @P
        public static KeyguardManager a(@N Context context) {
            return (KeyguardManager) context.getSystemService(KeyguardManager.class);
        }

        public static boolean b(@N KeyguardManager keyguardManager) {
            return keyguardManager.isDeviceSecure();
        }
    }
}
