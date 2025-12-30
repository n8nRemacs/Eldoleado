package androidx.camera.core.impl;

import android.util.Size;
import java.util.Map;

/* compiled from: SurfaceSizeDefinition.java */
@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class I0 {
    @j.N
    public static I0 a(@j.N Size size, @j.N Map<Integer, Size> map, @j.N Size size2, @j.N Map<Integer, Size> map2, @j.N Size size3, @j.N Map<Integer, Size> map3, @j.N Map<Integer, Size> map4) {
        return new C20108o(size, map, size2, map2, size3, map3, map4);
    }

    @j.N
    public abstract Size b();

    @j.N
    public abstract Map<Integer, Size> c();

    @j.N
    public abstract Size d();

    @j.N
    public abstract Size e();

    @j.N
    public abstract Map<Integer, Size> f();

    @j.N
    public abstract Map<Integer, Size> g();

    @j.N
    public abstract Map<Integer, Size> h();
}
