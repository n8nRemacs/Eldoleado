package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.impl.C20105m;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.UseCaseConfigFactory;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AttachedSurfaceInfo.java */
@VY0.c
@j.X
/* renamed from: androidx.camera.core.impl.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20081a {
    @j.N
    public static AbstractC20081a a(@j.N SurfaceConfig surfaceConfig, int i12, @j.N Size size, @j.N androidx.camera.core.F f12, @j.N ArrayList arrayList, @j.P Config config, @j.P Range range) {
        return new C20083b(surfaceConfig, i12, size, f12, arrayList, config, range);
    }

    @j.N
    public abstract List<UseCaseConfigFactory.CaptureType> b();

    @j.N
    public abstract androidx.camera.core.F c();

    public abstract int d();

    @j.P
    public abstract Config e();

    @j.N
    public abstract Size f();

    @j.N
    public abstract SurfaceConfig g();

    @j.P
    public abstract Range<Integer> h();

    @j.N
    public final G0 i(@j.N androidx.camera.camera2.impl.b bVar) {
        G0.a aVarA = G0.a(f());
        aVarA.b(c());
        ((C20105m.b) aVarA).f24159d = bVar;
        if (h() != null) {
            aVarA.c(h());
        }
        return aVarA.a();
    }
}
