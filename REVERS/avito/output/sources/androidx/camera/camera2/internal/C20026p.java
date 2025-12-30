package androidx.camera.camera2.internal;

import D.h;
import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageWriter;
import android.util.ArrayMap;
import android.util.Range;
import android.util.Size;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.camera2.internal.D;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraControl;
import androidx.camera.core.InterfaceC20060g0;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: Camera2CameraControlImpl.java */
@j.S
@j.X
/* renamed from: androidx.camera.camera2.internal.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C20026p implements CameraControlInternal {

    /* renamed from: b, reason: collision with root package name */
    @j.k0
    public final b f23388b;

    /* renamed from: c, reason: collision with root package name */
    public final Executor f23389c;

    /* renamed from: d, reason: collision with root package name */
    public final Object f23390d = new Object();

    /* renamed from: e, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.n f23391e;

    /* renamed from: f, reason: collision with root package name */
    public final CameraControlInternal.b f23392f;

    /* renamed from: g, reason: collision with root package name */
    public final SessionConfig.b f23393g;

    /* renamed from: h, reason: collision with root package name */
    public final C20034t0 f23394h;

    /* renamed from: i, reason: collision with root package name */
    public final Z0 f23395i;

    /* renamed from: j, reason: collision with root package name */
    public final W0 f23396j;

    /* renamed from: k, reason: collision with root package name */
    public final C20021m0 f23397k;

    /* renamed from: l, reason: collision with root package name */
    @j.k0
    public final d1 f23398l;

    /* renamed from: m, reason: collision with root package name */
    public final D.e f23399m;

    /* renamed from: n, reason: collision with root package name */
    public final D f23400n;

    /* renamed from: o, reason: collision with root package name */
    @j.B
    public int f23401o;

    /* renamed from: p, reason: collision with root package name */
    public volatile boolean f23402p;

    /* renamed from: q, reason: collision with root package name */
    public volatile int f23403q;

    /* renamed from: r, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.a f23404r;

    /* renamed from: s, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.b f23405s;

    /* renamed from: t, reason: collision with root package name */
    public final AtomicLong f23406t;

    /* renamed from: u, reason: collision with root package name */
    @j.N
    public volatile com.google.common.util.concurrent.D0<Void> f23407u;

    /* renamed from: v, reason: collision with root package name */
    public int f23408v;

    /* renamed from: w, reason: collision with root package name */
    public long f23409w;

    /* renamed from: x, reason: collision with root package name */
    public final a f23410x;

    /* compiled from: Camera2CameraControlImpl.java */
    @j.X
    /* renamed from: androidx.camera.camera2.internal.p$a */
    public static final class a extends AbstractC20109p {

        /* renamed from: a, reason: collision with root package name */
        public final HashSet f23411a = new HashSet();

        /* renamed from: b, reason: collision with root package name */
        public final ArrayMap f23412b = new ArrayMap();

        @Override // androidx.camera.core.impl.AbstractC20109p
        public final void a() {
            Iterator it = this.f23411a.iterator();
            while (it.hasNext()) {
                AbstractC20109p abstractC20109p = (AbstractC20109p) it.next();
                try {
                    ((Executor) this.f23412b.get(abstractC20109p)).execute(new RunnableC20041x(abstractC20109p, 6));
                } catch (RejectedExecutionException unused) {
                    C20140q0.c("Camera2CameraControlImp");
                }
            }
        }

        @Override // androidx.camera.core.impl.AbstractC20109p
        public final void b(@j.N androidx.camera.core.impl.r rVar) {
            Iterator it = this.f23411a.iterator();
            while (it.hasNext()) {
                AbstractC20109p abstractC20109p = (AbstractC20109p) it.next();
                try {
                    ((Executor) this.f23412b.get(abstractC20109p)).execute(new RunnableC20016k(abstractC20109p, rVar, 5));
                } catch (RejectedExecutionException unused) {
                    C20140q0.c("Camera2CameraControlImp");
                }
            }
        }

        @Override // androidx.camera.core.impl.AbstractC20109p
        public final void c(@j.N CameraCaptureFailure cameraCaptureFailure) {
            Iterator it = this.f23411a.iterator();
            while (it.hasNext()) {
                AbstractC20109p abstractC20109p = (AbstractC20109p) it.next();
                try {
                    ((Executor) this.f23412b.get(abstractC20109p)).execute(new RunnableC20016k(abstractC20109p, cameraCaptureFailure, 6));
                } catch (RejectedExecutionException unused) {
                    C20140q0.c("Camera2CameraControlImp");
                }
            }
        }
    }

    /* compiled from: Camera2CameraControlImpl.java */
    /* renamed from: androidx.camera.camera2.internal.p$b */
    public static final class b extends CameraCaptureSession.CaptureCallback {

        /* renamed from: c, reason: collision with root package name */
        public static final /* synthetic */ int f23413c = 0;

        /* renamed from: a, reason: collision with root package name */
        public final HashSet f23414a = new HashSet();

        /* renamed from: b, reason: collision with root package name */
        public final Executor f23415b;

        public b(@j.N Executor executor) {
            this.f23415b = executor;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
            this.f23415b.execute(new RunnableC20016k(1, this, totalCaptureResult));
        }
    }

    /* compiled from: Camera2CameraControlImpl.java */
    /* renamed from: androidx.camera.camera2.internal.p$c */
    public interface c {
        boolean a(@j.N TotalCaptureResult totalCaptureResult);
    }

    public C20026p(@j.N androidx.camera.camera2.internal.compat.n nVar, @j.N ScheduledExecutorService scheduledExecutorService, @j.N Executor executor, @j.N CameraControlInternal.b bVar, @j.N androidx.camera.core.impl.w0 w0Var) {
        SessionConfig.b bVar2 = new SessionConfig.b();
        this.f23393g = bVar2;
        this.f23401o = 0;
        this.f23402p = false;
        this.f23403q = 2;
        this.f23406t = new AtomicLong(0L);
        this.f23407u = androidx.camera.core.impl.utils.futures.f.g(null);
        this.f23408v = 1;
        this.f23409w = 0L;
        a aVar = new a();
        this.f23410x = aVar;
        this.f23391e = nVar;
        this.f23392f = bVar;
        this.f23389c = executor;
        b bVar3 = new b(executor);
        this.f23388b = bVar3;
        bVar2.s(this.f23408v);
        bVar2.g(new Z(bVar3));
        bVar2.g(aVar);
        this.f23397k = new C20021m0(this, nVar, executor);
        this.f23394h = new C20034t0(this, scheduledExecutorService, executor, w0Var);
        this.f23395i = new Z0(this, nVar, executor);
        this.f23396j = new W0(this, nVar, executor);
        this.f23398l = new d1(nVar);
        this.f23404r = new androidx.camera.camera2.internal.compat.workaround.a(w0Var);
        this.f23405s = new androidx.camera.camera2.internal.compat.workaround.b(w0Var);
        this.f23399m = new D.e(this, executor);
        this.f23400n = new D(this, nVar, w0Var, executor);
        executor.execute(new RunnableC20012i(this, 0));
    }

    public static boolean t(int i12, int[] iArr) {
        for (int i13 : iArr) {
            if (i12 == i13) {
                return true;
            }
        }
        return false;
    }

    public static boolean u(@j.N TotalCaptureResult totalCaptureResult, long j12) {
        Long l12;
        if (totalCaptureResult.getRequest() == null) {
            return false;
        }
        Object tag = totalCaptureResult.getRequest().getTag();
        return (tag instanceof androidx.camera.core.impl.J0) && (l12 = (Long) ((androidx.camera.core.impl.J0) tag).f23987a.get("CameraControlSessionUpdateId")) != null && l12.longValue() >= j12;
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> a(float f12) {
        com.google.common.util.concurrent.D0 d0E;
        androidx.camera.core.Q0 q0F;
        if (!s()) {
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        Z0 z02 = this.f23395i;
        synchronized (z02.f23096c) {
            try {
                z02.f23096c.e(f12);
                q0F = androidx.camera.core.internal.e.f(z02.f23096c);
            } catch (IllegalArgumentException e12) {
                d0E = androidx.camera.core.impl.utils.futures.f.e(e12);
            }
        }
        z02.c(q0F);
        d0E = androidx.concurrent.futures.b.a(new X0(z02, q0F, 1));
        return androidx.camera.core.impl.utils.futures.f.h(d0E);
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> b(float f12) {
        com.google.common.util.concurrent.D0 d0E;
        androidx.camera.core.Q0 q0F;
        if (!s()) {
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        Z0 z02 = this.f23395i;
        synchronized (z02.f23096c) {
            try {
                z02.f23096c.f(f12);
                q0F = androidx.camera.core.internal.e.f(z02.f23096c);
            } catch (IllegalArgumentException e12) {
                d0E = androidx.camera.core.impl.utils.futures.f.e(e12);
            }
        }
        z02.c(q0F);
        d0E = androidx.concurrent.futures.b.a(new X0(z02, q0F, 0));
        return androidx.camera.core.impl.utils.futures.f.h(d0E);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public final Config c() {
        return this.f23399m.a();
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public final com.google.common.util.concurrent.D0 d(@j.N final ArrayList arrayList, final int i12, final int i13) {
        if (!s()) {
            C20140q0.d(5, "Camera2CameraControlImp");
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        final int i14 = this.f23403q;
        androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.camera.core.impl.utils.futures.f.h(this.f23407u));
        androidx.camera.core.impl.utils.futures.a aVar = new androidx.camera.core.impl.utils.futures.a() { // from class: androidx.camera.camera2.internal.l
            @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
            public final com.google.common.util.concurrent.D0 apply(Object obj) {
                com.google.common.util.concurrent.D0<TotalCaptureResult> d0G;
                D d12 = this.f23361b.f23400n;
                androidx.camera.camera2.internal.compat.workaround.n nVar = new androidx.camera.camera2.internal.compat.workaround.n(d12.f22938d);
                int i15 = d12.f22941g;
                boolean z12 = d12.f22940f;
                Executor executor = d12.f22939e;
                final D.c cVar = new D.c(i15, executor, d12.f22935a, z12, nVar);
                ArrayList arrayList2 = cVar.f22957g;
                int i16 = i12;
                C20026p c20026p = d12.f22935a;
                if (i16 == 0) {
                    arrayList2.add(new D.b(c20026p));
                }
                boolean z13 = d12.f22937c;
                final int i17 = i14;
                if (z13) {
                    if (d12.f22936b.f23270a || d12.f22941g == 3 || i13 == 1) {
                        arrayList2.add(new D.f(c20026p, i17, executor));
                    } else {
                        arrayList2.add(new D.a(c20026p, i17, nVar));
                    }
                }
                com.google.common.util.concurrent.D0 d0G2 = androidx.camera.core.impl.utils.futures.f.g(null);
                boolean zIsEmpty = arrayList2.isEmpty();
                D.c.a aVar2 = cVar.f22958h;
                Executor executor2 = cVar.f22952b;
                if (!zIsEmpty) {
                    if (aVar2.b()) {
                        D.e eVar = new D.e(0L, null);
                        cVar.f22953c.m(eVar);
                        d0G = eVar.f22961b;
                    } else {
                        d0G = androidx.camera.core.impl.utils.futures.f.g(null);
                    }
                    androidx.camera.core.impl.utils.futures.d dVarA2 = androidx.camera.core.impl.utils.futures.d.a(d0G);
                    E e12 = new E(cVar, i17);
                    dVarA2.getClass();
                    d0G2 = (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m((androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA2, e12, executor2), new C(cVar, 1), executor2);
                }
                androidx.camera.core.impl.utils.futures.d dVarA3 = androidx.camera.core.impl.utils.futures.d.a(d0G2);
                final ArrayList arrayList3 = arrayList;
                androidx.camera.core.impl.utils.futures.a aVar3 = new androidx.camera.core.impl.utils.futures.a() { // from class: androidx.camera.camera2.internal.F
                    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
                    public final com.google.common.util.concurrent.D0 apply(Object obj2) {
                        C20026p c20026p2;
                        androidx.camera.core.impl.r rVar;
                        InterfaceC20120j0 interfaceC20120j0A;
                        int i18 = D.c.f22950k;
                        D.c cVar2 = cVar;
                        ArrayList arrayList4 = new ArrayList();
                        ArrayList<androidx.camera.core.impl.H> arrayList5 = new ArrayList();
                        Iterator it = arrayList3.iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            c20026p2 = cVar2.f22953c;
                            if (!zHasNext) {
                                break;
                            }
                            androidx.camera.core.impl.H h12 = (androidx.camera.core.impl.H) it.next();
                            H.a aVar4 = new H.a(h12);
                            androidx.camera.core.impl.r rVar2 = null;
                            int i19 = h12.f23970c;
                            if (i19 == 5) {
                                d1 d1Var = c20026p2.f23398l;
                                if (!d1Var.f23316d && !d1Var.f23315c) {
                                    try {
                                        interfaceC20120j0A = d1Var.f23314b.a();
                                    } catch (NoSuchElementException unused) {
                                        C20140q0.b("ZslControlImpl");
                                        interfaceC20120j0A = null;
                                    }
                                    if (interfaceC20120j0A != null) {
                                        d1 d1Var2 = c20026p2.f23398l;
                                        d1Var2.getClass();
                                        Image image = interfaceC20120j0A.getImage();
                                        ImageWriter imageWriter = d1Var2.f23322j;
                                        if (imageWriter != null && image != null) {
                                            try {
                                                imageWriter.queueInputImage(image);
                                                InterfaceC20060g0 interfaceC20060g0R1 = interfaceC20120j0A.r1();
                                                if (interfaceC20060g0R1 instanceof androidx.camera.core.internal.c) {
                                                    rVar2 = ((androidx.camera.core.internal.c) interfaceC20060g0R1).f24335a;
                                                }
                                            } catch (IllegalStateException e13) {
                                                e13.getMessage();
                                                C20140q0.b("ZslControlImpl");
                                            }
                                        }
                                    }
                                }
                            }
                            if (rVar2 != null) {
                                aVar4.f23983h = rVar2;
                            } else {
                                int i22 = (cVar2.f22951a != 3 || cVar2.f22955e) ? (i19 == -1 || i19 == 5) ? 2 : -1 : 4;
                                if (i22 != -1) {
                                    aVar4.f23978c = i22;
                                }
                            }
                            androidx.camera.camera2.internal.compat.workaround.n nVar2 = cVar2.f22954d;
                            if (nVar2.f23262b && i17 == 0 && nVar2.f23261a) {
                                b.a aVar5 = new b.a();
                                aVar5.c(CaptureRequest.CONTROL_AE_MODE, 3);
                                aVar4.c(aVar5.a());
                            }
                            arrayList4.add(androidx.concurrent.futures.b.a(new C(cVar2, aVar4)));
                            arrayList5.add(aVar4.e());
                        }
                        C20039w.e eVar2 = (C20039w.e) c20026p2.f23392f;
                        eVar2.getClass();
                        C20039w c20039w = C20039w.this;
                        c20039w.getClass();
                        ArrayList arrayList6 = new ArrayList();
                        for (androidx.camera.core.impl.H h13 : arrayList5) {
                            H.a aVar6 = new H.a(h13);
                            if (h13.f23970c == 5 && (rVar = h13.f23975h) != null) {
                                aVar6.f23983h = rVar;
                            }
                            if (Collections.unmodifiableList(h13.f23968a).isEmpty() && h13.f23973f) {
                                HashSet hashSet = aVar6.f23976a;
                                if (hashSet.isEmpty()) {
                                    Iterator<SessionConfig> it2 = c20039w.f23466b.b().iterator();
                                    while (it2.hasNext()) {
                                        List listUnmodifiableList = Collections.unmodifiableList(it2.next().f24017f.f23968a);
                                        if (!listUnmodifiableList.isEmpty()) {
                                            Iterator it3 = listUnmodifiableList.iterator();
                                            while (it3.hasNext()) {
                                                aVar6.d((DeferrableSurface) it3.next());
                                            }
                                        }
                                    }
                                    if (hashSet.isEmpty()) {
                                        C20140q0.d(5, "Camera2CameraImpl");
                                    }
                                } else {
                                    C20140q0.d(5, "Camera2CameraImpl");
                                }
                            }
                            arrayList6.add(aVar6.e());
                        }
                        c20039w.s("Issue capture request");
                        c20039w.f23478n.b(arrayList6);
                        return androidx.camera.core.impl.utils.futures.f.b(arrayList4);
                    }
                };
                dVarA3.getClass();
                androidx.camera.core.impl.utils.futures.d dVar = (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA3, aVar3, executor2);
                Objects.requireNonNull(aVar2);
                dVar.N(new RunnableC20041x(aVar2, 1), executor2);
                return androidx.camera.core.impl.utils.futures.f.h(dVar);
            }
        };
        Executor executor = this.f23389c;
        dVarA.getClass();
        return (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, aVar, executor);
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void e() {
        D.e eVar = this.f23399m;
        synchronized (eVar.f2834e) {
            eVar.f2835f = new b.a();
        }
        androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new D.a(eVar, 1))).N(new RunnableC20014j(), androidx.camera.core.impl.utils.executor.c.a());
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Void> f(final boolean z12) {
        com.google.common.util.concurrent.D0 d0A;
        if (!s()) {
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        final W0 w02 = this.f23396j;
        if (w02.f23080c) {
            W0.b(w02.f23079b, Integer.valueOf(z12 ? 1 : 0));
            d0A = androidx.concurrent.futures.b.a(new b.c() { // from class: androidx.camera.camera2.internal.T0
                @Override // androidx.concurrent.futures.b.c
                public final Object d(final b.a aVar) {
                    final W0 w03 = w02;
                    w03.getClass();
                    final boolean z13 = z12;
                    w03.f23081d.execute(new Runnable() { // from class: androidx.camera.camera2.internal.U0
                        @Override // java.lang.Runnable
                        public final void run() {
                            w03.a(aVar, z13);
                        }
                    });
                    return "enableTorch: " + z13;
                }
            });
        } else {
            C20140q0.d(3, "TorchControl");
            d0A = androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("No flash unit"));
        }
        return androidx.camera.core.impl.utils.futures.f.h(d0A);
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<androidx.camera.core.N> g(@j.N androidx.camera.core.M m12) {
        if (!s()) {
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        C20034t0 c20034t0 = this.f23394h;
        c20034t0.getClass();
        return androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new C20025o0(0, c20034t0, m12)));
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void h(@j.N Config config) {
        D.e eVar = this.f23399m;
        D.h hVarA = h.a.c(config).a();
        synchronized (eVar.f2834e) {
            try {
                for (Config.a<?> aVar : hVarA.e()) {
                    eVar.f2835f.f22897a.K(aVar, hVarA.f(aVar));
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new D.a(eVar, 0))).N(new RunnableC20014j(), androidx.camera.core.impl.utils.executor.c.a());
    }

    @Override // androidx.camera.core.CameraControl
    @j.N
    public final com.google.common.util.concurrent.D0<Integer> i(int i12) {
        if (!s()) {
            return androidx.camera.core.impl.utils.futures.f.e(new CameraControl.OperationCanceledException("Camera is not active."));
        }
        C20021m0 c20021m0 = this.f23397k;
        C20023n0 c20023n0 = c20021m0.f23373b;
        if (!c20023n0.a()) {
            return androidx.camera.core.impl.utils.futures.f.e(new IllegalArgumentException("ExposureCompensation is not supported"));
        }
        Range<Integer> rangeD = c20023n0.d();
        if (rangeD.contains((Range<Integer>) Integer.valueOf(i12))) {
            synchronized (c20023n0.f23380a) {
                c20023n0.f23382c = i12;
            }
            return androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new E(c20021m0, i12)));
        }
        StringBuilder sbJ = G.j(i12, "Requested ExposureCompensation ", " is not within valid range [");
        sbJ.append(rangeD.getUpper());
        sbJ.append("..");
        sbJ.append(rangeD.getLower());
        sbJ.append("]");
        return androidx.camera.core.impl.utils.futures.f.e(new IllegalArgumentException(sbJ.toString()));
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    @j.N
    public final Rect j() {
        Rect rect = (Rect) this.f23391e.a(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        rect.getClass();
        return rect;
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void k(int i12) {
        if (!s()) {
            C20140q0.d(5, "Camera2CameraControlImp");
            return;
        }
        this.f23403q = i12;
        d1 d1Var = this.f23398l;
        boolean z12 = true;
        if (this.f23403q != 1 && this.f23403q != 0) {
            z12 = false;
        }
        d1Var.f23316d = z12;
        this.f23407u = androidx.camera.core.impl.utils.futures.f.h(androidx.concurrent.futures.b.a(new C(this, 6)));
    }

    @Override // androidx.camera.core.impl.CameraControlInternal
    public final void l(@j.N SessionConfig.b bVar) throws Exception {
        HashMap map;
        StreamConfigurationMap streamConfigurationMap;
        int[] validOutputFormatsForInput;
        d1 d1Var = this.f23398l;
        androidx.camera.camera2.internal.compat.n nVar = d1Var.f23313a;
        while (true) {
            androidx.camera.core.internal.utils.f fVar = d1Var.f23314b;
            if (fVar.c()) {
                break;
            } else {
                fVar.a().close();
            }
        }
        C20094g0 c20094g0 = d1Var.f23321i;
        StreamConfigurationMap streamConfigurationMap2 = null;
        if (c20094g0 != null) {
            androidx.camera.core.y0 y0Var = d1Var.f23319g;
            if (y0Var != null) {
                androidx.camera.core.impl.utils.futures.f.h(c20094g0.f23933e).N(new RunnableC20041x(y0Var, 8), androidx.camera.core.impl.utils.executor.c.d());
                d1Var.f23319g = null;
            }
            c20094g0.a();
            d1Var.f23321i = null;
        }
        ImageWriter imageWriter = d1Var.f23322j;
        if (imageWriter != null) {
            imageWriter.close();
            d1Var.f23322j = null;
        }
        if (d1Var.f23315c || d1Var.f23318f) {
            return;
        }
        try {
            streamConfigurationMap2 = (StreamConfigurationMap) nVar.a(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        } catch (AssertionError e12) {
            e12.getMessage();
            C20140q0.b("ZslControlImpl");
        }
        if (streamConfigurationMap2 == null || streamConfigurationMap2.getInputFormats() == null) {
            map = new HashMap();
        } else {
            map = new HashMap();
            for (int i12 : streamConfigurationMap2.getInputFormats()) {
                Size[] inputSizes = streamConfigurationMap2.getInputSizes(i12);
                if (inputSizes != null) {
                    Arrays.sort(inputSizes, new androidx.camera.core.impl.utils.g(true));
                    map.put(Integer.valueOf(i12), inputSizes[0]);
                }
            }
        }
        if (!d1Var.f23317e || map.isEmpty() || !map.containsKey(34) || (streamConfigurationMap = (StreamConfigurationMap) nVar.a(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)) == null || (validOutputFormatsForInput = streamConfigurationMap.getValidOutputFormatsForInput(34)) == null) {
            return;
        }
        for (int i13 : validOutputFormatsForInput) {
            if (i13 == 256) {
                Size size = (Size) map.get(34);
                androidx.camera.core.r0 r0Var = new androidx.camera.core.r0(size.getWidth(), size.getHeight(), 34, 9);
                d1Var.f23320h = r0Var.f24519b;
                d1Var.f23319g = new androidx.camera.core.y0(r0Var);
                r0Var.d(new C(d1Var, 5), androidx.camera.core.impl.utils.executor.c.c());
                C20094g0 c20094g02 = new C20094g0(d1Var.f23319g.getSurface(), new Size(d1Var.f23319g.getWidth(), d1Var.f23319g.getHeight()), 34);
                d1Var.f23321i = c20094g02;
                androidx.camera.core.y0 y0Var2 = d1Var.f23319g;
                com.google.common.util.concurrent.D0 d0H = androidx.camera.core.impl.utils.futures.f.h(c20094g02.f23933e);
                Objects.requireNonNull(y0Var2);
                d0H.N(new RunnableC20041x(y0Var2, 8), androidx.camera.core.impl.utils.executor.c.d());
                bVar.i(d1Var.f23321i, androidx.camera.core.F.f23562d);
                bVar.b(d1Var.f23320h);
                bVar.h(new c1(d1Var));
                bVar.r(new InputConfiguration(d1Var.f23319g.getWidth(), d1Var.f23319g.getHeight(), d1Var.f23319g.f()));
                return;
            }
        }
    }

    public final void m(@j.N c cVar) {
        this.f23388b.f23414a.add(cVar);
    }

    public final void n() {
        synchronized (this.f23390d) {
            try {
                int i12 = this.f23401o;
                if (i12 == 0) {
                    throw new IllegalStateException("Decrementing use count occurs more times than incrementing");
                }
                this.f23401o = i12 - 1;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void o(boolean z12) {
        androidx.camera.core.impl.r rVar;
        this.f23402p = z12;
        if (!z12) {
            H.a aVar = new H.a();
            aVar.f23978c = this.f23408v;
            aVar.f23981f = true;
            b.a aVar2 = new b.a();
            aVar2.c(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(q(1)));
            aVar2.c(CaptureRequest.FLASH_MODE, 0);
            aVar.c(aVar2.a());
            List<androidx.camera.core.impl.H> listSingletonList = Collections.singletonList(aVar.e());
            C20039w.e eVar = (C20039w.e) this.f23392f;
            eVar.getClass();
            listSingletonList.getClass();
            C20039w c20039w = C20039w.this;
            c20039w.getClass();
            ArrayList arrayList = new ArrayList();
            for (androidx.camera.core.impl.H h12 : listSingletonList) {
                H.a aVar3 = new H.a(h12);
                if (h12.f23970c == 5 && (rVar = h12.f23975h) != null) {
                    aVar3.f23983h = rVar;
                }
                if (Collections.unmodifiableList(h12.f23968a).isEmpty() && h12.f23973f) {
                    HashSet hashSet = aVar3.f23976a;
                    if (hashSet.isEmpty()) {
                        Iterator<SessionConfig> it = c20039w.f23466b.b().iterator();
                        while (it.hasNext()) {
                            List listUnmodifiableList = Collections.unmodifiableList(it.next().f24017f.f23968a);
                            if (!listUnmodifiableList.isEmpty()) {
                                Iterator it2 = listUnmodifiableList.iterator();
                                while (it2.hasNext()) {
                                    aVar3.d((DeferrableSurface) it2.next());
                                }
                            }
                        }
                        if (hashSet.isEmpty()) {
                            C20140q0.d(5, "Camera2CameraImpl");
                        }
                    } else {
                        C20140q0.d(5, "Camera2CameraImpl");
                    }
                }
                arrayList.add(aVar3.e());
            }
            c20039w.s("Issue capture request");
            c20039w.f23478n.b(arrayList);
        }
        w();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e4 A[LOOP:0: B:43:0x00de->B:45:0x00e4, LOOP_END] */
    @j.N
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.impl.SessionConfig p() {
        /*
            Method dump skipped, instructions count: 301
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.C20026p.p():androidx.camera.core.impl.SessionConfig");
    }

    public final int q(int i12) {
        int[] iArr = (int[]) this.f23391e.a(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
        if (iArr == null) {
            return 0;
        }
        return t(i12, iArr) ? i12 : t(1, iArr) ? 1 : 0;
    }

    public final int r(int i12) {
        int[] iArr = (int[]) this.f23391e.a(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
        if (iArr == null) {
            return 0;
        }
        if (t(i12, iArr)) {
            return i12;
        }
        if (t(4, iArr)) {
            return 4;
        }
        return t(1, iArr) ? 1 : 0;
    }

    public final boolean s() {
        int i12;
        synchronized (this.f23390d) {
            i12 = this.f23401o;
        }
        return i12 > 0;
    }

    public final void v(boolean z12) {
        androidx.camera.core.Q0 q0F;
        C20034t0 c20034t0 = this.f23394h;
        if (z12 != c20034t0.f23443d) {
            c20034t0.f23443d = z12;
            if (!c20034t0.f23443d) {
                c20034t0.b();
            }
        }
        Z0 z02 = this.f23395i;
        if (z02.f23099f != z12) {
            z02.f23099f = z12;
            if (!z12) {
                synchronized (z02.f23096c) {
                    z02.f23096c.f(1.0f);
                    q0F = androidx.camera.core.internal.e.f(z02.f23096c);
                }
                z02.c(q0F);
                z02.f23098e.l0();
                z02.f23094a.w();
            }
        }
        W0 w02 = this.f23396j;
        if (w02.f23082e != z12) {
            w02.f23082e = z12;
            if (!z12) {
                if (w02.f23084g) {
                    w02.f23084g = false;
                    w02.f23078a.o(false);
                    W0.b(w02.f23079b, 0);
                }
                b.a<Void> aVar = w02.f23083f;
                if (aVar != null) {
                    aVar.d(new CameraControl.OperationCanceledException("Camera is not active."));
                    w02.f23083f = null;
                }
            }
        }
        C20021m0 c20021m0 = this.f23397k;
        if (z12 != c20021m0.f23375d) {
            c20021m0.f23375d = z12;
            if (!z12) {
                C20023n0 c20023n0 = c20021m0.f23373b;
                synchronized (c20023n0.f23380a) {
                    c20023n0.f23382c = 0;
                }
                c20021m0.a();
            }
        }
        D.e eVar = this.f23399m;
        eVar.getClass();
        eVar.f2833d.execute(new D.b(0, eVar, z12));
    }

    public final long w() {
        this.f23409w = this.f23406t.getAndIncrement();
        C20039w.this.K();
        return this.f23409w;
    }
}
