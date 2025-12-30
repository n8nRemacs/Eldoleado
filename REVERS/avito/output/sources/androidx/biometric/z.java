package androidx.biometric;

import android.content.pm.PackageManager;
import j.N;
import j.X;

/* compiled from: PackageUtils.java */
/* loaded from: classes.dex */
class z {

    /* compiled from: PackageUtils.java */
    @X
    public static class a {
        private a() {
        }

        public static boolean a(@N PackageManager packageManager) {
            return packageManager.hasSystemFeature("android.hardware.fingerprint");
        }
    }
}
