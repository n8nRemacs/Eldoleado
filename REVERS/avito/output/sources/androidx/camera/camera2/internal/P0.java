package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import androidx.camera.camera2.internal.A;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.DeferrableSurface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: SynchronizedCaptureSessionImpl.java */
@j.X
/* loaded from: classes.dex */
class P0 extends N0 {

    /* renamed from: o, reason: collision with root package name */
    public final Object f23048o;

    /* renamed from: p, reason: collision with root package name */
    @j.B
    @j.P
    public ArrayList f23049p;

    /* renamed from: q, reason: collision with root package name */
    @j.B
    @j.P
    public androidx.camera.core.impl.utils.futures.d f23050q;

    /* renamed from: r, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.i f23051r;

    /* renamed from: s, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.w f23052s;

    /* renamed from: t, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.h f23053t;

    public P0(@j.N Handler handler, @j.N C20009g0 c20009g0, @j.N androidx.camera.core.impl.w0 w0Var, @j.N androidx.camera.core.impl.w0 w0Var2, @j.N Executor executor, @j.N ScheduledExecutorService scheduledExecutorService) {
        super(c20009g0, executor, scheduledExecutorService, handler);
        this.f23048o = new Object();
        this.f23051r = new androidx.camera.camera2.internal.compat.workaround.i(w0Var, w0Var2);
        this.f23052s = new androidx.camera.camera2.internal.compat.workaround.w(w0Var);
        this.f23053t = new androidx.camera.camera2.internal.compat.workaround.h(w0Var2);
    }

    public static void v(P0 p02) {
        p02.toString();
        C20140q0.d(3, "SyncCaptureSessionImpl");
        super.close();
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.K0
    public final void close() {
        toString();
        C20140q0.d(3, "SyncCaptureSessionImpl");
        androidx.camera.camera2.internal.compat.workaround.w wVar = this.f23052s;
        synchronized (wVar.f23277b) {
            try {
                if (wVar.f23276a && !wVar.f23280e) {
                    wVar.f23278c.cancel(true);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        androidx.camera.core.impl.utils.futures.f.h(this.f23052s.f23278c).N(new RunnableC20041x(this, 5), this.f23033d);
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.Q0.b
    @j.N
    public final com.google.common.util.concurrent.D0<Void> e(@j.N CameraDevice cameraDevice, @j.N androidx.camera.camera2.internal.compat.params.l lVar, @j.N List<DeferrableSurface> list) {
        ArrayList arrayList;
        com.google.common.util.concurrent.D0<Void> d0H;
        synchronized (this.f23048o) {
            androidx.camera.camera2.internal.compat.workaround.w wVar = this.f23052s;
            C20009g0 c20009g0 = this.f23031b;
            synchronized (c20009g0.f23328b) {
                arrayList = new ArrayList(c20009g0.f23330d);
            }
            C c12 = new C(this, 4);
            wVar.getClass();
            androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.camera2.internal.compat.workaround.w.a(cameraDevice, lVar, list, arrayList, c12);
            this.f23050q = dVarA;
            d0H = androidx.camera.core.impl.utils.futures.f.h(dVarA);
        }
        return d0H;
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.Q0.b
    @j.N
    public final com.google.common.util.concurrent.D0 f(@j.N ArrayList arrayList) {
        com.google.common.util.concurrent.D0 d0F;
        synchronized (this.f23048o) {
            this.f23049p = arrayList;
            d0F = super.f(arrayList);
        }
        return d0F;
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.K0
    @j.N
    public final com.google.common.util.concurrent.D0<Void> h() {
        return androidx.camera.core.impl.utils.futures.f.h(this.f23052s.f23278c);
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.K0
    public final int k(@j.N CaptureRequest captureRequest, @j.N CameraCaptureSession.CaptureCallback captureCallback) {
        int iK2;
        androidx.camera.camera2.internal.compat.workaround.w wVar = this.f23052s;
        synchronized (wVar.f23277b) {
            try {
                if (wVar.f23276a) {
                    A.a aVar = new A.a(Arrays.asList(wVar.f23281f, captureCallback));
                    wVar.f23280e = true;
                    captureCallback = aVar;
                }
                iK2 = super.k(captureRequest, captureCallback);
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return iK2;
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.K0.a
    public final void n(@j.N K0 k02) {
        synchronized (this.f23048o) {
            this.f23051r.a(this.f23049p);
        }
        toString();
        C20140q0.d(3, "SyncCaptureSessionImpl");
        super.n(k02);
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.K0.a
    public final void p(@j.N K0 k02) {
        ArrayList arrayList;
        ArrayList arrayList2;
        K0 k03;
        K0 k04;
        toString();
        C20140q0.d(3, "SyncCaptureSessionImpl");
        C20009g0 c20009g0 = this.f23031b;
        synchronized (c20009g0.f23328b) {
            arrayList = new ArrayList(c20009g0.f23331e);
        }
        synchronized (c20009g0.f23328b) {
            arrayList2 = new ArrayList(c20009g0.f23329c);
        }
        androidx.camera.camera2.internal.compat.workaround.h hVar = this.f23053t;
        if (hVar.f23253a != null) {
            LinkedHashSet<K0> linkedHashSet = new LinkedHashSet();
            Iterator it = arrayList.iterator();
            while (it.hasNext() && (k04 = (K0) it.next()) != k02) {
                linkedHashSet.add(k04);
            }
            for (K0 k05 : linkedHashSet) {
                k05.c().o(k05);
            }
        }
        super.p(k02);
        if (hVar.f23253a != null) {
            LinkedHashSet<K0> linkedHashSet2 = new LinkedHashSet();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext() && (k03 = (K0) it2.next()) != k02) {
                linkedHashSet2.add(k03);
            }
            for (K0 k06 : linkedHashSet2) {
                k06.c().n(k06);
            }
        }
    }

    @Override // androidx.camera.camera2.internal.N0, androidx.camera.camera2.internal.Q0.b
    public final boolean stop() {
        boolean z12;
        boolean zStop;
        synchronized (this.f23048o) {
            try {
                synchronized (this.f23030a) {
                    z12 = this.f23037h != null;
                }
                if (z12) {
                    this.f23051r.a(this.f23049p);
                } else {
                    androidx.camera.core.impl.utils.futures.d dVar = this.f23050q;
                    if (dVar != null) {
                        dVar.cancel(true);
                    }
                }
                zStop = super.stop();
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return zStop;
    }
}
