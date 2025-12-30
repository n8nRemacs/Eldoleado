package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;

/* compiled from: ImmediateSurface.java */
@j.X
/* renamed from: androidx.camera.core.impl.g0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20094g0 extends DeferrableSurface {

    /* renamed from: o, reason: collision with root package name */
    public final Surface f24113o;

    public C20094g0(@j.N Surface surface, @j.N Size size, int i12) {
        super(size, i12);
        this.f24113o = surface;
    }

    @Override // androidx.camera.core.impl.DeferrableSurface
    @j.N
    public final com.google.common.util.concurrent.D0<Surface> f() {
        return androidx.camera.core.impl.utils.futures.f.g(this.f24113o);
    }

    public C20094g0(@j.N Surface surface) {
        this.f24113o = surface;
    }
}
