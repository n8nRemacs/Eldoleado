package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import androidx.camera.core.impl.w0;
import com.adjust.sdk.Constants;
import j.N;
import j.X;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: CameraQuirks.java */
@X
/* renamed from: androidx.camera.camera2.internal.compat.quirk.g, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C19996g {
    @N
    public static w0 a(@N androidx.camera.camera2.internal.compat.n nVar) {
        ArrayList arrayList = new ArrayList();
        CameraCharacteristics.Key key = CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL;
        Integer num = (Integer) nVar.a(key);
        if (num != null && num.intValue() == 2) {
            arrayList.add(new C19990a(nVar));
        }
        HashSet hashSet = z.f23233a;
        String str = Build.DEVICE;
        Locale locale = Locale.US;
        if (hashSet.contains(str.toLowerCase(locale))) {
            arrayList.add(new z());
        }
        Integer num2 = (Integer) nVar.a(key);
        if (num2 != null && num2.intValue() == 2) {
            arrayList.add(new C19994e(nVar));
        }
        List<String> list = w.f23230a;
        String str2 = Build.MODEL;
        if (w.f23230a.contains(str2.toUpperCase(locale)) && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
            arrayList.add(new w());
        }
        List<String> list2 = C19995f.f23214a;
        if (C19995f.f23214a.contains(str2.toUpperCase(locale)) && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
            arrayList.add(new C19995f());
        }
        String str3 = Build.BRAND;
        if (("motorola".equalsIgnoreCase(str3) && "MotoG3".equalsIgnoreCase(str2)) || ((Constants.REFERRER_API_SAMSUNG.equalsIgnoreCase(str3) && "SM-G532F".equalsIgnoreCase(str2)) || (Constants.REFERRER_API_SAMSUNG.equalsIgnoreCase(str3) && "SM-J700F".equalsIgnoreCase(str2)))) {
            arrayList.add(new J());
        }
        Iterator<String> it = s.f23225a.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (Build.MODEL.toUpperCase(Locale.US).startsWith(it.next())) {
                if (((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
                    arrayList.add(new s());
                }
            }
        }
        if (Build.BRAND.equalsIgnoreCase("SAMSUNG") && Build.VERSION.SDK_INT < 33 && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
            arrayList.add(new C19991b());
        }
        CameraCharacteristics.Key key2 = CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL;
        Integer num3 = (Integer) nVar.a(key2);
        if (num3 != null && num3.intValue() == 2) {
            arrayList.add(new C19999j());
        }
        Integer num4 = (Integer) nVar.a(key2);
        if (num4 != null && num4.intValue() == 2) {
            arrayList.add(new B());
        }
        Integer num5 = (Integer) nVar.a(key2);
        if (num5 != null && num5.intValue() == 2) {
            arrayList.add(new C19998i());
        }
        List<String> list3 = u.f23227a;
        String str4 = Build.MODEL;
        Locale locale2 = Locale.US;
        boolean z12 = u.f23228b.contains(str4.toLowerCase(locale2)) && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        boolean zContains = u.f23227a.contains(str4.toLowerCase(locale2));
        if (z12 || zContains) {
            arrayList.add(new u());
        }
        List<String> list4 = x.f23231a;
        if (x.f23231a.contains(str4.toLowerCase(locale2)) && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
            arrayList.add(new x());
        }
        List<String> list5 = t.f23226a;
        if (t.f23226a.contains(str4.toLowerCase(locale2)) && ((Integer) nVar.a(CameraCharacteristics.LENS_FACING)).intValue() == 0) {
            arrayList.add(new t());
        }
        return new w0(arrayList);
    }
}
