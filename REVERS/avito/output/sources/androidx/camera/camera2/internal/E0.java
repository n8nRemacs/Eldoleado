package androidx.camera.camera2.internal;

import androidx.camera.core.impl.C0;

/* compiled from: ProcessingCaptureSession.java */
/* loaded from: classes.dex */
class E0 implements C0.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ androidx.camera.core.impl.H f22975a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0 f22976b;

    public E0(C0 c02, androidx.camera.core.impl.H h12) {
        this.f22976b = c02;
        this.f22975a = h12;
    }

    @Override // androidx.camera.core.impl.C0.a
    public final void a() {
        this.f22976b.f22915c.execute(new D0(this.f22975a, 0));
    }

    @Override // androidx.camera.core.impl.C0.a
    public final void b() {
        this.f22976b.f22915c.execute(new D0(this.f22975a, 1));
    }
}
