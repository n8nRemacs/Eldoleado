package androidx.camera.core;

import androidx.camera.core.T;
import androidx.camera.core.impl.InterfaceC20092f0;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class S implements T.a, InterfaceC20092f0.a {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f23652b;

    @Override // androidx.camera.core.impl.InterfaceC20092f0.a
    public void a(InterfaceC20092f0 interfaceC20092f0) {
        r0 r0Var = (r0) this.f23652b;
        synchronized (r0Var.f24518a) {
            r0Var.f24520c++;
        }
        r0Var.j(interfaceC20092f0);
    }

    @Override // androidx.camera.core.T.a
    public void d(z0 z0Var) {
        T.d dVar = T.f23653s;
        ((T.a) this.f23652b).d(z0Var);
    }
}
