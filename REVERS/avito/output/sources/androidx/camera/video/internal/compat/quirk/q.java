package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.B;
import androidx.camera.video.A;
import j.N;
import j.X;
import java.util.Arrays;
import java.util.Locale;

/* compiled from: ReportedVideoQualityNotSupportedQuirk.java */
@X
/* loaded from: classes.dex */
public class q implements t {
    public static boolean c() {
        return "Huawei".equalsIgnoreCase(Build.BRAND) && "HMA-L29".equalsIgnoreCase(Build.MODEL);
    }

    public static boolean d() {
        return "Huawei".equalsIgnoreCase(Build.BRAND) && "LYA-AL00".equalsIgnoreCase(Build.MODEL);
    }

    public static boolean e() {
        return "Huawei".equalsIgnoreCase(Build.MANUFACTURER) && Arrays.asList("JNY-L21A", "JNY-L01A", "JNY-L21B", "JNY-L22A", "JNY-L02A", "JNY-L22B", "JNY-LX1").contains(Build.MODEL.toUpperCase(Locale.US));
    }

    @Override // androidx.camera.video.internal.compat.quirk.t
    public final boolean a() {
        return c() || d() || e();
    }

    @Override // androidx.camera.video.internal.compat.quirk.t
    public final boolean b(@N B b12, @N A a12) {
        if (c() || d()) {
            return a12 == A.f24683d;
        }
        if ("Vivo".equalsIgnoreCase(Build.BRAND) && "vivo 1820".equalsIgnoreCase(Build.MODEL)) {
            return a12 == A.f24681b || a12 == A.f24682c;
        }
        if (e()) {
            return b12.b() == 0 && (a12 == A.f24682c || a12 == A.f24681b);
        }
        return false;
    }
}
