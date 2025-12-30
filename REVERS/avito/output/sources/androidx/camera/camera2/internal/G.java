package androidx.camera.camera2.internal;

import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.internal.D;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.internal.utils.b;
import java.util.ArrayList;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class G implements D.e.a, b.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f22980a;

    public static androidx.camera.core.impl.H0 b(SurfaceConfig.ConfigType configType, SurfaceConfig.ConfigSize configSize, androidx.camera.core.impl.H0 h02, ArrayList arrayList, androidx.camera.core.impl.H0 h03) {
        h02.a(SurfaceConfig.a(configType, configSize));
        arrayList.add(h03);
        return new androidx.camera.core.impl.H0();
    }

    public static androidx.camera.core.impl.H0 c(ArrayList arrayList, androidx.camera.core.impl.H0 h02) {
        arrayList.add(h02);
        return new androidx.camera.core.impl.H0();
    }

    public static String d(float f12, String str, StringBuilder sb2) {
        sb2.append(f12);
        sb2.append(str);
        return sb2.toString();
    }

    public static String e(int i12, String str, String str2) {
        return str + i12 + str2;
    }

    public static String f(String str, String str2) {
        return str + str2;
    }

    public static String g(StringBuilder sb2, String str, String str2, String str3) {
        sb2.append(str);
        sb2.append(str2);
        sb2.append(str3);
        return sb2.toString();
    }

    public static String h(StringBuilder sb2, String str, String str2, String str3, String str4) {
        sb2.append(str);
        sb2.append(str2);
        sb2.append(str3);
        sb2.append(str4);
        return sb2.toString();
    }

    public static String i(Throwable th2, StringBuilder sb2) {
        sb2.append(th2.getMessage());
        return sb2.toString();
    }

    public static StringBuilder j(int i12, String str, String str2) {
        StringBuilder sb2 = new StringBuilder(str);
        sb2.append(i12);
        sb2.append(str2);
        return sb2;
    }

    public static void k(SurfaceConfig.ConfigType configType, SurfaceConfig.ConfigSize configSize, androidx.camera.core.impl.H0 h02, SurfaceConfig.ConfigType configType2, SurfaceConfig.ConfigSize configSize2) {
        h02.a(SurfaceConfig.a(configType, configSize));
        h02.a(SurfaceConfig.a(configType2, configSize2));
    }

    @Override // androidx.camera.camera2.internal.D.e.a
    public boolean a(TotalCaptureResult totalCaptureResult) {
        switch (this.f22980a) {
            case 0:
                int i12 = D.c.f22950k;
                return D.a(totalCaptureResult, false);
            default:
                int i13 = D.f.f22966f;
                return D.a(totalCaptureResult, true);
        }
    }
}
