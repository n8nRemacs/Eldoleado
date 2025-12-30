package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.os.Build;
import android.view.Surface;
import androidx.camera.camera2.impl.d;
import androidx.camera.camera2.internal.A;
import androidx.camera.camera2.internal.K0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.AbstractC20109p;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.H;
import androidx.camera.core.impl.SessionConfig;
import androidx.concurrent.futures.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

/* compiled from: CaptureSession.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.d0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20003d0 implements InterfaceC20007f0 {

    /* renamed from: e, reason: collision with root package name */
    @j.B
    @j.P
    public Q0 f23287e;

    /* renamed from: f, reason: collision with root package name */
    @j.B
    @j.P
    public K0 f23288f;

    /* renamed from: g, reason: collision with root package name */
    @j.B
    @j.P
    public SessionConfig f23289g;

    /* renamed from: l, reason: collision with root package name */
    @j.B
    public d f23294l;

    /* renamed from: m, reason: collision with root package name */
    @j.B
    public com.google.common.util.concurrent.D0<Void> f23295m;

    /* renamed from: n, reason: collision with root package name */
    @j.B
    public b.a<Void> f23296n;

    /* renamed from: r, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.params.b f23300r;

    /* renamed from: a, reason: collision with root package name */
    public final Object f23283a = new Object();

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public final ArrayList f23284b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    public final CameraCaptureSession.CaptureCallback f23285c = new a();

    /* renamed from: h, reason: collision with root package name */
    @j.B
    @j.N
    public androidx.camera.core.impl.s0 f23290h = androidx.camera.core.impl.s0.f24172H;

    /* renamed from: i, reason: collision with root package name */
    @j.B
    @j.N
    public androidx.camera.camera2.impl.d f23291i = androidx.camera.camera2.impl.d.b();

    /* renamed from: j, reason: collision with root package name */
    @j.B
    public final HashMap f23292j = new HashMap();

    /* renamed from: k, reason: collision with root package name */
    @j.B
    public List<DeferrableSurface> f23293k = Collections.emptyList();

    /* renamed from: o, reason: collision with root package name */
    @j.B
    @j.N
    public HashMap f23297o = new HashMap();

    /* renamed from: p, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.q f23298p = new androidx.camera.camera2.internal.compat.workaround.q();

    /* renamed from: q, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.workaround.t f23299q = new androidx.camera.camera2.internal.compat.workaround.t();

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public final e f23286d = new e();

    /* compiled from: CaptureSession.java */
    /* renamed from: androidx.camera.camera2.internal.d0$b */
    public class b implements androidx.camera.core.impl.utils.futures.c<Void> {
        public b() {
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            synchronized (C20003d0.this.f23283a) {
                try {
                    C20003d0.this.f23287e.f23056a.stop();
                    int iOrdinal = C20003d0.this.f23294l.ordinal();
                    if ((iOrdinal == 3 || iOrdinal == 5 || iOrdinal == 6) && !(th2 instanceof CancellationException)) {
                        Objects.toString(C20003d0.this.f23294l);
                        C20140q0.d(5, "CaptureSession");
                        C20003d0.this.i();
                    }
                } finally {
                }
            }
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final /* bridge */ /* synthetic */ void onSuccess(@j.P Void r12) {
        }
    }

    /* compiled from: CaptureSession.java */
    /* renamed from: androidx.camera.camera2.internal.d0$c */
    public static /* synthetic */ class c {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23302a;

        static {
            int[] iArr = new int[d.values().length];
            f23302a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23302a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23302a[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f23302a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f23302a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f23302a[5] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f23302a[6] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f23302a[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: CaptureSession.java */
    /* renamed from: androidx.camera.camera2.internal.d0$d */
    public static final class d {

        /* renamed from: b, reason: collision with root package name */
        public static final d f23303b;

        /* renamed from: c, reason: collision with root package name */
        public static final d f23304c;

        /* renamed from: d, reason: collision with root package name */
        public static final d f23305d;

        /* renamed from: e, reason: collision with root package name */
        public static final d f23306e;

        /* renamed from: f, reason: collision with root package name */
        public static final d f23307f;

        /* renamed from: g, reason: collision with root package name */
        public static final d f23308g;

        /* renamed from: h, reason: collision with root package name */
        public static final d f23309h;

        /* renamed from: i, reason: collision with root package name */
        public static final d f23310i;

        /* renamed from: j, reason: collision with root package name */
        public static final /* synthetic */ d[] f23311j;

        static {
            d dVar = new d("UNINITIALIZED", 0);
            f23303b = dVar;
            d dVar2 = new d("INITIALIZED", 1);
            f23304c = dVar2;
            d dVar3 = new d("GET_SURFACE", 2);
            f23305d = dVar3;
            d dVar4 = new d("OPENING", 3);
            f23306e = dVar4;
            d dVar5 = new d("OPENED", 4);
            f23307f = dVar5;
            d dVar6 = new d("CLOSED", 5);
            f23308g = dVar6;
            d dVar7 = new d("RELEASING", 6);
            f23309h = dVar7;
            d dVar8 = new d("RELEASED", 7);
            f23310i = dVar8;
            f23311j = new d[]{dVar, dVar2, dVar3, dVar4, dVar5, dVar6, dVar7, dVar8};
        }

        public d() {
            throw null;
        }

        public static d valueOf(String str) {
            return (d) Enum.valueOf(d.class, str);
        }

        public static d[] values() {
            return (d[]) f23311j.clone();
        }
    }

    /* compiled from: CaptureSession.java */
    /* renamed from: androidx.camera.camera2.internal.d0$e */
    public final class e extends K0.a {
        public e() {
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void o(@j.N K0 k02) {
            synchronized (C20003d0.this.f23283a) {
                try {
                    switch (C20003d0.this.f23294l.ordinal()) {
                        case 0:
                        case 1:
                        case 2:
                        case 4:
                            throw new IllegalStateException("onConfigureFailed() should not be possible in state: " + C20003d0.this.f23294l);
                        case 3:
                        case 5:
                        case 6:
                            C20003d0.this.i();
                            break;
                        case 7:
                            C20140q0.d(3, "CaptureSession");
                            break;
                    }
                    Objects.toString(C20003d0.this.f23294l);
                    C20140q0.b("CaptureSession");
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void p(@j.N K0 k02) {
            synchronized (C20003d0.this.f23283a) {
                try {
                    switch (C20003d0.this.f23294l.ordinal()) {
                        case 0:
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                            throw new IllegalStateException("onConfigured() should not be possible in state: " + C20003d0.this.f23294l);
                        case 3:
                            C20003d0 c20003d0 = C20003d0.this;
                            c20003d0.f23294l = d.f23307f;
                            c20003d0.f23288f = k02;
                            if (c20003d0.f23289g != null) {
                                androidx.camera.camera2.impl.d dVar = c20003d0.f23291i;
                                dVar.getClass();
                                d.a aVar = new d.a(Collections.unmodifiableList(new ArrayList(dVar.f24151a)));
                                ArrayList arrayList = new ArrayList();
                                Iterator it = aVar.f22898a.iterator();
                                while (it.hasNext()) {
                                    ((androidx.camera.camera2.impl.c) it.next()).getClass();
                                }
                                if (!arrayList.isEmpty()) {
                                    C20003d0 c20003d02 = C20003d0.this;
                                    c20003d02.k(c20003d02.n(arrayList));
                                }
                            }
                            C20140q0.d(3, "CaptureSession");
                            C20003d0 c20003d03 = C20003d0.this;
                            c20003d03.l(c20003d03.f23289g);
                            C20003d0 c20003d04 = C20003d0.this;
                            ArrayList arrayList2 = c20003d04.f23284b;
                            if (!arrayList2.isEmpty()) {
                                try {
                                    c20003d04.k(arrayList2);
                                } finally {
                                    arrayList2.clear();
                                }
                            }
                            Objects.toString(C20003d0.this.f23294l);
                            C20140q0.d(3, "CaptureSession");
                            break;
                        case 5:
                            C20003d0.this.f23288f = k02;
                            Objects.toString(C20003d0.this.f23294l);
                            C20140q0.d(3, "CaptureSession");
                            break;
                        case 6:
                            k02.close();
                            Objects.toString(C20003d0.this.f23294l);
                            C20140q0.d(3, "CaptureSession");
                            break;
                        default:
                            Objects.toString(C20003d0.this.f23294l);
                            C20140q0.d(3, "CaptureSession");
                            break;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
                throw th2;
            }
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void q(@j.N K0 k02) {
            synchronized (C20003d0.this.f23283a) {
                try {
                    if (C20003d0.this.f23294l.ordinal() == 0) {
                        throw new IllegalStateException("onReady() should not be possible in state: " + C20003d0.this.f23294l);
                    }
                    Objects.toString(C20003d0.this.f23294l);
                    C20140q0.d(3, "CaptureSession");
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // androidx.camera.camera2.internal.K0.a
        public final void r(@j.N K0 k02) {
            synchronized (C20003d0.this.f23283a) {
                try {
                    if (C20003d0.this.f23294l == d.f23303b) {
                        throw new IllegalStateException("onSessionFinished() should not be possible in state: " + C20003d0.this.f23294l);
                    }
                    C20140q0.d(3, "CaptureSession");
                    C20003d0.this.i();
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    public C20003d0(@j.N androidx.camera.camera2.internal.compat.params.b bVar) {
        this.f23294l = d.f23303b;
        this.f23294l = d.f23304c;
        this.f23300r = bVar;
    }

    @j.B
    public static CameraCaptureSession.CaptureCallback h(List list, CameraCaptureSession.CaptureCallback... captureCallbackArr) {
        CameraCaptureSession.CaptureCallback aVar;
        ArrayList arrayList = new ArrayList(list.size() + captureCallbackArr.length);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC20109p abstractC20109p = (AbstractC20109p) it.next();
            if (abstractC20109p == null) {
                aVar = null;
            } else {
                ArrayList arrayList2 = new ArrayList();
                C19980a0.a(abstractC20109p, arrayList2);
                aVar = arrayList2.size() == 1 ? (CameraCaptureSession.CaptureCallback) arrayList2.get(0) : new A.a(arrayList2);
            }
            arrayList.add(aVar);
        }
        Collections.addAll(arrayList, captureCallbackArr);
        return new A.a(arrayList);
    }

    @j.N
    public static androidx.camera.core.impl.n0 m(ArrayList arrayList) {
        Object objF;
        androidx.camera.core.impl.n0 n0VarR = androidx.camera.core.impl.n0.R();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            androidx.camera.core.impl.s0 s0Var = ((androidx.camera.core.impl.H) it.next()).f23969b;
            for (Config.a<?> aVar : s0Var.e()) {
                Object objF2 = null;
                try {
                    objF = s0Var.f(aVar);
                } catch (IllegalArgumentException unused) {
                    objF = null;
                }
                if (n0VarR.f24173F.containsKey(aVar)) {
                    try {
                        objF2 = n0VarR.f(aVar);
                    } catch (IllegalArgumentException unused2) {
                    }
                    if (!Objects.equals(objF2, objF)) {
                        aVar.getClass();
                        Objects.toString(objF);
                        Objects.toString(objF2);
                        C20140q0.d(3, "CaptureSession");
                    }
                } else {
                    n0VarR.K(aVar, objF);
                }
            }
        }
        return n0VarR;
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void a(@j.N HashMap map) {
        synchronized (this.f23283a) {
            this.f23297o = map;
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void b(@j.N List<androidx.camera.core.impl.H> list) {
        synchronized (this.f23283a) {
            try {
                switch (this.f23294l.ordinal()) {
                    case 0:
                        throw new IllegalStateException("issueCaptureRequests() should not be possible in state: " + this.f23294l);
                    case 1:
                    case 2:
                    case 3:
                        this.f23284b.addAll(list);
                        break;
                    case 4:
                        this.f23284b.addAll(list);
                        ArrayList arrayList = this.f23284b;
                        if (!arrayList.isEmpty()) {
                            try {
                                k(arrayList);
                                arrayList.clear();
                            } catch (Throwable th2) {
                                arrayList.clear();
                                throw th2;
                            }
                        }
                        break;
                    case 5:
                    case 6:
                    case 7:
                        throw new IllegalStateException("Cannot issue capture request on a closed/released session.");
                }
            } finally {
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final com.google.common.util.concurrent.D0<Void> c(@j.N final SessionConfig sessionConfig, @j.N final CameraDevice cameraDevice, @j.N Q0 q02) {
        synchronized (this.f23283a) {
            try {
                if (this.f23294l.ordinal() != 1) {
                    Objects.toString(this.f23294l);
                    C20140q0.b("CaptureSession");
                    return androidx.camera.core.impl.utils.futures.f.e(new IllegalStateException("open() should not allow the state: " + this.f23294l));
                }
                this.f23294l = d.f23305d;
                ArrayList arrayList = new ArrayList(sessionConfig.b());
                this.f23293k = arrayList;
                this.f23287e = q02;
                androidx.camera.core.impl.utils.futures.d dVarA = androidx.camera.core.impl.utils.futures.d.a(q02.f23056a.f(arrayList));
                androidx.camera.core.impl.utils.futures.a aVar = new androidx.camera.core.impl.utils.futures.a() { // from class: androidx.camera.camera2.internal.c0
                    /* JADX WARN: Removed duplicated region for block: B:61:0x01b2 A[Catch: all -> 0x003b, TryCatch #1 {all -> 0x003b, CameraAccessException -> 0x019e, blocks: (B:4:0x0012, B:10:0x0021, B:11:0x0038, B:15:0x003e, B:16:0x0044, B:18:0x004a, B:19:0x005f, B:20:0x00b6, B:22:0x00bc, B:23:0x00c6, B:24:0x00d1, B:26:0x00d7, B:27:0x00e3, B:28:0x00f9, B:30:0x00ff, B:32:0x0117, B:33:0x012a, B:34:0x012e, B:35:0x013c, B:37:0x0142, B:40:0x0153, B:41:0x015e, B:43:0x0177, B:45:0x017b, B:46:0x0182, B:51:0x019a, B:55:0x01a0, B:56:0x01aa, B:49:0x0189, B:58:0x01ac, B:59:0x01b0, B:61:0x01b2, B:62:0x01c9), top: B:68:0x0012 }] */
                    @Override // androidx.camera.core.impl.utils.futures.a, com.google.common.base.InterfaceC37276u
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public final com.google.common.util.concurrent.D0 apply(java.lang.Object r14) {
                        /*
                            Method dump skipped, instructions count: 461
                            To view this dump add '--comments-level debug' option
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.C19984c0.apply(java.lang.Object):com.google.common.util.concurrent.D0");
                    }
                };
                Executor executor = this.f23287e.f23056a.f23033d;
                dVarA.getClass();
                androidx.camera.core.impl.utils.futures.d dVar = (androidx.camera.core.impl.utils.futures.d) androidx.camera.core.impl.utils.futures.f.m(dVarA, aVar, executor);
                androidx.camera.core.impl.utils.futures.f.a(dVar, new b(), this.f23287e.f23056a.f23033d);
                return androidx.camera.core.impl.utils.futures.f.h(dVar);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void close() {
        synchronized (this.f23283a) {
            int iOrdinal = this.f23294l.ordinal();
            if (iOrdinal == 0) {
                throw new IllegalStateException("close() should not be possible in state: " + this.f23294l);
            }
            if (iOrdinal == 1) {
                this.f23294l = d.f23310i;
            } else if (iOrdinal == 2) {
                androidx.core.util.z.f(this.f23287e, "The Opener shouldn't null in state:" + this.f23294l);
                this.f23287e.f23056a.stop();
                this.f23294l = d.f23310i;
            } else if (iOrdinal == 3) {
                androidx.core.util.z.f(this.f23287e, "The Opener shouldn't null in state:" + this.f23294l);
                this.f23287e.f23056a.stop();
                this.f23294l = d.f23308g;
                this.f23289g = null;
            } else if (iOrdinal == 4) {
                if (this.f23289g != null) {
                    androidx.camera.camera2.impl.d dVar = this.f23291i;
                    dVar.getClass();
                    d.a aVar = new d.a(Collections.unmodifiableList(new ArrayList(dVar.f24151a)));
                    ArrayList arrayList = new ArrayList();
                    Iterator it = aVar.f22898a.iterator();
                    while (it.hasNext()) {
                        ((androidx.camera.camera2.impl.c) it.next()).getClass();
                    }
                    if (!arrayList.isEmpty()) {
                        try {
                            b(n(arrayList));
                        } catch (IllegalStateException unused) {
                            C20140q0.c("CaptureSession");
                        }
                    }
                }
                androidx.core.util.z.f(this.f23287e, "The Opener shouldn't null in state:" + this.f23294l);
                this.f23287e.f23056a.stop();
                this.f23294l = d.f23308g;
                this.f23289g = null;
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.P
    public final SessionConfig d() {
        SessionConfig sessionConfig;
        synchronized (this.f23283a) {
            sessionConfig = this.f23289g;
        }
        return sessionConfig;
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void e(@j.P SessionConfig sessionConfig) {
        synchronized (this.f23283a) {
            try {
                switch (this.f23294l.ordinal()) {
                    case 0:
                        throw new IllegalStateException("setSessionConfig() should not be possible in state: " + this.f23294l);
                    case 1:
                    case 2:
                    case 3:
                        this.f23289g = sessionConfig;
                        break;
                    case 4:
                        this.f23289g = sessionConfig;
                        if (sessionConfig != null) {
                            if (!this.f23292j.keySet().containsAll(sessionConfig.b())) {
                                C20140q0.b("CaptureSession");
                                return;
                            } else {
                                C20140q0.d(3, "CaptureSession");
                                l(this.f23289g);
                                break;
                            }
                        } else {
                            return;
                        }
                    case 5:
                    case 6:
                    case 7:
                        throw new IllegalStateException("Session configuration cannot be set on a closed/released session.");
                }
            } finally {
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    public final void f() {
        ArrayList arrayList;
        synchronized (this.f23283a) {
            try {
                if (this.f23284b.isEmpty()) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(this.f23284b);
                    this.f23284b.clear();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Iterator<AbstractC20109p> it2 = ((androidx.camera.core.impl.H) it.next()).f23972e.iterator();
                while (it2.hasNext()) {
                    it2.next().a();
                }
            }
        }
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final List<androidx.camera.core.impl.H> g() {
        List<androidx.camera.core.impl.H> listUnmodifiableList;
        synchronized (this.f23283a) {
            listUnmodifiableList = Collections.unmodifiableList(this.f23284b);
        }
        return listUnmodifiableList;
    }

    @j.B
    public final void i() {
        d dVar = this.f23294l;
        d dVar2 = d.f23310i;
        if (dVar == dVar2) {
            C20140q0.d(3, "CaptureSession");
            return;
        }
        this.f23294l = dVar2;
        this.f23288f = null;
        b.a<Void> aVar = this.f23296n;
        if (aVar != null) {
            aVar.b(null);
            this.f23296n = null;
        }
    }

    @j.N
    public final androidx.camera.camera2.internal.compat.params.f j(@j.N SessionConfig.e eVar, @j.N HashMap map, @j.P String str) {
        long jLongValue;
        DynamicRangeProfiles dynamicRangeProfilesD;
        Surface surface = (Surface) map.get(eVar.e());
        androidx.core.util.z.f(surface, "Surface in OutputConfig not found in configuredSurfaceMap.");
        androidx.camera.camera2.internal.compat.params.f fVar = new androidx.camera.camera2.internal.compat.params.f(eVar.f(), surface);
        if (str != null) {
            fVar.f(str);
        } else {
            fVar.f(eVar.c());
        }
        if (!eVar.d().isEmpty()) {
            fVar.b();
            Iterator<DeferrableSurface> it = eVar.d().iterator();
            while (it.hasNext()) {
                Surface surface2 = (Surface) map.get(it.next());
                androidx.core.util.z.f(surface2, "Surface in OutputConfig not found in configuredSurfaceMap.");
                fVar.a(surface2);
            }
        }
        if (Build.VERSION.SDK_INT < 33 || (dynamicRangeProfilesD = this.f23300r.d()) == null) {
            jLongValue = 1;
        } else {
            androidx.camera.core.F fB2 = eVar.b();
            Long lA2 = androidx.camera.camera2.internal.compat.params.a.a(fB2, dynamicRangeProfilesD);
            if (lA2 == null) {
                Objects.toString(fB2);
                C20140q0.b("CaptureSession");
                jLongValue = 1;
            } else {
                jLongValue = lA2.longValue();
            }
        }
        fVar.e(jLongValue);
        return fVar;
    }

    public final int k(ArrayList arrayList) {
        Q q12;
        ArrayList arrayList2;
        boolean z12;
        androidx.camera.core.impl.r rVar;
        synchronized (this.f23283a) {
            try {
                if (this.f23294l != d.f23307f) {
                    C20140q0.d(3, "CaptureSession");
                    return -1;
                }
                if (arrayList.isEmpty()) {
                    return -1;
                }
                try {
                    q12 = new Q();
                    arrayList2 = new ArrayList();
                    C20140q0.d(3, "CaptureSession");
                    Iterator it = arrayList.iterator();
                    z12 = false;
                    while (it.hasNext()) {
                        androidx.camera.core.impl.H h12 = (androidx.camera.core.impl.H) it.next();
                        if (Collections.unmodifiableList(h12.f23968a).isEmpty()) {
                            C20140q0.d(3, "CaptureSession");
                        } else {
                            Iterator it2 = Collections.unmodifiableList(h12.f23968a).iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    DeferrableSurface deferrableSurface = (DeferrableSurface) it2.next();
                                    if (!this.f23292j.containsKey(deferrableSurface)) {
                                        Objects.toString(deferrableSurface);
                                        C20140q0.d(3, "CaptureSession");
                                        break;
                                    }
                                } else {
                                    if (h12.f23970c == 2) {
                                        z12 = true;
                                    }
                                    H.a aVar = new H.a(h12);
                                    if (h12.f23970c == 5 && (rVar = h12.f23975h) != null) {
                                        aVar.f23983h = rVar;
                                    }
                                    SessionConfig sessionConfig = this.f23289g;
                                    if (sessionConfig != null) {
                                        aVar.c(sessionConfig.f24017f.f23969b);
                                    }
                                    aVar.c(this.f23290h);
                                    aVar.c(h12.f23969b);
                                    CaptureRequest captureRequestB = K.b(aVar.e(), this.f23288f.d(), this.f23292j);
                                    if (captureRequestB == null) {
                                        C20140q0.d(3, "CaptureSession");
                                        return -1;
                                    }
                                    ArrayList arrayList3 = new ArrayList();
                                    Iterator<AbstractC20109p> it3 = h12.f23972e.iterator();
                                    while (it3.hasNext()) {
                                        C19980a0.a(it3.next(), arrayList3);
                                    }
                                    q12.a(captureRequestB, arrayList3);
                                    arrayList2.add(captureRequestB);
                                }
                            }
                        }
                    }
                } catch (CameraAccessException e12) {
                    e12.getMessage();
                    C20140q0.b("CaptureSession");
                    Thread.dumpStack();
                }
                if (arrayList2.isEmpty()) {
                    C20140q0.d(3, "CaptureSession");
                    return -1;
                }
                if (this.f23298p.a(arrayList2, z12)) {
                    this.f23288f.b();
                    q12.f23055b = new C19982b0(this);
                }
                if (this.f23299q.b(arrayList2, z12)) {
                    q12.a((CaptureRequest) arrayList2.get(arrayList2.size() - 1), Collections.singletonList(new C20005e0(this)));
                }
                return this.f23288f.j(arrayList2, q12);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final int l(@j.P SessionConfig sessionConfig) {
        synchronized (this.f23283a) {
            try {
            } catch (Throwable th2) {
                throw th2;
            }
            if (sessionConfig == null) {
                C20140q0.d(3, "CaptureSession");
                return -1;
            }
            if (this.f23294l != d.f23307f) {
                C20140q0.d(3, "CaptureSession");
                return -1;
            }
            androidx.camera.core.impl.H h12 = sessionConfig.f24017f;
            if (Collections.unmodifiableList(h12.f23968a).isEmpty()) {
                C20140q0.d(3, "CaptureSession");
                try {
                    this.f23288f.b();
                } catch (CameraAccessException e12) {
                    e12.getMessage();
                    C20140q0.b("CaptureSession");
                    Thread.dumpStack();
                }
                return -1;
            }
            try {
                C20140q0.d(3, "CaptureSession");
                H.a aVar = new H.a(h12);
                androidx.camera.camera2.impl.d dVar = this.f23291i;
                dVar.getClass();
                androidx.camera.core.impl.n0 n0VarM = m(new d.a(Collections.unmodifiableList(new ArrayList(dVar.f24151a))).a());
                this.f23290h = n0VarM;
                aVar.c(n0VarM);
                CaptureRequest captureRequestB = K.b(aVar.e(), this.f23288f.d(), this.f23292j);
                if (captureRequestB == null) {
                    C20140q0.d(3, "CaptureSession");
                    return -1;
                }
                return this.f23288f.k(captureRequestB, h(h12.f23972e, this.f23285c));
            } catch (CameraAccessException e13) {
                e13.getMessage();
                C20140q0.b("CaptureSession");
                Thread.dumpStack();
                return -1;
            }
            throw th2;
        }
    }

    @j.B
    public final ArrayList n(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            H.a aVar = new H.a((androidx.camera.core.impl.H) it.next());
            aVar.f23978c = 1;
            Iterator it2 = Collections.unmodifiableList(this.f23289g.f24017f.f23968a).iterator();
            while (it2.hasNext()) {
                aVar.d((DeferrableSurface) it2.next());
            }
            arrayList2.add(aVar.e());
        }
        return arrayList2;
    }

    @Override // androidx.camera.camera2.internal.InterfaceC20007f0
    @j.N
    public final com.google.common.util.concurrent.D0 release() {
        synchronized (this.f23283a) {
            try {
                switch (this.f23294l.ordinal()) {
                    case 0:
                        throw new IllegalStateException("release() should not be possible in state: " + this.f23294l);
                    case 2:
                        androidx.core.util.z.f(this.f23287e, "The Opener shouldn't null in state:" + this.f23294l);
                        this.f23287e.f23056a.stop();
                    case 1:
                        this.f23294l = d.f23310i;
                        return androidx.camera.core.impl.utils.futures.f.g(null);
                    case 4:
                    case 5:
                        K0 k02 = this.f23288f;
                        if (k02 != null) {
                            k02.close();
                        }
                    case 3:
                        androidx.camera.camera2.impl.d dVar = this.f23291i;
                        dVar.getClass();
                        Iterator it = new d.a(Collections.unmodifiableList(new ArrayList(dVar.f24151a))).f22898a.iterator();
                        while (it.hasNext()) {
                            ((androidx.camera.camera2.impl.c) it.next()).getClass();
                        }
                        this.f23294l = d.f23309h;
                        androidx.core.util.z.f(this.f23287e, "The Opener shouldn't null in state:" + this.f23294l);
                        if (this.f23287e.f23056a.stop()) {
                            i();
                            return androidx.camera.core.impl.utils.futures.f.g(null);
                        }
                    case 6:
                        if (this.f23295m == null) {
                            this.f23295m = androidx.concurrent.futures.b.a(new C19982b0(this));
                        }
                        return this.f23295m;
                    default:
                        return androidx.camera.core.impl.utils.futures.f.g(null);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* compiled from: CaptureSession.java */
    /* renamed from: androidx.camera.camera2.internal.d0$a */
    public class a extends CameraCaptureSession.CaptureCallback {
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(@j.N CameraCaptureSession cameraCaptureSession, @j.N CaptureRequest captureRequest, @j.N TotalCaptureResult totalCaptureResult) {
        }
    }
}
