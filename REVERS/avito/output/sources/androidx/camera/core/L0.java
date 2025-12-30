package androidx.camera.core;

/* compiled from: SurfaceRequest.java */
/* loaded from: classes.dex */
class L0 implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ androidx.camera.core.processing.t f23605a;

    public L0(androidx.camera.core.processing.t tVar) {
        this.f23605a = tVar;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void onSuccess(@j.P Void r12) {
        this.f23605a.run();
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
    }
}
