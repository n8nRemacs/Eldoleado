package androidx.camera.core.internal.compat.quirk;

import android.os.Build;
import android.util.Pair;
import androidx.camera.core.impl.w0;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

/* compiled from: DeviceQuirks.java */
@X
/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    @N
    public static final w0 f24337a;

    static {
        ArrayList arrayList = new ArrayList();
        String str = Build.BRAND;
        if (("HUAWEI".equalsIgnoreCase(str) && "SNE-LX1".equalsIgnoreCase(Build.MODEL)) || ("HONOR".equalsIgnoreCase(str) && "STK-LX1".equalsIgnoreCase(Build.MODEL))) {
            arrayList.add(new d());
        } else {
            String str2 = Build.FINGERPRINT;
            if (!str2.startsWith("generic") && !str2.startsWith("unknown")) {
                String str3 = Build.MODEL;
                if (!str3.contains("google_sdk") && !str3.contains("Emulator") && !str3.contains("Cuttlefish") && !str3.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion") && ((!str.startsWith("generic") || !Build.DEVICE.startsWith("generic")) && !Build.PRODUCT.equals("google_sdk"))) {
                    Build.HARDWARE.contains("ranchu");
                }
            }
        }
        arrayList.add(new j());
        HashSet hashSet = a.f24336a;
        Locale locale = Locale.US;
        String upperCase = str.toUpperCase(locale);
        String str4 = Build.MODEL;
        if (a.f24336a.contains(Pair.create(upperCase, str4.toUpperCase(locale)))) {
            arrayList.add(new a());
        }
        if (g.f24340a.contains(str4.toUpperCase(locale))) {
            arrayList.add(new g());
        }
        if (f.f24339a.contains(str4.toUpperCase(locale))) {
            arrayList.add(new f());
        }
        HashSet hashSet2 = e.f24338a;
        if ("Samsung".equalsIgnoreCase(str) && e.f24338a.contains(Build.DEVICE.toUpperCase(locale))) {
            arrayList.add(new e());
        }
        f24337a = new w0(arrayList);
    }
}
