package androidx.biometric;

import android.os.Build;
import androidx.biometric.p;
import j.N;
import j.P;

/* compiled from: AuthenticatorUtils.java */
/* loaded from: classes.dex */
class c {
    public static int a(@N p.d dVar, @P p.c cVar) {
        int i12 = dVar.f22781f;
        return i12 != 0 ? i12 : cVar != null ? 15 : 255;
    }

    public static boolean b(int i12) {
        return (i12 & 32768) != 0;
    }

    public static boolean c(int i12) {
        if (i12 == 15 || i12 == 255) {
            return true;
        }
        if (i12 == 32768) {
            return Build.VERSION.SDK_INT >= 30;
        }
        if (i12 != 32783) {
            return i12 == 33023 || i12 == 0;
        }
        int i13 = Build.VERSION.SDK_INT;
        return i13 < 28 || i13 > 29;
    }
}
