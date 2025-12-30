package androidx.camera.core;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: SingleCloseImageProxy.java */
@j.X
/* loaded from: classes.dex */
final class A0 extends O {

    /* renamed from: e, reason: collision with root package name */
    public final AtomicBoolean f23548e;

    public A0(InterfaceC20120j0 interfaceC20120j0) {
        super(interfaceC20120j0);
        this.f23548e = new AtomicBoolean(false);
    }

    @Override // androidx.camera.core.O, java.lang.AutoCloseable
    public final void close() {
        if (this.f23548e.getAndSet(true)) {
            return;
        }
        super.close();
    }
}
