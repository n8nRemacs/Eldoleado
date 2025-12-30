package androidx.camera.core;

import androidx.camera.core.K0;
import androidx.concurrent.futures.b;

/* compiled from: SurfaceRequest.java */
/* loaded from: classes.dex */
class H0 implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ b.a f23576a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ com.google.common.util.concurrent.D0 f23577b;

    public H0(b.a aVar, com.google.common.util.concurrent.D0 d02) {
        this.f23576a = aVar;
        this.f23577b = d02;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        if (th2 instanceof K0.b) {
            androidx.core.util.z.g(null, this.f23577b.cancel(false));
        } else {
            androidx.core.util.z.g(null, this.f23576a.b(null));
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Void r22) {
        androidx.core.util.z.g(null, this.f23576a.b(null));
    }
}
