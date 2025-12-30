package androidx.camera.extensions.internal.compat.quirk;

import android.os.Build;
import android.util.Pair;
import androidx.camera.core.impl.w0;
import com.adjust.sdk.Constants;
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
    public static final w0 f24627a;

    static {
        boolean zContains;
        ArrayList arrayList = new ArrayList();
        String str = Build.BRAND;
        if ((Constants.REFERRER_API_GOOGLE.equalsIgnoreCase(str) && "redfin".equalsIgnoreCase(Build.DEVICE)) || "motorola".equalsIgnoreCase(str)) {
            arrayList.add(new d());
        }
        if (str.equalsIgnoreCase("SAMSUNG")) {
            arrayList.add(new a());
        }
        if (str.equalsIgnoreCase("SAMSUNG")) {
            arrayList.add(new f());
        }
        HashSet hashSet = g.f24629a;
        Locale locale = Locale.US;
        if (hashSet.contains(Pair.create(str.toLowerCase(locale), Build.DEVICE.toLowerCase(locale)))) {
            arrayList.add(new g());
        }
        HashSet hashSet2 = e.f24628a;
        if (Constants.REFERRER_API_SAMSUNG.equalsIgnoreCase(str)) {
            zContains = e.f24628a.contains(Build.MODEL.toUpperCase(locale));
        } else {
            zContains = false;
        }
        if (zContains) {
            arrayList.add(new e());
        }
        f24627a = new w0(arrayList);
    }
}
