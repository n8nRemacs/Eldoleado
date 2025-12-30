package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.view.Surface;
import androidx.camera.camera2.internal.K0;
import androidx.camera.camera2.internal.S;
import androidx.camera.camera2.internal.compat.C19985a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: SynchronizedCaptureSessionStateCallbacks.java */
@j.X
/* loaded from: classes.dex */
final class R0 extends K0.a {

    /* renamed from: a, reason: collision with root package name */
    public final ArrayList f23064a;

    public R0(@j.N List<K0.a> list) {
        ArrayList arrayList = new ArrayList();
        this.f23064a = arrayList;
        arrayList.addAll(list);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void l(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).l(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    @j.X
    public final void m(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).m(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void n(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).n(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void o(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).o(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void p(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).p(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void q(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).q(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void r(@j.N K0 k02) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).r(k02);
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    @j.X
    public final void s(@j.N K0 k02, @j.N Surface surface) {
        Iterator it = this.f23064a.iterator();
        while (it.hasNext()) {
            ((K0.a) it.next()).s(k02, surface);
        }
    }

    /* compiled from: SynchronizedCaptureSessionStateCallbacks.java */
    @j.X
    public static class a extends K0.a {

        /* renamed from: a, reason: collision with root package name */
        @j.N
        public final CameraCaptureSession.StateCallback f23065a;

        public a(@j.N List<CameraCaptureSession.StateCallback> list) {
            this.f23065a = list.isEmpty() ? new S.b() : list.size() == 1 ? list.get(0) : new S.a(list);
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void l(@j.N K0 k02) {
            this.f23065a.onActive(k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        @j.X
        public final void m(@j.N K0 k02) {
            C19985a.d.b(this.f23065a, k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void n(@j.N K0 k02) {
            this.f23065a.onClosed(k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void o(@j.N K0 k02) {
            this.f23065a.onConfigureFailed(k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void p(@j.N K0 k02) {
            this.f23065a.onConfigured(k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void q(@j.N K0 k02) {
            this.f23065a.onReady(k02.g().c());
        }

        @Override // androidx.camera.camera2.internal.K0.a
        @j.X
        public final void s(@j.N K0 k02, @j.N Surface surface) {
            C19985a.b.a(this.f23065a, k02.g().c(), surface);
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void r(@j.N K0 k02) {
        }
    }
}
