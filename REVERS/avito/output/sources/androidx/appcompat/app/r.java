package androidx.appcompat.app;

import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.view.Surface;
import androidx.camera.core.K0;
import androidx.camera.core.v0;
import androidx.camera.video.internal.encoder.InterfaceC20172n;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class r implements v0.c, InterfaceC20172n {
    public static StringBuilder A(String str, String str2, String str3) {
        StringBuilder sb2 = new StringBuilder(str);
        sb2.append(str2);
        sb2.append(str3);
        return sb2;
    }

    public static void B(int i12, androidx.compose.runtime.A a12, int i13, Y41.p pVar) {
        a12.H(Integer.valueOf(i12));
        a12.a(Integer.valueOf(i13), pVar);
    }

    public static void C(Class cls, StringBuilder sb2, String str) {
        sb2.append(cls.getSimpleName());
        sb2.append(str);
    }

    public static double b(double d12, double d13, double d14) {
        return (Math.sin(d12) * d13) + d14;
    }

    public static float c(float f12, float f13, float f14, float f15) {
        return ((f12 - f13) * f14) + f15;
    }

    public static int d(float f12, int i12, int i13) {
        return (Float.hashCode(f12) + i12) * i13;
    }

    public static int e(int i12, int i13, int i14) {
        return (Integer.hashCode(i12) + i13) * i14;
    }

    public static int f(int i12, int i13, int i14, int i15) {
        return ((i12 - i13) / i14) + i15;
    }

    public static int g(int i12, int i13, long j12) {
        return (Long.hashCode(j12) + i12) * i13;
    }

    public static int h(int i12, int i13, Y41.a aVar) {
        return (aVar.hashCode() + i12) * i13;
    }

    public static int i(int i12, int i13, boolean z12) {
        return (Boolean.hashCode(z12) + i12) * i13;
    }

    public static Object j(int i12, ArrayList arrayList) {
        return arrayList.get(arrayList.size() - i12);
    }

    public static String k(char c12, float f12, StringBuilder sb2) {
        sb2.append(f12);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String l(int i12, int i13, String str, String str2, String str3) {
        return str + i12 + str2 + i13 + str3;
    }

    public static String m(int i12, StringBuilder sb2) {
        sb2.append(Integer.toHexString(i12));
        return sb2.toString();
    }

    public static String n(Uri uri, String str) {
        return str + uri;
    }

    public static String o(Object obj, String str, StringBuilder sb2) {
        sb2.append(obj);
        sb2.append(str);
        return sb2.toString();
    }

    public static String p(String str, int i12, char c12) {
        return str + i12 + c12;
    }

    public static String q(String str, String str2) {
        return str + str2;
    }

    public static String r(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String s(String str, StringBuilder sb2, boolean z12) {
        sb2.append(z12);
        sb2.append(str);
        return sb2.toString();
    }

    public static String t(StringBuilder sb2, int i12, char c12) {
        sb2.append(i12);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String u(StringBuilder sb2, long j12, char c12) {
        sb2.append(j12);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String v(StringBuilder sb2, Y41.a aVar, char c12) {
        sb2.append(aVar);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String w(StringBuilder sb2, Map map, char c12) {
        sb2.append(map);
        sb2.append(c12);
        return sb2.toString();
    }

    public static String x(StringBuilder sb2, boolean z12, char c12) {
        sb2.append(z12);
        sb2.append(c12);
        return sb2.toString();
    }

    public static StringBuilder y(int i12, int i13, String str, String str2, String str3) {
        StringBuilder sb2 = new StringBuilder(str);
        sb2.append(i12);
        sb2.append(str2);
        sb2.append(i13);
        sb2.append(str3);
        return sb2;
    }

    public static StringBuilder z(String str, String str2) {
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(str2);
        return sb2;
    }

    @Override // androidx.camera.core.v0.c
    public void a(K0 k02) {
        SurfaceTexture surfaceTexture = new SurfaceTexture(0);
        surfaceTexture.setDefaultBufferSize(k02.f23590b.getWidth(), k02.f23590b.getHeight());
        surfaceTexture.detachFromGLContext();
        Surface surface = new Surface(surfaceTexture);
        k02.a(surface, androidx.camera.core.impl.utils.executor.c.a(), new androidx.camera.core.internal.d(0, surface, surfaceTexture));
    }
}
