package androidx.camera.core.imagecapture;

/* compiled from: SingleBundlingNode.java */
/* loaded from: classes.dex */
class I implements androidx.camera.core.impl.utils.futures.c<Void> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ B f23793a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ J f23794b;

    public I(J j12, B b12) {
        this.f23794b = j12;
        this.f23793a = b12;
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final void a(@j.N Throwable th2) {
        androidx.camera.core.impl.utils.t.a();
        J j12 = this.f23794b;
        if (this.f23793a == j12.f23795a) {
            j12.f23795a = null;
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.c
    public final /* bridge */ /* synthetic */ void onSuccess(@j.P Void r12) {
    }
}
