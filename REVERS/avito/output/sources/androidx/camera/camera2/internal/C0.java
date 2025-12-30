package androidx.camera.camera2.internal;

import D.h;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.impl.b;
import androidx.camera.camera2.internal.C0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.C0;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.SessionConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import q.InterfaceC47174a;

/* compiled from: ProcessingCaptureSession.java */
@j.S
@j.X
/* loaded from: classes.dex */
final class C0 implements InterfaceC20007f0 {

    /* renamed from: m, reason: collision with root package name */
    public static int f22912m;

    /* renamed from: a, reason: collision with root package name */
    public final androidx.camera.core.impl.C0 f22913a;

    /* renamed from: b, reason: collision with root package name */
    public final C20045z f22914b;

    /* renamed from: c, reason: collision with root package name */
    public final Executor f22915c;

    /* renamed from: d, reason: collision with root package name */
    public final ScheduledExecutorService f22916d;

    /* renamed from: e, reason: collision with root package name */
    public final C20003d0 f22917e;

    /* renamed from: g, reason: collision with root package name */
    @j.P
    public SessionConfig f22919g;

    /* renamed from: h, reason: collision with root package name */
    @j.P
    public N f22920h;

    /* renamed from: f, reason: collision with root package name */
    public List<DeferrableSurface> f22918f = new ArrayList();

    /* renamed from: j, reason: collision with root package name */
    @j.P
    public volatile List<androidx.camera.core.impl.H> f22922j = null;

    /* renamed from: k, reason: collision with root package name */
    public D.h f22923k = new h.a().a();

    /* renamed from: l, reason: collision with root package name */
    public D.h f22924l = new h.a().a();

    /* renamed from: i, reason: collision with root package name */
    public c f22921i = c.f22926b;

    /* compiled from: ProcessingCaptureSession.java */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {
        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            C20140q0.c("ProcessingCaptureSession");
            throw null;
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(@j.P Void r12) {
        }
    }

    /* compiled from: ProcessingCaptureSession.java */
    public static /* synthetic */ class b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f22925a;

