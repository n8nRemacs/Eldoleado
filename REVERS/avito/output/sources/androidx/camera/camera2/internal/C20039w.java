package androidx.camera.camera2.internal;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Rational;
import android.util.Size;
import androidx.camera.camera2.internal.C20039w;
import androidx.camera.camera2.internal.Q0;
import androidx.camera.camera2.internal.U;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.quirk.C20001l;
import androidx.camera.core.C20140q0;
import androidx.camera.core.CameraState;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.C20102k0;
import androidx.camera.core.impl.C20115w;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.E;
import androidx.camera.core.impl.InterfaceC20112t;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.view.C23038g0;
import com.adjust.sdk.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Camera2CameraImpl.java */
@j.X
/* renamed from: androidx.camera.camera2.internal.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20039w implements CameraInternal {

    /* renamed from: A, reason: collision with root package name */
    public boolean f23463A;

    /* renamed from: B, reason: collision with root package name */
    @j.N
    public final C20013i0 f23464B;

    /* renamed from: C, reason: collision with root package name */
    @j.N
    public final androidx.camera.camera2.internal.compat.params.b f23465C;

    /* renamed from: b, reason: collision with root package name */
    public final androidx.camera.core.impl.K0 f23466b;

    /* renamed from: c, reason: collision with root package name */
    public final androidx.camera.camera2.internal.compat.w f23467c;

    /* renamed from: d, reason: collision with root package name */
    public final Executor f23468d;

    /* renamed from: e, reason: collision with root package name */
    public final ScheduledExecutorService f23469e;

    /* renamed from: f, reason: collision with root package name */
    public volatile f f23470f = f.f23498b;

    /* renamed from: g, reason: collision with root package name */
    public final C20102k0<CameraInternal.State> f23471g;

    /* renamed from: h, reason: collision with root package name */
    public final W f23472h;

    /* renamed from: i, reason: collision with root package name */
    public final C20026p f23473i;

    /* renamed from: j, reason: collision with root package name */
    public final g f23474j;

    /* renamed from: k, reason: collision with root package name */
    @j.N
    public final C20045z f23475k;

    /* renamed from: l, reason: collision with root package name */
    @j.P
    public CameraDevice f23476l;

    /* renamed from: m, reason: collision with root package name */
    public int f23477m;

    /* renamed from: n, reason: collision with root package name */
    public InterfaceC20007f0 f23478n;

    /* renamed from: o, reason: collision with root package name */
    public final LinkedHashMap f23479o;

    /* renamed from: p, reason: collision with root package name */
    @j.N
    public final c f23480p;

    /* renamed from: q, reason: collision with root package name */
    @j.N
    public final C.a f23481q;

    /* renamed from: r, reason: collision with root package name */
    @j.N
    public final androidx.camera.core.impl.E f23482r;

    /* renamed from: s, reason: collision with root package name */
    public final HashSet f23483s;

    /* renamed from: t, reason: collision with root package name */
    public A0 f23484t;

    /* renamed from: u, reason: collision with root package name */
    @j.N
    public final C20009g0 f23485u;

    /* renamed from: v, reason: collision with root package name */
    @j.N
    public final Q0.a f23486v;

    /* renamed from: w, reason: collision with root package name */
    public final HashSet f23487w;

    /* renamed from: x, reason: collision with root package name */
    @j.N
    public InterfaceC20112t f23488x;

    /* renamed from: y, reason: collision with root package name */
    public final Object f23489y;

    /* renamed from: z, reason: collision with root package name */
    @j.B
    @j.P
    public androidx.camera.core.impl.C0 f23490z;

    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$a */
    public class a implements androidx.camera.core.impl.utils.futures.c<Void> {
        public a() {
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void a(@j.N Throwable th2) {
            SessionConfig next;
            if (!(th2 instanceof DeferrableSurface.SurfaceClosedException)) {
                if (th2 instanceof CancellationException) {
                    C20039w.this.s("Unable to configure camera cancelled");
                    return;
                }
                f fVar = C20039w.this.f23470f;
                f fVar2 = f.f23501e;
                if (fVar == fVar2) {
                    C20039w.this.F(fVar2, CameraState.a.b(4, th2), true);
                }
                if (th2 instanceof CameraAccessException) {
                    C20039w.this.s(G.i(th2, new StringBuilder("Unable to configure camera due to ")));
                    return;
                } else {
                    if (th2 instanceof TimeoutException) {
                        String str = C20039w.this.f23475k.f23525a;
                        C20140q0.b("Camera2CameraImpl");
                        return;
                    }
                    return;
                }
            }
            C20039w c20039w = C20039w.this;
            DeferrableSurface deferrableSurface = ((DeferrableSurface.SurfaceClosedException) th2).f23939b;
            Iterator<SessionConfig> it = c20039w.f23466b.d().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (next.b().contains(deferrableSurface)) {
                        break;
                    }
                }
            }
            if (next != null) {
                C20039w c20039w2 = C20039w.this;
                c20039w2.getClass();
                ScheduledExecutorService scheduledExecutorServiceD = androidx.camera.core.impl.utils.executor.c.d();
                List<SessionConfig.c> list = next.f24016e;
                if (list.isEmpty()) {
                    return;
                }
                SessionConfig.c cVar = list.get(0);
                new Throwable();
                c20039w2.s("Posting surface closed");
                scheduledExecutorServiceD.execute(new RunnableC20041x(cVar, next));
            }
        }

        @Override // androidx.camera.core.impl.utils.futures.c
        public final void onSuccess(@j.P Void r32) {
            C20039w c20039w = C20039w.this;
            if (c20039w.f23481q.f1850e == 2 && c20039w.f23470f == f.f23501e) {
                C20039w.this.E(f.f23502f);
            }
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$b */
    public static /* synthetic */ class b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f23492a;

        static {
            int[] iArr = new int[f.values().length];
            f23492a = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23492a[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23492a[5] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f23492a[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f23492a[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f23492a[2] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f23492a[6] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f23492a[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f23492a[8] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$c */
    public final class c extends CameraManager.AvailabilityCallback implements E.c {

        /* renamed from: a, reason: collision with root package name */
        public final String f23493a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f23494b = true;

        public c(String str) {
            this.f23493a = str;
        }

        @Override // androidx.camera.core.impl.E.c
        public final void a() {
            if (C20039w.this.f23470f == f.f23499c) {
                C20039w.this.J(false);
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public final void onCameraAvailable(@j.N String str) {
            if (this.f23493a.equals(str)) {
                this.f23494b = true;
                if (C20039w.this.f23470f == f.f23499c) {
                    C20039w.this.J(false);
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
        public final void onCameraUnavailable(@j.N String str) {
            if (this.f23493a.equals(str)) {
                this.f23494b = false;
            }
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$d */
    public final class d implements E.b {
        public d() {
        }

        @Override // androidx.camera.core.impl.E.b
        public final void a() {
            if (C20039w.this.f23470f == f.f23501e) {
                C20039w.this.A();
            }
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$e */
    public final class e implements CameraControlInternal.b {
        public e() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: Camera2CameraImpl.java */
    /* renamed from: androidx.camera.camera2.internal.w$f */
    public static final class f {

        /* renamed from: b, reason: collision with root package name */
        public static final f f23498b;

        /* renamed from: c, reason: collision with root package name */
        public static final f f23499c;

        /* renamed from: d, reason: collision with root package name */
        public static final f f23500d;

        /* renamed from: e, reason: collision with root package name */
        public static final f f23501e;

        /* renamed from: f, reason: collision with root package name */
        public static final f f23502f;

        /* renamed from: g, reason: collision with root package name */
        public static final f f23503g;

        /* renamed from: h, reason: collision with root package name */
        public static final f f23504h;

        /* renamed from: i, reason: collision with root package name */
        public static final f f23505i;

        /* renamed from: j, reason: collision with root package name */
        public static final f f23506j;

        /* renamed from: k, reason: collision with root package name */
        public static final /* synthetic */ f[] f23507k;

        static {
            f fVar = new f("INITIALIZED", 0);
            f23498b = fVar;
            f fVar2 = new f("PENDING_OPEN", 1);
            f23499c = fVar2;
            f fVar3 = new f("OPENING", 2);
            f23500d = fVar3;
            f fVar4 = new f("OPENED", 3);
            f23501e = fVar4;
            f fVar5 = new f("CONFIGURED", 4);
            f23502f = fVar5;
            f fVar6 = new f("CLOSING", 5);
            f23503g = fVar6;
            f fVar7 = new f("REOPENING", 6);
            f23504h = fVar7;
            f fVar8 = new f("RELEASING", 7);
            f23505i = fVar8;
            f fVar9 = new f("RELEASED", 8);
            f23506j = fVar9;
            f23507k = new f[]{fVar, fVar2, fVar3, fVar4, fVar5, fVar6, fVar7, fVar8, fVar9};
        }

        public f() {
            throw null;
        }

        public static f valueOf(String str) {
            return (f) Enum.valueOf(f.class, str);
        }

        public static f[] values() {
            return (f[]) f23507k.clone();
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    @j.X
    /* renamed from: androidx.camera.camera2.internal.w$g */
    public final class g extends CameraDevice.StateCallback {

        /* renamed from: a, reason: collision with root package name */
        public final Executor f23508a;

        /* renamed from: b, reason: collision with root package name */
        public final ScheduledExecutorService f23509b;

        /* renamed from: c, reason: collision with root package name */
        public b f23510c;

        /* renamed from: d, reason: collision with root package name */
        public ScheduledFuture<?> f23511d;

        /* renamed from: e, reason: collision with root package name */
        @j.N
        public final a f23512e = new a();

        /* compiled from: Camera2CameraImpl.java */
        /* renamed from: androidx.camera.camera2.internal.w$g$a */
        public class a {

            /* renamed from: a, reason: collision with root package name */
            public long f23514a = -1;

            public a() {
            }

            public final int a() {
                if (!g.this.c()) {
                    return 700;
                }
                long jUptimeMillis = SystemClock.uptimeMillis();
                if (this.f23514a == -1) {
                    this.f23514a = jUptimeMillis;
                }
                long j12 = jUptimeMillis - this.f23514a;
                if (j12 <= 120000) {
                    return 1000;
                }
                return j12 <= 300000 ? 2000 : 4000;
            }
        }

        /* compiled from: Camera2CameraImpl.java */
        /* renamed from: androidx.camera.camera2.internal.w$g$b */
        public class b implements Runnable {

            /* renamed from: b, reason: collision with root package name */
            public final Executor f23516b;

            /* renamed from: c, reason: collision with root package name */
            public boolean f23517c = false;

            public b(@j.N Executor executor) {
                this.f23516b = executor;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.f23516b.execute(new RunnableC20041x(this, 0));
            }
        }

        public g(@j.N Executor executor, @j.N ScheduledExecutorService scheduledExecutorService) {
            this.f23508a = executor;
            this.f23509b = scheduledExecutorService;
        }

        public final boolean a() {
            if (this.f23511d == null) {
                return false;
            }
            C20039w.this.s("Cancelling scheduled re-open: " + this.f23510c);
            this.f23510c.f23517c = true;
            this.f23510c = null;
            this.f23511d.cancel(false);
            this.f23511d = null;
            return true;
        }

        public final void b() {
            androidx.core.util.z.g(null, this.f23510c == null);
            androidx.core.util.z.g(null, this.f23511d == null);
            a aVar = this.f23512e;
            aVar.getClass();
            long jUptimeMillis = SystemClock.uptimeMillis();
            if (aVar.f23514a == -1) {
                aVar.f23514a = jUptimeMillis;
            }
            long j12 = jUptimeMillis - aVar.f23514a;
            g gVar = g.this;
            long j13 = !gVar.c() ? 10000 : Constants.THIRTY_MINUTES;
            C20039w c20039w = C20039w.this;
            if (j12 >= j13) {
                aVar.f23514a = -1L;
                gVar.c();
                C20140q0.b("Camera2CameraImpl");
                c20039w.F(f.f23499c, null, false);
                return;
            }
            this.f23510c = new b(this.f23508a);
            c20039w.s("Attempting camera re-open in " + aVar.a() + "ms: " + this.f23510c + " activeResuming = " + c20039w.f23463A);
            this.f23511d = this.f23509b.schedule(this.f23510c, (long) aVar.a(), TimeUnit.MILLISECONDS);
        }

        public final boolean c() {
            int i12;
            C20039w c20039w = C20039w.this;
            return c20039w.f23463A && ((i12 = c20039w.f23477m) == 1 || i12 == 2);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(@j.N CameraDevice cameraDevice) {
            C20039w.this.s("CameraDevice.onClosed()");
            androidx.core.util.z.g("Unexpected onClose callback on camera device: " + cameraDevice, C20039w.this.f23476l == null);
            int iOrdinal = C20039w.this.f23470f.ordinal();
            if (iOrdinal != 5) {
                if (iOrdinal == 6) {
                    C20039w c20039w = C20039w.this;
                    int i12 = c20039w.f23477m;
                    if (i12 == 0) {
                        c20039w.J(false);
                        return;
                    } else {
                        c20039w.s("Camera closed due to error: ".concat(C20039w.u(i12)));
                        b();
                        return;
                    }
                }
                if (iOrdinal != 7) {
                    throw new IllegalStateException("Camera closed while in state: " + C20039w.this.f23470f);
                }
            }
            androidx.core.util.z.g(null, C20039w.this.x());
            C20039w.this.t();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(@j.N CameraDevice cameraDevice) {
            C20039w.this.s("CameraDevice.onDisconnected()");
            onError(cameraDevice, 1);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(@j.N CameraDevice cameraDevice, int i12) {
            C20039w c20039w = C20039w.this;
            c20039w.f23476l = cameraDevice;
            c20039w.f23477m = i12;
            switch (c20039w.f23470f.ordinal()) {
                case 2:
                case 3:
                case 4:
                case 6:
                    cameraDevice.getId();
                    C20039w.this.f23470f.name();
                    int i13 = 3;
                    C20140q0.d(3, "Camera2CameraImpl");
                    f fVar = C20039w.this.f23470f;
                    f fVar2 = f.f23500d;
                    f fVar3 = f.f23504h;
                    androidx.core.util.z.g("Attempt to handle open error from non open state: " + C20039w.this.f23470f, fVar == fVar2 || C20039w.this.f23470f == f.f23501e || C20039w.this.f23470f == f.f23502f || C20039w.this.f23470f == fVar3);
                    if (i12 != 1 && i12 != 2 && i12 != 4) {
                        cameraDevice.getId();
                        C20140q0.b("Camera2CameraImpl");
                        C20039w.this.F(f.f23503g, CameraState.a.a(i12 == 3 ? 5 : 6), true);
                        C20039w.this.q();
                        return;
                    }
                    cameraDevice.getId();
                    C20140q0.d(3, "Camera2CameraImpl");
                    C20039w c20039w2 = C20039w.this;
                    androidx.core.util.z.g("Can only reopen camera device after error if the camera device is actually in an error state.", c20039w2.f23477m != 0);
                    if (i12 == 1) {
                        i13 = 2;
                    } else if (i12 == 2) {
                        i13 = 1;
                    }
                    c20039w2.F(fVar3, CameraState.a.a(i13), true);
                    c20039w2.q();
                    return;
                case 5:
                case 7:
                    cameraDevice.getId();
                    C20039w.this.f23470f.name();
                    C20140q0.b("Camera2CameraImpl");
                    C20039w.this.q();
                    return;
                default:
                    throw new IllegalStateException("onError() should not be possible from state: " + C20039w.this.f23470f);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(@j.N CameraDevice cameraDevice) {
            C20039w.this.s("CameraDevice.onOpened()");
            C20039w c20039w = C20039w.this;
            c20039w.f23476l = cameraDevice;
            c20039w.f23477m = 0;
            this.f23512e.f23514a = -1L;
            int iOrdinal = c20039w.f23470f.ordinal();
            if (iOrdinal != 2) {
                if (iOrdinal != 5) {
                    if (iOrdinal != 6) {
                        if (iOrdinal != 7) {
                            throw new IllegalStateException("onOpened() should not be possible from state: " + C20039w.this.f23470f);
                        }
                    }
                }
                androidx.core.util.z.g(null, C20039w.this.x());
                C20039w.this.f23476l.close();
                C20039w.this.f23476l = null;
                return;
            }
            C20039w.this.E(f.f23501e);
            androidx.camera.core.impl.E e12 = C20039w.this.f23482r;
            String id2 = cameraDevice.getId();
            C20039w c20039w2 = C20039w.this;
            if (e12.h(id2, c20039w2.f23481q.b(c20039w2.f23476l.getId()))) {
                C20039w.this.A();
            }
        }
    }

    /* compiled from: Camera2CameraImpl.java */
    @VY0.c
    /* renamed from: androidx.camera.camera2.internal.w$h */
    public static abstract class h {
        @j.N
        public abstract SessionConfig a();

        @j.P
        public abstract Size b();

        @j.N
        public abstract androidx.camera.core.impl.L0<?> c();

        @j.N
        public abstract String d();

        @j.N
        public abstract Class<?> e();
    }

    public C20039w(@j.N androidx.camera.camera2.internal.compat.w wVar, @j.N String str, @j.N C20045z c20045z, @j.N C.a aVar, @j.N androidx.camera.core.impl.E e12, @j.N Executor executor, @j.N Handler handler, @j.N C20013i0 c20013i0) throws CameraUnavailableException {
        C20102k0<CameraInternal.State> c20102k0 = new C20102k0<>();
        this.f23471g = c20102k0;
        this.f23477m = 0;
        new AtomicInteger(0);
        this.f23479o = new LinkedHashMap();
        this.f23483s = new HashSet();
        this.f23487w = new HashSet();
        this.f23488x = C20115w.f24305a;
        this.f23489y = new Object();
        this.f23463A = false;
        this.f23467c = wVar;
        this.f23481q = aVar;
        this.f23482r = e12;
        ScheduledExecutorService scheduledExecutorServiceE = androidx.camera.core.impl.utils.executor.c.e(handler);
        this.f23469e = scheduledExecutorServiceE;
        Executor executorF = androidx.camera.core.impl.utils.executor.c.f(executor);
        this.f23468d = executorF;
        this.f23474j = new g(executorF, scheduledExecutorServiceE);
        this.f23466b = new androidx.camera.core.impl.K0(str);
        c20102k0.f24145a.postValue(new C20102k0.b<>(CameraInternal.State.CLOSED));
        W w12 = new W(e12);
        this.f23472h = w12;
        C20009g0 c20009g0 = new C20009g0(executorF);
        this.f23485u = c20009g0;
        this.f23464B = c20013i0;
        try {
            androidx.camera.camera2.internal.compat.n nVarB = wVar.b(str);
            C20026p c20026p = new C20026p(nVarB, scheduledExecutorServiceE, executorF, new e(), c20045z.f23534j);
            this.f23473i = c20026p;
            this.f23475k = c20045z;
            c20045z.r(c20026p);
            c20045z.f23532h.c(w12.f23076b);
            this.f23465C = androidx.camera.camera2.internal.compat.params.b.a(nVarB);
            this.f23478n = y();
            this.f23486v = new Q0.a(handler, c20009g0, c20045z.f23534j, C20001l.f23216a, executorF, scheduledExecutorServiceE);
            c cVar = new c(str);
            this.f23480p = cVar;
            e12.f(this, executorF, new d(), cVar);
            wVar.f23241a.d(executorF, cVar);
        } catch (CameraAccessExceptionCompat e13) {
            throw X.a(e13);
        }
    }

    @j.N
    public static ArrayList G(@j.N ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            androidx.camera.core.N0 n02 = (androidx.camera.core.N0) it.next();
            arrayList2.add(new C19983c(w(n02), n02.getClass(), n02.f23627m, n02.f23620f, n02.b()));
        }
        return arrayList2;
    }

    public static String u(int i12) {
        return i12 != 0 ? i12 != 1 ? i12 != 2 ? i12 != 3 ? i12 != 4 ? i12 != 5 ? "UNKNOWN ERROR" : "ERROR_CAMERA_SERVICE" : "ERROR_CAMERA_DEVICE" : "ERROR_CAMERA_DISABLED" : "ERROR_MAX_CAMERAS_IN_USE" : "ERROR_CAMERA_IN_USE" : "ERROR_NONE";
    }

    @j.N
    public static String v(@j.N A0 a02) {
        StringBuilder sb2 = new StringBuilder("MeteringRepeating");
        a02.getClass();
        sb2.append(a02.hashCode());
        return sb2.toString();
    }

    @j.N
    public static String w(@j.N androidx.camera.core.N0 n02) {
        return n02.g() + n02.hashCode();
    }

    @j.S
    public final void A() {
        androidx.core.util.z.g(null, this.f23470f == f.f23501e);
        SessionConfig.f fVarC = this.f23466b.c();
        if (!fVarC.f24030j || !fVarC.f24029i) {
            s("Unable to create capture session due to conflicting configurations");
            return;
        }
        if (!this.f23482r.h(this.f23476l.getId(), this.f23481q.b(this.f23476l.getId()))) {
            s("Unable to create capture session in camera operating mode = " + this.f23481q.f1850e);
            return;
        }
        HashMap map = new HashMap();
        Collection<SessionConfig> collectionD = this.f23466b.d();
        Collection<androidx.camera.core.impl.L0<?>> collectionE = this.f23466b.e();
        Config.a<Long> aVar = H0.f22982a;
        ArrayList arrayList = new ArrayList(collectionE);
        Iterator<SessionConfig> it = collectionD.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SessionConfig next = it.next();
            androidx.camera.core.impl.s0 s0Var = next.f24017f.f23969b;
            Config.a<Long> aVar2 = H0.f22982a;
            if (s0Var.f24173F.containsKey(aVar2) && next.b().size() != 1) {
                String.format("SessionConfig has stream use case but also contains %d surfaces, abort populateSurfaceToStreamUseCaseMapping().", Integer.valueOf(next.b().size()));
                C20140q0.b("Camera2CameraImpl");
                break;
            } else if (next.f24017f.f23969b.f24173F.containsKey(aVar2)) {
                int i12 = 0;
                for (SessionConfig sessionConfig : collectionD) {
                    if (((androidx.camera.core.impl.L0) arrayList.get(i12)).z() == UseCaseConfigFactory.CaptureType.f24059g) {
                        map.put(sessionConfig.b().get(0), 1L);
                    } else if (sessionConfig.f24017f.f23969b.f24173F.containsKey(aVar2)) {
                        map.put(sessionConfig.b().get(0), (Long) sessionConfig.f24017f.f23969b.f(aVar2));
                    }
                    i12++;
                }
            }
        }
        this.f23478n.a(map);
        InterfaceC20007f0 interfaceC20007f0 = this.f23478n;
        SessionConfig sessionConfigB = fVarC.b();
        CameraDevice cameraDevice = this.f23476l;
        cameraDevice.getClass();
        androidx.camera.core.impl.utils.futures.f.a(interfaceC20007f0.c(sessionConfigB, cameraDevice, this.f23486v.a()), new a(), this.f23468d);
    }

    public final com.google.common.util.concurrent.D0 B(@j.N InterfaceC20007f0 interfaceC20007f0) {
        interfaceC20007f0.close();
        com.google.common.util.concurrent.D0 d0Release = interfaceC20007f0.release();
        s("Releasing session in state " + this.f23470f.name());
        this.f23479o.put(interfaceC20007f0, d0Release);
        androidx.camera.core.impl.utils.futures.f.a(d0Release, new C20037v(this, interfaceC20007f0), androidx.camera.core.impl.utils.executor.c.a());
        return d0Release;
    }

    public final void C() {
        if (this.f23484t != null) {
            StringBuilder sb2 = new StringBuilder("MeteringRepeating");
            this.f23484t.getClass();
            sb2.append(this.f23484t.hashCode());
            String string = sb2.toString();
            androidx.camera.core.impl.K0 k02 = this.f23466b;
            k02.i(string);
            StringBuilder sb3 = new StringBuilder("MeteringRepeating");
            this.f23484t.getClass();
            sb3.append(this.f23484t.hashCode());
            k02.j(sb3.toString());
            A0 a02 = this.f23484t;
            a02.getClass();
            C20140q0.d(3, "MeteringRepeating");
            C20094g0 c20094g0 = a02.f22900a;
            if (c20094g0 != null) {
                c20094g0.a();
            }
            a02.f22900a = null;
            this.f23484t = null;
        }
    }

    public final void D() {
        androidx.core.util.z.g(null, this.f23478n != null);
        s("Resetting Capture Session");
        InterfaceC20007f0 interfaceC20007f0 = this.f23478n;
        SessionConfig sessionConfigD = interfaceC20007f0.d();
        List<androidx.camera.core.impl.H> listG = interfaceC20007f0.g();
        InterfaceC20007f0 interfaceC20007f0Y = y();
        this.f23478n = interfaceC20007f0Y;
        interfaceC20007f0Y.e(sessionConfigD);
        this.f23478n.b(listG);
        B(interfaceC20007f0);
    }

    public final void E(@j.N f fVar) {
        F(fVar, null, true);
    }

    public final void F(@j.N f fVar, @j.P CameraState.a aVar, boolean z12) {
        CameraInternal.State state;
        CameraState cameraStateA;
        s("Transitioning camera internal state: " + this.f23470f + " --> " + fVar);
        this.f23470f = fVar;
        switch (fVar.ordinal()) {
            case 0:
                state = CameraInternal.State.CLOSED;
                break;
            case 1:
                state = CameraInternal.State.PENDING_OPEN;
                break;
            case 2:
            case 6:
                state = CameraInternal.State.OPENING;
                break;
            case 3:
                state = CameraInternal.State.OPEN;
                break;
            case 4:
                state = CameraInternal.State.CONFIGURED;
                break;
            case 5:
                state = CameraInternal.State.CLOSING;
                break;
            case 7:
                state = CameraInternal.State.RELEASING;
                break;
            case 8:
                state = CameraInternal.State.RELEASED;
                break;
            default:
                throw new IllegalStateException("Unknown state: " + fVar);
        }
        this.f23482r.d(this, state, z12);
        this.f23471g.f24145a.postValue(new C20102k0.b<>(state));
        W w12 = this.f23472h;
        w12.getClass();
        int iOrdinal = state.ordinal();
        CameraState.Type type = CameraState.Type.f23552c;
        switch (iOrdinal) {
            case 0:
                if (!w12.f23075a.c()) {
                    cameraStateA = CameraState.a(CameraState.Type.f23551b);
                    break;
                } else {
                    cameraStateA = CameraState.a(type);
                    break;
                }
            case 1:
                cameraStateA = CameraState.b(type, aVar);
                break;
            case 2:
            case 3:
                cameraStateA = CameraState.b(CameraState.Type.f23553d, aVar);
                break;
            case 4:
            case 6:
                cameraStateA = CameraState.b(CameraState.Type.f23554e, aVar);
                break;
            case 5:
            case 7:
                cameraStateA = CameraState.b(CameraState.Type.f23555f, aVar);
                break;
            default:
                throw new IllegalStateException("Unknown internal camera state: " + state);
        }
        cameraStateA.toString();
        state.toString();
        Objects.toString(aVar);
        C20140q0.d(3, "CameraStateMachine");
        C23038g0<CameraState> c23038g0 = w12.f23076b;
        if (Objects.equals(c23038g0.getValue(), cameraStateA)) {
            return;
        }
        cameraStateA.toString();
        C20140q0.d(3, "CameraStateMachine");
        c23038g0.postValue(cameraStateA);
    }

    public final void H(@j.N ArrayList arrayList) {
        Size sizeB;
        boolean zIsEmpty = this.f23466b.d().isEmpty();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        Rational rational = null;
        while (it.hasNext()) {
            h hVar = (h) it.next();
            if (!this.f23466b.f(hVar.d())) {
                this.f23466b.h(hVar.d(), hVar.a(), hVar.c());
                arrayList2.add(hVar.d());
                if (hVar.e() == androidx.camera.core.v0.class && (sizeB = hVar.b()) != null) {
                    rational = new Rational(sizeB.getWidth(), sizeB.getHeight());
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        s("Use cases [" + TextUtils.join(", ", arrayList2) + "] now ATTACHED");
        if (zIsEmpty) {
            this.f23473i.v(true);
            C20026p c20026p = this.f23473i;
            synchronized (c20026p.f23390d) {
                c20026p.f23401o++;
            }
        }
        p();
        L();
        K();
        D();
        f fVar = this.f23470f;
        f fVar2 = f.f23501e;
        if (fVar == fVar2) {
            A();
        } else {
            int iOrdinal = this.f23470f.ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                I(false);
            } else if (iOrdinal != 5) {
                s("open() ignored due to being in state: " + this.f23470f);
            } else {
                E(f.f23504h);
                if (!x() && this.f23477m == 0) {
                    androidx.core.util.z.g("Camera Device should be open if session close is not complete", this.f23476l != null);
                    E(fVar2);
                    A();
                }
            }
        }
        if (rational != null) {
            this.f23473i.f23394h.f23444e = rational;
        }
    }

    public final void I(boolean z12) {
        s("Attempting to force open the camera.");
        if (this.f23482r.g(this)) {
            z(z12);
        } else {
            s("No cameras available. Waiting for available camera before opening camera.");
            E(f.f23499c);
        }
    }

    public final void J(boolean z12) {
        s("Attempting to open the camera.");
        if (this.f23480p.f23494b && this.f23482r.g(this)) {
            z(z12);
        } else {
            s("No cameras available. Waiting for available camera before opening camera.");
            E(f.f23499c);
        }
    }

    public final void K() {
        SessionConfig.f fVarA = this.f23466b.a();
        boolean z12 = fVarA.f24030j && fVarA.f24029i;
        C20026p c20026p = this.f23473i;
        if (!z12) {
            c20026p.f23408v = 1;
            c20026p.f23394h.f23453n = 1;
            c20026p.f23400n.f22941g = 1;
            this.f23478n.e(c20026p.p());
            return;
        }
        int i12 = fVarA.b().f24017f.f23970c;
        c20026p.f23408v = i12;
        c20026p.f23394h.f23453n = i12;
        c20026p.f23400n.f22941g = i12;
        fVarA.a(c20026p.p());
        this.f23478n.e(fVarA.b());
    }

    public final void L() {
        Iterator<androidx.camera.core.impl.L0<?>> it = this.f23466b.e().iterator();
        boolean zM2 = false;
        while (it.hasNext()) {
            zM2 |= it.next().m();
        }
        this.f23473i.f23398l.f23315c = zM2;
    }

    @Override // androidx.camera.core.N0.d
    public final void b(@j.N androidx.camera.core.N0 n02) {
        n02.getClass();
        this.f23468d.execute(new RunnableC20033t(this, w(n02), n02.f23627m, n02.f23620f, 1));
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @j.N
    public final androidx.camera.core.impl.B c() {
        return this.f23475k;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void e(@j.P InterfaceC20112t interfaceC20112t) {
        if (interfaceC20112t == null) {
            interfaceC20112t = C20115w.f24305a;
        }
        androidx.camera.core.impl.C0 c0W = interfaceC20112t.w();
        this.f23488x = interfaceC20112t;
        synchronized (this.f23489y) {
            this.f23490z = c0W;
        }
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @j.N
    public final androidx.camera.core.impl.q0<CameraInternal.State> f() {
        return this.f23471g;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void g(@j.N ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        if (arrayList2.isEmpty()) {
            return;
        }
        ArrayList arrayList3 = new ArrayList(G(arrayList2));
        Iterator it = new ArrayList(arrayList2).iterator();
        while (it.hasNext()) {
            androidx.camera.core.N0 n02 = (androidx.camera.core.N0) it.next();
            String strW = w(n02);
            HashSet hashSet = this.f23487w;
            if (hashSet.contains(strW)) {
                n02.v();
                hashSet.remove(strW);
            }
        }
        this.f23468d.execute(new RunnableC20031s(this, arrayList3, 1));
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @j.N
    public final InterfaceC20112t h() {
        return this.f23488x;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void i(@j.N ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        if (arrayList2.isEmpty()) {
            return;
        }
        C20026p c20026p = this.f23473i;
        synchronized (c20026p.f23390d) {
            c20026p.f23401o++;
        }
        Iterator it = new ArrayList(arrayList2).iterator();
        while (it.hasNext()) {
            androidx.camera.core.N0 n02 = (androidx.camera.core.N0) it.next();
            String strW = w(n02);
            HashSet hashSet = this.f23487w;
            if (!hashSet.contains(strW)) {
                hashSet.add(strW);
                n02.u();
                n02.s();
            }
        }
        try {
            this.f23468d.execute(new RunnableC20031s(this, new ArrayList(G(arrayList2)), 0));
        } catch (RejectedExecutionException unused) {
            s("Unable to attach use cases.");
            c20026p.n();
        }
    }

    @Override // androidx.camera.core.N0.d
    public final void k(@j.N androidx.camera.core.N0 n02) {
        this.f23468d.execute(new RunnableC20033t(this, w(n02), n02.f23627m, n02.f23620f, 2));
    }

    @Override // androidx.camera.core.impl.CameraInternal
    @j.N
    public final CameraControlInternal l() {
        return this.f23473i;
    }

    @Override // androidx.camera.core.impl.CameraInternal
    public final void m(final boolean z12) {
        this.f23468d.execute(new Runnable() { // from class: androidx.camera.camera2.internal.u
            @Override // java.lang.Runnable
            public final void run() {
                C20039w c20039w = this.f23459b;
                boolean z13 = z12;
                c20039w.f23463A = z13;
                if (z13 && c20039w.f23470f == C20039w.f.f23499c) {
                    c20039w.I(false);
                }
            }
        });
    }

    @Override // androidx.camera.core.N0.d
    public final void n(@j.N androidx.camera.core.N0 n02) {
        n02.getClass();
        SessionConfig sessionConfig = n02.f23627m;
        androidx.camera.core.impl.L0<?> l02 = n02.f23620f;
        this.f23468d.execute(new RunnableC20033t(this, w(n02), sessionConfig, l02, 0));
    }

    @Override // androidx.camera.core.N0.d
    public final void o(@j.N androidx.camera.core.N0 n02) {
        this.f23468d.execute(new RunnableC20016k(3, this, w(n02)));
    }

    public final void p() {
        androidx.camera.core.impl.K0 k02 = this.f23466b;
        SessionConfig sessionConfigB = k02.c().b();
        androidx.camera.core.impl.H h12 = sessionConfigB.f24017f;
        int size = Collections.unmodifiableList(h12.f23968a).size();
        int size2 = sessionConfigB.b().size();
        if (sessionConfigB.b().isEmpty()) {
            return;
        }
        if (!Collections.unmodifiableList(h12.f23968a).isEmpty()) {
            if (size2 == 1 && size == 1) {
                C();
                return;
            } else if (size >= 2) {
                C();
                return;
            } else {
                C20140q0.d(3, "Camera2CameraImpl");
                return;
            }
        }
        if (this.f23484t == null) {
            this.f23484t = new A0(this.f23475k.f23526b, this.f23464B, new r(this));
        }
        A0 a02 = this.f23484t;
        if (a02 != null) {
            String strV = v(a02);
            A0 a03 = this.f23484t;
            k02.h(strV, a03.f22901b, a03.f22902c);
            A0 a04 = this.f23484t;
            k02.g(strV, a04.f22901b, a04.f22902c);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void q() {
        /*
            r10 = this;
            androidx.camera.camera2.internal.w$f r0 = r10.f23470f
            androidx.camera.camera2.internal.w$f r1 = androidx.camera.camera2.internal.C20039w.f.f23503g
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L1b
            androidx.camera.camera2.internal.w$f r0 = r10.f23470f
            androidx.camera.camera2.internal.w$f r1 = androidx.camera.camera2.internal.C20039w.f.f23505i
            if (r0 == r1) goto L1b
            androidx.camera.camera2.internal.w$f r0 = r10.f23470f
            androidx.camera.camera2.internal.w$f r1 = androidx.camera.camera2.internal.C20039w.f.f23504h
            if (r0 != r1) goto L19
            int r0 = r10.f23477m
            if (r0 == 0) goto L19
            goto L1b
        L19:
            r0 = r3
            goto L1c
        L1b:
            r0 = r2
        L1c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "closeCamera should only be called in a CLOSING, RELEASING or REOPENING (with error) state. Current state: "
            r1.<init>(r4)
            androidx.camera.camera2.internal.w$f r4 = r10.f23470f
            r1.append(r4)
            java.lang.String r4 = " (error: "
            r1.append(r4)
            int r4 = r10.f23477m
            java.lang.String r4 = u(r4)
            r1.append(r4)
            java.lang.String r4 = ")"
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            androidx.core.util.z.g(r1, r0)
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 >= r1) goto Lc0
            androidx.camera.camera2.internal.z r0 = r10.f23475k
            androidx.camera.camera2.internal.compat.n r0 = r0.f23526b
            android.hardware.camera2.CameraCharacteristics$Key r1 = android.hardware.camera2.CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL
            java.lang.Object r0 = r0.a(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            r0.getClass()
            int r0 = r0.intValue()
            r1 = 2
            if (r0 != r1) goto Lc0
            int r0 = r10.f23477m
            if (r0 != 0) goto Lc0
            androidx.camera.camera2.internal.d0 r6 = new androidx.camera.camera2.internal.d0
            androidx.camera.camera2.internal.compat.params.b r0 = r10.f23465C
            r6.<init>(r0)
            java.util.HashSet r0 = r10.f23483s
            r0.add(r6)
            r10.D()
            android.graphics.SurfaceTexture r0 = new android.graphics.SurfaceTexture
            r0.<init>(r3)
            r1 = 640(0x280, float:8.97E-43)
            r3 = 480(0x1e0, float:6.73E-43)
            r0.setDefaultBufferSize(r1, r3)
            android.view.Surface r1 = new android.view.Surface
            r1.<init>(r0)
            androidx.camera.camera2.internal.k r8 = new androidx.camera.camera2.internal.k
            r3 = 7
            r8.<init>(r3, r1, r0)
            androidx.camera.core.impl.SessionConfig$b r0 = new androidx.camera.core.impl.SessionConfig$b
            r0.<init>()
            androidx.camera.core.impl.g0 r7 = new androidx.camera.core.impl.g0
            r7.<init>(r1)
            androidx.camera.core.F r1 = androidx.camera.core.F.f23562d
            r0.f(r7, r1)
            r0.s(r2)
            java.lang.String r1 = "Start configAndClose."
            r10.s(r1)
            androidx.camera.core.impl.SessionConfig r0 = r0.k()
            android.hardware.camera2.CameraDevice r1 = r10.f23476l
            r1.getClass()
            androidx.camera.camera2.internal.Q0$a r2 = r10.f23486v
            androidx.camera.camera2.internal.Q0 r2 = r2.a()
            com.google.common.util.concurrent.D0 r0 = r6.c(r0, r1, r2)
            androidx.camera.camera2.internal.t r1 = new androidx.camera.camera2.internal.t
            r9 = 3
            r4 = r1
            r5 = r10
            r4.<init>(r5, r6, r7, r8, r9)
            java.util.concurrent.Executor r2 = r10.f23468d
            r0.N(r1, r2)
            goto Lc3
        Lc0:
            r10.D()
        Lc3:
            androidx.camera.camera2.internal.f0 r0 = r10.f23478n
            r0.f()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.C20039w.q():void");
    }

    public final CameraDevice.StateCallback r() {
        ArrayList arrayList = new ArrayList(this.f23466b.c().b().f24013b);
        arrayList.add(this.f23485u.f23332f);
        arrayList.add(this.f23474j);
        return arrayList.isEmpty() ? new U.b() : arrayList.size() == 1 ? (CameraDevice.StateCallback) arrayList.get(0) : new U.a(arrayList);
    }

    public final void s(@j.N String str) {
        toString();
        C20140q0.d(3, "Camera2CameraImpl");
    }

    public final void t() {
        f fVar = this.f23470f;
        f fVar2 = f.f23505i;
        f fVar3 = f.f23503g;
        androidx.core.util.z.g(null, fVar == fVar2 || this.f23470f == fVar3);
        androidx.core.util.z.g(null, this.f23479o.isEmpty());
        this.f23476l = null;
        if (this.f23470f == fVar3) {
            E(f.f23498b);
            return;
        }
        this.f23467c.f23241a.f(this.f23480p);
        E(f.f23506j);
    }

    @j.N
    public final String toString() {
        return String.format(Locale.US, "Camera@%x[id=%s]", Integer.valueOf(hashCode()), this.f23475k.f23525a);
    }

    public final boolean x() {
        return this.f23479o.isEmpty() && this.f23483s.isEmpty();
    }

    @j.N
    public final InterfaceC20007f0 y() {
        synchronized (this.f23489y) {
            try {
                if (this.f23490z == null) {
                    return new C20003d0(this.f23465C);
                }
                return new C0(this.f23490z, this.f23475k, this.f23465C, this.f23468d, this.f23469e);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    public final void z(boolean z12) {
        g gVar = this.f23474j;
        if (!z12) {
            gVar.f23512e.f23514a = -1L;
        }
        gVar.a();
        s("Opening camera.");
        E(f.f23500d);
        try {
            this.f23467c.f23241a.a(this.f23475k.f23525a, this.f23468d, r());
        } catch (CameraAccessExceptionCompat e12) {
            s("Unable to open camera due to " + e12.getMessage());
            if (e12.f23130b != 10001) {
                return;
            }
            F(f.f23498b, CameraState.a.b(7, e12), true);
        } catch (SecurityException e13) {
            s("Unable to open camera due to " + e13.getMessage());
            E(f.f23504h);
            gVar.b();
        }
    }
}
