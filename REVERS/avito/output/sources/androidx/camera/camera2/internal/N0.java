package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.view.Surface;
import androidx.camera.camera2.internal.K0;
import androidx.camera.camera2.internal.Q0;
import androidx.camera.camera2.internal.compat.C19986b;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.concurrent.futures.b;
import j.InterfaceC42164u;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: SynchronizedCaptureSessionBaseImpl.java */
@j.X
/* loaded from: classes.dex */
class N0 extends K0.a implements K0, Q0.b {

    /* renamed from: b, reason: collision with root package name */
    @j.N
    public final C20009g0 f23031b;

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public final Handler f23032c;

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public final Executor f23033d;

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public final ScheduledExecutorService f23034e;

    /* renamed from: f, reason: collision with root package name */
    @j.P
    public K0.a f23035f;

    /* renamed from: g, reason: collision with root package name */
    @j.P
    public C19986b f23036g;

    /* renamed from: h, reason: collision with root package name */
    @j.B
    @j.P
    public com.google.common.util.concurrent.D0<Void> f23037h;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    @j.P
    public b.a<Void> f23038i;

    /* renamed from: j, reason: collision with root package name */
    @j.B
    @j.P
    public androidx.camera.core.impl.utils.futures.d f23039j;

    /* renamed from: a, reason: collision with root package name */
    public final Object f23030a = new Object();

    /* renamed from: k, reason: collision with root package name */
    @j.B
    @j.P
    public List<DeferrableSurface> f23040k = null;

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public boolean f23041l = false;

    /* renamed from: m, reason: collision with root package name */
    @j.B
    public boolean f23042m = false;

    /* renamed from: n, reason: collision with root package name */
    @j.B
    public boolean f23043n = false;

    /* compiled from: SynchronizedCaptureSessionBaseImpl.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {
        public a() {
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            K0 k02;
            N0 n02 = N0.this;
            n02.u();
            C20009g0 c20009g0 = n02.f23031b;
            Iterator it = c20009g0.a().iterator();
            while (it.hasNext() && (k02 = (K0) it.next()) != n02) {
                k02.i();
            }
            synchronized (c20009g0.f23328b) {
                c20009g0.f23331e.remove(n02);
            }
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(@j.P Void r12) {
        }
    }

    /* compiled from: SynchronizedCaptureSessionBaseImpl.java */
    @j.X
    public static class b {
        @InterfaceC42164u
        public static Surface a(CameraCaptureSession cameraCaptureSession) {
            return cameraCaptureSession.getInputSurface();
        }
    }

    public N0(@j.N C20009g0 c20009g0, @j.N Executor executor, @j.N ScheduledExecutorService scheduledExecutorService, @j.N Handler handler) {
        this.f23031b = c20009g0;
        this.f23032c = handler;
        this.f23033d = executor;
        this.f23034e = scheduledExecutorService;
    }

    @Override // androidx.camera.camera2.internal.K0
    public final void a() throws CameraAccessException {
        androidx.core.util.z.f(this.f23036g, "Need to call openCaptureSession before using this API.");
        this.f23036g.c().abortCaptures();
    }

    @Override // androidx.camera.camera2.internal.K0
    public final void b() throws CameraAccessException {
        androidx.core.util.z.f(this.f23036g, "Need to call openCaptureSession before using this API.");
        this.f23036g.c().stopRepeating();
    }

    @Override // androidx.camera.camera2.internal.K0
    public void close() {
        androidx.core.util.z.f(this.f23036g, "Need to call openCaptureSession before using this API.");
        C20009g0 c20009g0 = this.f23031b;
        synchronized (c20009g0.f23328b) {
            c20009g0.f23330d.add(this);
        }
        this.f23036g.c().close();
        this.f23033d.execute(new RunnableC20041x(this, 4));
    }

    @Override // androidx.camera.camera2.internal.K0
    @j.N
    public final CameraDevice d() {
        this.f23036g.getClass();
        return this.f23036g.c().getDevice();
    }