        static {
            int[] iArr = new int[c.values().length];
            f22925a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f22925a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f22925a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f22925a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f22925a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: ProcessingCaptureSession.java */
    public static final class c {

        /* renamed from: b, reason: collision with root package name */
        public static final c f22926b;

        /* renamed from: c, reason: collision with root package name */
        public static final c f22927c;

        /* renamed from: d, reason: collision with root package name */
        public static final c f22928d;

        /* renamed from: e, reason: collision with root package name */
        public static final c f22929e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ c[] f22930f;

        static {
            c cVar = new c("UNINITIALIZED", 0);
            f22926b = cVar;
            c cVar2 = new c("SESSION_INITIALIZED", 1);
            f22927c = cVar2;
            c cVar3 = new c("ON_CAPTURE_SESSION_STARTED", 2);
            f22928d = cVar3;
            c cVar4 = new c("ON_CAPTURE_SESSION_ENDED", 3);
            c cVar5 = new c("DE_INITIALIZED", 4);
            f22929e = cVar5;
            f22930f = new c[]{cVar, cVar2, cVar3, cVar4, cVar5};
        }

        public c() {
            throw null;
        }

        public static c valueOf(String str) {
            return (c) Enum.valueOf(c.class, str);
        }

        public static c[] values() {
            return (c[]) f22930f.clone();
        }
    }

    static {
        new ArrayList();
        f22912m = 0;
    }

    public C0(@j.N androidx.camera.core.impl.C0 c02, @j.N C20045z c20045z, @j.N androidx.camera.camera2.internal.compat.params.b bVar, @j.N Executor executor, @j.N ScheduledExecutorService scheduledExecutorService) {
        this.f22917e = new C20003d0(bVar);
        this.f22913a = c02;
        this.f22914b = c20045z;
        this.f22915c = executor;
        this.f22916d = scheduledExecutorService;
        new d();
        f22912m++;
        C20140q0.d(3, "ProcessingCaptureSession");
    }

    public static void h(@j.N List<androidx.camera.core.impl.H> list) {
        Iterator<androidx.camera.core.impl.H> it = list.iterator();
        while (it.hasNext()) {
            Iterator<AbstractC20109p> it2 = it.next().f23972e.iterator();
            while (it2.hasNext()) {
                it2.next().a();
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void b(@j.N List<androidx.camera.core.impl.H> list) {
        androidx.camera.core.impl.n0 n0Var;
        if (list.isEmpty()) {
            return;
        }
        Objects.toString(this.f22921i);
        C20140q0.d(3, "ProcessingCaptureSession");
        int iOrdinal = this.f22921i.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            this.f22922j = list;
            return;
        }
        if (iOrdinal != 2) {
            if (iOrdinal == 3 || iOrdinal == 4) {
                Objects.toString(this.f22921i);
                C20140q0.d(3, "ProcessingCaptureSession");
                h(list);
                return;
            }
            return;
        }
        for (androidx.camera.core.impl.H h12 : list) {
            if (h12.f23970c == 2) {
                h.a aVarC = h.a.c(h12.f23969b);
                Config.a<Integer> aVar = androidx.camera.core.impl.H.f23965i;
                androidx.camera.core.impl.s0 s0Var = h12.f23969b;
                if (s0Var.f24173F.containsKey(aVar)) {
                    CaptureRequest.Key key = CaptureRequest.JPEG_ORIENTATION;
                    Integer num = (Integer) s0Var.f(aVar);
                    aVarC.f2840a.K(androidx.camera.camera2.impl.b.Q(key), num);
                }
                Config.a<Integer> aVar2 = androidx.camera.core.impl.H.f23966j;
                if (s0Var.f24173F.containsKey(aVar2)) {
                    CaptureRequest.Key key2 = CaptureRequest.JPEG_QUALITY;
                    Byte bValueOf = Byte.valueOf(((Integer) s0Var.f(aVar2)).byteValue());
                    aVarC.f2840a.K(androidx.camera.camera2.impl.b.Q(key2), bValueOf);
                }
                D.h hVarA = aVarC.a();
                this.f22924l = hVarA;
                D.h hVar = this.f22923k;
                b.a aVar3 = new b.a();
                Iterator<Config.a<?>> it = hVar.e().iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    n0Var = aVar3.f22897a;
                    if (!zHasNext) {
                        break;
                    }
                    Config.a<?> next = it.next();
                    n0Var.K(next, hVar.f(next));
                }
                for (Config.a<?> aVar4 : hVarA.e()) {
                    n0Var.K(aVar4, hVarA.f(aVar4));
                }
                this.f22913a.g(aVar3.a());
                throw null;
            }
            C20140q0.d(3, "ProcessingCaptureSession");
            D.h hVarA2 = h.a.c(h12.f23969b).a();
            Iterator<Config.a<?>> it2 = hVarA2.e().iterator();
            while (it2.hasNext()) {
                CaptureRequest.Key key3 = (CaptureRequest.Key) it2.next().d();
                if (key3.equals(CaptureRequest.CONTROL_AF_TRIGGER) || key3.equals(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER)) {
                    this.f22913a.d(hVarA2, new E0(this, h12));
                    break;
                }
            }
            h(Arrays.asList(h12));
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final com.google.common.util.concurrent.D0<Void> c(@j.N SessionConfig sessionConfig, @j.N CameraDevice cameraDevice, @j.N Q0 q02) {
        androidx.core.util.z.a("Invalid state state:" + this.f22921i, this.f22921i == c.f22926b);
        androidx.core.util.z.a("SessionConfig contains no surfaces", sessionConfig.b().isEmpty() ^ true);
        C20140q0.d(3, "ProcessingCaptureSession");
        List<DeferrableSurface> listB = sessionConfig.b();
        this.f22918f = listB;
        Executor executor = this.f22915c;
        androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(androidx.camera.core.impl.O.c(listB, executor, this.f22916d));
        C20025o0 c20025o0 = new C20025o0(this, sessionConfig, cameraDevice, q02);
        dVarA.getClass();
        return (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.l((androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, c20025o0, executor), new InterfaceC47174a() { // from class: androidx.camera.camera2.internal.B0
            @Override // q.InterfaceC47174a
            public final Object apply(Object obj) {
                C0 c02 = this.f22909a;
                C20003d0 c20003d0 = c02.f22917e;
                androidx.core.util.z.a("Invalid state state:" + c02.f22921i, c02.f22921i == C0.c.f22927c);
                throw null;
            }
        }, executor);
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void close() {
        Objects.toString(this.f22921i);
        C20140q0.d(3, "ProcessingCaptureSession");
        if (this.f22921i != c.f22928d) {
            this.f22917e.close();
        } else {
            C20140q0.d(3, "ProcessingCaptureSession");
            this.f22913a.a();
            throw null;
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.P
    public final SessionConfig d() {
        return this.f22919g;
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void e(@j.P SessionConfig sessionConfig) {
        androidx.camera.core.impl.n0 n0Var;
        C20140q0.d(3, "ProcessingCaptureSession");
        this.f22919g = sessionConfig;
        if (sessionConfig == null) {
            return;
        }
        N n12 = this.f22920h;
        if (n12 != null) {
            n12.f23025d = sessionConfig;
        }
        if (this.f22921i == c.f22928d) {
            D.h hVarA = h.a.c(sessionConfig.f24017f.f23969b).a();
            this.f22923k = hVarA;
            D.h hVar = this.f22924l;
            b.a aVar = new b.a();
            Iterator<Config.a<?>> it = hVarA.e().iterator();
            while (true) {
                boolean zHasNext = it.hasNext();
                n0Var = aVar.f22897a;
                if (!zHasNext) {
                    break;
                }
                Config.a<?> next = it.next();
                n0Var.K(next, hVarA.f(next));
            }
            for (Config.a<?> aVar2 : hVar.e()) {
                n0Var.K(aVar2, hVar.f(aVar2));
            }
            this.f22913a.g(aVar.a());
            throw null;
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void f() {
        C20140q0.d(3, "ProcessingCaptureSession");
        if (this.f22922j != null) {
            Iterator<androidx.camera.core.impl.H> it = this.f22922j.iterator();
            while (it.hasNext()) {
                Iterator<AbstractC20109p> it2 = it.next().f23972e.iterator();
                while (it2.hasNext()) {
                    it2.next().a();
                }
            }
            this.f22922j = null;
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final List<androidx.camera.core.impl.H> g() {
        return this.f22922j != null ? this.f22922j : Collections.emptyList();
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final com.google.common.util.concurrent.D0 release() {
        Objects.toString(this.f22921i);
        C20140q0.d(3, "ProcessingCaptureSession");
        com.google.common.util.concurrent.D0 d0Release = this.f22917e.release();
        int iOrdinal = this.f22921i.ordinal();
        if (iOrdinal == 1 || iOrdinal == 3) {
            d0Release.N(new RunnableC20041x(this, 3), androidx.camera.core.impl.utils.executor.c.a());
        }
        this.f22921i = c.f22929e;
        return d0Release;
    }

    /* compiled from: ProcessingCaptureSession.java */
    public static class d implements C0.a {
        @Override // androidx.camera.core.impl.C0.a
        public final void a() {
        }

        @Override // androidx.camera.core.impl.C0.a
        public final void b() {
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void a(@j.N HashMap map) {
    }
}
