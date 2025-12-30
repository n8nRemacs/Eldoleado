package androidx.camera.core;

import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.DeferrableSurface;

/* compiled from: SurfaceRequest.java */
/* loaded from: classes.dex */
class I0 extends DeferrableSurface {

    /* renamed from: o, reason: collision with root package name */
    public final /* synthetic */ K0 f23578o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public I0(K0 k02, Size size) {
        super(size, 34);
        this.f23578o = k02;
    }

    @Override // androidx.camera.core.impl.DeferrableSurface
    @j.N
    public final com.google.common.util.concurrent.D0<Surface> f() {
        return this.f23578o.f23594f;
    }
}
