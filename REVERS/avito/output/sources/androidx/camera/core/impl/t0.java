package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;

/* compiled from: OutputSurface.java */
@VY0.c
@j.X
/* loaded from: classes.dex */
public abstract class t0 {
    @j.N
    public static t0 a(@j.N Surface surface, @j.N Size size, int i12) {
        return new C20099j(surface, size, i12);
    }

    public abstract int b();

    @j.N
    public abstract Size c();

    @j.N
    public abstract Surface d();
}