    @Override // androidx.camera.camera2.internal.Q0.b
    @j.N
    public com.google.common.util.concurrent.D0<Void> e(@j.N CameraDevice cameraDevice, @j.N final androidx.camera.camera2.internal.compat.params.l lVar, @j.N final List<DeferrableSurface> list) {
        synchronized (this.f23030a) {
            try {
                if (this.f23042m) {
                    return androidx.camera.core.impl.utils.futures.f.e(new CancellationException("Opener is disabled"));
                }
                C20009g0 c20009g0 = this.f23031b;
                synchronized (c20009g0.f23328b) {
                    c20009g0.f23331e.add(this);
                }
                final androidx.camera.camera2.internal.compat.o oVar = new androidx.camera.camera2.internal.compat.o(cameraDevice, this.f23032c);
                com.google.common.util.concurrent.D0<Void> d0A = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.camera2.internal.M0
                    @Override // androidx.concurrent.futures.b.c
                    public final Object d(b.a aVar) {
                        String str;
                        N0 n02 = this.f23018b;
                        List<DeferrableSurface> list2 = list;
                        androidx.camera.camera2.internal.compat.o oVar2 = oVar;
                        androidx.camera.camera2.internal.compat.params.l lVar2 = lVar;
                        synchronized (n02.f23030a) {
                            synchronized (n02.f23030a) {
                                n02.u();
                                androidx.camera.core.impl.O.b(list2);
                                n02.f23040k = list2;
                            }
                            androidx.core.util.z.g("The openCaptureSessionCompleter can only set once!", n02.f23038i == null);
                            n02.f23038i = aVar;
                            oVar2.a(lVar2);
                            str = "openCaptureSession[session=" + n02 + "]";
                        }
                        return str;
                    }
                });
                this.f23037h = d0A;
                androidx.camera.core.impl.utils.futures.f.a(d0A, new a(), androidx.camera.core.impl.utils.executor.c.a());
                return androidx.camera.core.impl.utils.futures.f.h(this.f23037h);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.Q0.b
    @j.N
    public com.google.common.util.concurrent.D0 f(@j.N ArrayList arrayList) {
        synchronized (this.f23030a) {
            try {
                if (this.f23042m) {
                    return androidx.camera.core.impl.utils.futures.f.e(new CancellationException("Opener is disabled"));
                }
                androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.camera.core.impl.O.c(arrayList, this.f23033d, this.f23034e));
                C20025o0 c20025o0 = new C20025o0(2, this, arrayList);
                Executor executor = this.f23033d;
                dVarA.getClass();
                androidx.camera.core.impl.utils.futures.d dVar = (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, c20025o0, executor);
                this.f23039j = dVar;
                return androidx.camera.core.impl.utils.futures.f.h(dVar);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.K0
    @j.N
    public final C19986b g() {
        this.f23036g.getClass();
        return this.f23036g;
    }

    @Override // androidx.camera.camera2.internal.K0
    @j.N
    public com.google.common.util.concurrent.D0<Void> h() {
        return androidx.camera.core.impl.utils.futures.f.g(null);
    }

    @Override // androidx.camera.camera2.internal.K0
    public final void i() {
        u();
    }

    @Override // androidx.camera.camera2.internal.K0
    public final int j(@j.N ArrayList arrayList, @j.N CameraCaptureSession.CaptureCallback captureCallback) {
        androidx.core.util.z.f(this.f23036g, "Need to call openCaptureSession before using this API.");
        return this.f23036g.a(arrayList, this.f23033d, captureCallback);
    }

    @Override // androidx.camera.camera2.internal.K0
    public int k(@j.N CaptureRequest captureRequest, @j.N CameraCaptureSession.CaptureCallback captureCallback) {
        androidx.core.util.z.f(this.f23036g, "Need to call openCaptureSession before using this API.");
        return this.f23036g.b(captureRequest, this.f23033d, captureCallback);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void l(@j.N K0 k02) {
        Objects.requireNonNull(this.f23035f);
        this.f23035f.l(k02);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    @j.X
    public final void m(@j.N K0 k02) {
        Objects.requireNonNull(this.f23035f);
        this.f23035f.m(k02);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public void n(@j.N K0 k02) {
        com.google.common.util.concurrent.D0<Void> d02;
        synchronized (this.f23030a) {
            try {
                if (this.f23041l) {
                    d02 = null;
                } else {
                    this.f23041l = true;
                    androidx.core.util.z.f(this.f23037h, "Need to call openCaptureSession before using this API.");
                    d02 = this.f23037h;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        u();
        if (d02 != null) {
            d02.N(new L0(this, k02, 0), androidx.camera.core.impl.utils.executor.c.a());
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void o(@j.N K0 k02) {
        K0 k03;
        Objects.requireNonNull(this.f23035f);
        u();
        C20009g0 c20009g0 = this.f23031b;
        Iterator it = c20009g0.a().iterator();
        while (it.hasNext() && (k03 = (K0) it.next()) != this) {
            k03.i();
        }
        synchronized (c20009g0.f23328b) {
            c20009g0.f23331e.remove(this);
        }
        this.f23035f.o(k02);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public void p(@j.N K0 k02) {
        K0 k03;
        Objects.requireNonNull(this.f23035f);
        C20009g0 c20009g0 = this.f23031b;
        synchronized (c20009g0.f23328b) {
            c20009g0.f23329c.add(this);
            c20009g0.f23331e.remove(this);
        }
        Iterator it = c20009g0.a().iterator();
        while (it.hasNext() && (k03 = (K0) it.next()) != this) {
            k03.i();
        }
        this.f23035f.p(k02);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void q(@j.N K0 k02) {
        Objects.requireNonNull(this.f23035f);
        this.f23035f.q(k02);
    }

    @Override // androidx.camera.camera2.internal.K0.a
    public final void r(@j.N K0 k02) {
        com.google.common.util.concurrent.D0<Void> d02;
        synchronized (this.f23030a) {
            try {
                if (this.f23043n) {
                    d02 = null;
                } else {
                    this.f23043n = true;
                    androidx.core.util.z.f(this.f23037h, "Need to call openCaptureSession before using this API.");
                    d02 = this.f23037h;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (d02 != null) {
            d02.N(new L0(this, k02, 1), androidx.camera.core.impl.utils.executor.c.a());
        }
    }

    @Override // androidx.camera.camera2.internal.K0.a
    @j.X
    public final void s(@j.N K0 k02, @j.N Surface surface) {
        Objects.requireNonNull(this.f23035f);
        this.f23035f.s(k02, surface);
    }

    @Override // androidx.camera.camera2.internal.Q0.b
    public boolean stop() {
        boolean z12;
        boolean z13;
        try {
            synchronized (this.f23030a) {
                try {
                    if (!this.f23042m) {
                        androidx.camera.core.impl.utils.futures.d dVar = this.f23039j;
                        dVar = dVar != null ? dVar : null;
                        this.f23042m = true;
                    }
                    synchronized (this.f23030a) {
                        z12 = this.f23037h != null;
                    }
                    z13 = !z12;
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return z13;
        } finally {
            if (dVar != null) {
                dVar.cancel(true);
            }
        }
    }

    public final void t(@j.N CameraCaptureSession cameraCaptureSession) {
        if (this.f23036g == null) {
            this.f23036g = new C19986b(cameraCaptureSession, this.f23032c);
        }
    }

    public final void u() {
        synchronized (this.f23030a) {
            try {
                List<DeferrableSurface> list = this.f23040k;
                if (list != null) {
                    androidx.camera.core.impl.O.a(list);
                    this.f23040k = null;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.K0
    @j.N
    public final K0.a c() {
        return this;
    }
}
