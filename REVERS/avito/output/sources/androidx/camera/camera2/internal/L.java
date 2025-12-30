package androidx.camera.camera2.internal;

import android.content.Context;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.InterfaceC20116x;
import androidx.camera.core.impl.SurfaceConfig;
import java.util.HashMap;
import java.util.Set;

/* compiled from: Camera2DeviceSurfaceManager.java */
@j.X
/* loaded from: classes.dex */
public final class L implements InterfaceC20116x {

    /* renamed from: a, reason: collision with root package name */
    public final HashMap f23009a;

    /* renamed from: b, reason: collision with root package name */
    public final a f23010b;

    /* compiled from: Camera2DeviceSurfaceManager.java */
    public class a implements InterfaceC20006f {
    }

    public L() {
        throw null;
    }

    @RestrictTo
    public L(@j.N Context context, @j.P Object obj, @j.N Set<String> set) {
        a aVar = new a();
        this.f23009a = new HashMap();
        this.f23010b = aVar;
        androidx.camera.camera2.internal.compat.w wVarA = obj instanceof androidx.camera.camera2.internal.compat.w ? (androidx.camera.camera2.internal.compat.w) obj : androidx.camera.camera2.internal.compat.w.a(context, androidx.camera.core.impl.utils.o.a());
        context.getClass();
        for (String str : set) {
            this.f23009a.put(str, new J0(context, str, wVarA, this.f23010b));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x01d4, code lost:
    
        if (r5.contains(r8) != false) goto L63;
     */
    /* JADX WARN: Removed duplicated region for block: B:308:0x07e0  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x0a6a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x0c39  */
    /* JADX WARN: Removed duplicated region for block: B:554:0x0dc2  */
    /* JADX WARN: Removed duplicated region for block: B:674:0x082d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0257  */
    @Override // androidx.camera.core.impl.InterfaceC20116x
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final android.util.Pair a(int r38, @j.N java.lang.String r39, @j.N java.util.ArrayList r40, @j.N java.util.HashMap r41) {
        /*
            Method dump skipped, instructions count: 3756
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.L.a(int, java.lang.String, java.util.ArrayList, java.util.HashMap):android.util.Pair");
    }

    @Override // androidx.camera.core.impl.InterfaceC20116x
    @j.P
    public final SurfaceConfig b(int i12, @j.N String str, int i13, @j.N Size size) {
        J0 j02 = (J0) this.f23009a.get(str);
        if (j02 != null) {
            return SurfaceConfig.f(i12, i13, size, j02.h(i13));
        }
        return null;
    }
}
