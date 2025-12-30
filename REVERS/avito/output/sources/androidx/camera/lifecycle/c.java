package androidx.camera.lifecycle;

import androidx.camera.core.CameraControl;
import androidx.camera.core.InterfaceC20127n;
import androidx.camera.core.InterfaceC20142t;
import androidx.camera.core.N0;
import androidx.camera.core.impl.A0;
import androidx.camera.core.impl.C0;
import androidx.camera.core.impl.C20115w;
import androidx.camera.core.impl.InterfaceC20112t;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.view.InterfaceC22982O;
import androidx.view.InterfaceC22983P;
import androidx.view.InterfaceC23042i0;
import androidx.view.Lifecycle;
import j.B;
import j.N;
import j.P;
import j.X;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* compiled from: LifecycleCamera.java */
@X
/* loaded from: classes.dex */
final class c implements InterfaceC22982O, InterfaceC20127n {

    /* renamed from: c, reason: collision with root package name */
    @B
    public final InterfaceC22983P f24659c;

    /* renamed from: d, reason: collision with root package name */
    public final CameraUseCaseAdapter f24660d;

    /* renamed from: b, reason: collision with root package name */
    public final Object f24658b = new Object();

    /* renamed from: e, reason: collision with root package name */
    @B
    public boolean f24661e = false;

    public c(InterfaceC22983P interfaceC22983P, CameraUseCaseAdapter cameraUseCaseAdapter) {
        this.f24659c = interfaceC22983P;
        this.f24660d = cameraUseCaseAdapter;
        if (interfaceC22983P.getLifecycle().getF46705d().a(Lifecycle.State.f46682e)) {
            cameraUseCaseAdapter.b();
        } else {
            cameraUseCaseAdapter.q();
        }
        interfaceC22983P.getLifecycle().a(this);
    }

    @Override // androidx.camera.core.InterfaceC20127n
    @N
    public final CameraControl a() {
        return this.f24660d.f24326q;
    }

    public final void b(List list) {
        synchronized (this.f24658b) {
            CameraUseCaseAdapter cameraUseCaseAdapter = this.f24660d;
            synchronized (cameraUseCaseAdapter.f24321l) {
                LinkedHashSet linkedHashSet = new LinkedHashSet(cameraUseCaseAdapter.f24315f);
                linkedHashSet.addAll(list);
                try {
                    cameraUseCaseAdapter.x(linkedHashSet, false);
                } catch (IllegalArgumentException e12) {
                    throw new CameraUseCaseAdapter.CameraException(e12.getMessage());
                }
            }
        }
    }

    public final void e(@P InterfaceC20112t interfaceC20112t) {
        CameraUseCaseAdapter cameraUseCaseAdapter = this.f24660d;
        synchronized (cameraUseCaseAdapter.f24321l) {
            if (interfaceC20112t == null) {
                try {
                    interfaceC20112t = C20115w.f24305a;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            if (!cameraUseCaseAdapter.f24315f.isEmpty() && !cameraUseCaseAdapter.f24320k.J().equals(interfaceC20112t.J())) {
                throw new IllegalStateException("Need to unbind all use cases before binding with extension enabled");
            }
            cameraUseCaseAdapter.f24320k = interfaceC20112t;
            C0 c0W = interfaceC20112t.w();
            if (c0W != null) {
                Set<Integer> setC = c0W.c();
                A0 a02 = cameraUseCaseAdapter.f24326q;
                a02.f23871d = true;
                a02.f23872e = setC;
            } else {
                A0 a03 = cameraUseCaseAdapter.f24326q;
                a03.f23871d = false;
                a03.f23872e = null;
            }
            cameraUseCaseAdapter.f24311b.e(cameraUseCaseAdapter.f24320k);
        }
    }

    @Override // androidx.camera.core.InterfaceC20127n
    @N
    public final InterfaceC20142t getCameraInfo() {
        return this.f24660d.f24327r;
    }

    public final InterfaceC22983P k() {
        InterfaceC22983P interfaceC22983P;
        synchronized (this.f24658b) {
            interfaceC22983P = this.f24659c;
        }
        return interfaceC22983P;
    }

    @N
    public final List<N0> n() {
        List<N0> listUnmodifiableList;
        synchronized (this.f24658b) {
            listUnmodifiableList = Collections.unmodifiableList(this.f24660d.t());
        }
        return listUnmodifiableList;
    }

    public final void o() {
        synchronized (this.f24658b) {
            try {
                if (this.f24661e) {
                    return;
                }
                onStop(this.f24659c);
                this.f24661e = true;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @InterfaceC23042i0(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24658b) {
            CameraUseCaseAdapter cameraUseCaseAdapter = this.f24660d;
            cameraUseCaseAdapter.v((ArrayList) cameraUseCaseAdapter.t());
        }
    }

    @InterfaceC23042i0(Lifecycle.Event.ON_PAUSE)
    public void onPause(InterfaceC22983P interfaceC22983P) {
        this.f24660d.f24311b.m(false);
    }

    @InterfaceC23042i0(Lifecycle.Event.ON_RESUME)
    public void onResume(InterfaceC22983P interfaceC22983P) {
        this.f24660d.f24311b.m(true);
    }

    @InterfaceC23042i0(Lifecycle.Event.ON_START)
    public void onStart(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24658b) {
            try {
                if (!this.f24661e) {
                    this.f24660d.b();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @InterfaceC23042i0(Lifecycle.Event.ON_STOP)
    public void onStop(InterfaceC22983P interfaceC22983P) {
        synchronized (this.f24658b) {
            try {
                if (!this.f24661e) {
                    this.f24660d.q();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void p() {
        synchronized (this.f24658b) {
            try {
                if (this.f24661e) {
                    this.f24661e = false;
                    if (this.f24659c.getLifecycle().getF46705d().a(Lifecycle.State.f46682e)) {
                        onStart(this.f24659c);
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
