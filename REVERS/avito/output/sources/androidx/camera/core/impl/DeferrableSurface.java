package androidx.camera.core.impl;

import Fc1.G3;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20140q0;
import androidx.concurrent.futures.b;
import java.util.concurrent.atomic.AtomicInteger;

@j.X
/* loaded from: classes.dex */
public abstract class DeferrableSurface {

    /* renamed from: k, reason: collision with root package name */
    public static final Size f23925k = new Size(0, 0);

    /* renamed from: l, reason: collision with root package name */
    public static final boolean f23926l = C20140q0.d(3, "DeferrableSurface");

    /* renamed from: m, reason: collision with root package name */
    public static final AtomicInteger f23927m = new AtomicInteger(0);

    /* renamed from: n, reason: collision with root package name */
    public static final AtomicInteger f23928n = new AtomicInteger(0);

    /* renamed from: a, reason: collision with root package name */
    public final Object f23929a;

    /* renamed from: b, reason: collision with root package name */
    @j.B
    public int f23930b;

    /* renamed from: c, reason: collision with root package name */
    @j.B
    public boolean f23931c;

    /* renamed from: d, reason: collision with root package name */
    @j.B
    public b.a<Void> f23932d;

    /* renamed from: e, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<Void> f23933e;

    /* renamed from: f, reason: collision with root package name */
    @j.B
    public b.a<Void> f23934f;

    /* renamed from: g, reason: collision with root package name */
    public final com.google.common.util.concurrent.D0<Void> f23935g;

    /* renamed from: h, reason: collision with root package name */
    @j.N
    public final Size f23936h;

    /* renamed from: i, reason: collision with root package name */
    public final int f23937i;

    /* renamed from: j, reason: collision with root package name */
    @j.P
    public Class<?> f23938j;

    @RestrictTo
    public static final class SurfaceClosedException extends Exception {

        /* renamed from: b, reason: collision with root package name */
        public final DeferrableSurface f23939b;

        public SurfaceClosedException(@j.N DeferrableSurface deferrableSurface, @j.N String str) {
            super(str);
            this.f23939b = deferrableSurface;
        }
    }

    public static final class SurfaceUnavailableException extends Exception {
    }

    public DeferrableSurface() {
        this(f23925k, 0);
    }

    public final void a() {
        b.a<Void> aVar;
        synchronized (this.f23929a) {
            try {
                if (this.f23931c) {
                    aVar = null;
                } else {
                    this.f23931c = true;
                    this.f23934f.b(null);
                    if (this.f23930b == 0) {
                        aVar = this.f23932d;
                        this.f23932d = null;
                    } else {
                        aVar = null;
                    }
                    if (C20140q0.d(3, "DeferrableSurface")) {
                        toString();
                        C20140q0.d(3, "DeferrableSurface");
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (aVar != null) {
            aVar.b(null);
        }
    }

    public final void b() {
        b.a<Void> aVar;
        synchronized (this.f23929a) {
            try {
                int i12 = this.f23930b;
                if (i12 == 0) {
                    throw new IllegalStateException("Decrementing use count occurs more times than incrementing");
                }
                int i13 = i12 - 1;
                this.f23930b = i13;
                if (i13 == 0 && this.f23931c) {
                    aVar = this.f23932d;
                    this.f23932d = null;
                } else {
                    aVar = null;
                }
                if (C20140q0.d(3, "DeferrableSurface")) {
                    toString();
                    C20140q0.d(3, "DeferrableSurface");
                    if (this.f23930b == 0) {
                        f23928n.get();
                        f23927m.decrementAndGet();
                        e();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (aVar != null) {
            aVar.b(null);
        }
    }

    @j.N
    public final com.google.common.util.concurrent.D0<Surface> c() {
        synchronized (this.f23929a) {
            try {
                if (this.f23931c) {
                    return androidx.camera.core.impl.utils.futures.f.e(new SurfaceClosedException(this, "DeferrableSurface already closed."));
                }
                return f();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void d() {
        synchronized (this.f23929a) {
            try {
                int i12 = this.f23930b;
                if (i12 == 0 && this.f23931c) {
                    throw new SurfaceClosedException(this, "Cannot begin use on a closed surface.");
                }
                this.f23930b = i12 + 1;
                if (C20140q0.d(3, "DeferrableSurface")) {
                    if (this.f23930b == 1) {
                        f23928n.get();
                        f23927m.incrementAndGet();
                        e();
                    }
                    toString();
                    C20140q0.d(3, "DeferrableSurface");
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void e() {
        if (!f23926l && C20140q0.d(3, "DeferrableSurface")) {
            C20140q0.d(3, "DeferrableSurface");
        }
        toString();
        C20140q0.d(3, "DeferrableSurface");
    }

    @j.N
    public abstract com.google.common.util.concurrent.D0<Surface> f();

    public DeferrableSurface(@j.N Size size, int i12) {
        this.f23929a = new Object();
        this.f23930b = 0;
        this.f23931c = false;
        this.f23936h = size;
        this.f23937i = i12;
        final int i13 = 0;
        com.google.common.util.concurrent.D0<Void> d0A = androidx.concurrent.futures.b.a(new b.c(this) { // from class: androidx.camera.core.impl.L

            /* renamed from: c, reason: collision with root package name */
            public final /* synthetic */ DeferrableSurface f23996c;

            {
                this.f23996c = this;
            }

            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar) {
                switch (i13) {
                    case 0:
                        DeferrableSurface deferrableSurface = this.f23996c;
                        synchronized (deferrableSurface.f23929a) {
                            deferrableSurface.f23932d = aVar;
                        }
                        return "DeferrableSurface-termination(" + deferrableSurface + ")";
                    default:
                        DeferrableSurface deferrableSurface2 = this.f23996c;
                        synchronized (deferrableSurface2.f23929a) {
                            deferrableSurface2.f23934f = aVar;
                        }
                        return "DeferrableSurface-close(" + deferrableSurface2 + ")";
                }
            }
        });
        this.f23933e = d0A;
        final int i14 = 1;
        this.f23935g = androidx.concurrent.futures.b.a(new b.c(this) { // from class: androidx.camera.core.impl.L

            /* renamed from: c, reason: collision with root package name */
            public final /* synthetic */ DeferrableSurface f23996c;

            {
                this.f23996c = this;
            }

            @Override // androidx.concurrent.futures.b.c
            public final Object d(b.a aVar) {
                switch (i14) {
                    case 0:
                        DeferrableSurface deferrableSurface = this.f23996c;
                        synchronized (deferrableSurface.f23929a) {
                            deferrableSurface.f23932d = aVar;
                        }
                        return "DeferrableSurface-termination(" + deferrableSurface + ")";
                    default:
                        DeferrableSurface deferrableSurface2 = this.f23996c;
                        synchronized (deferrableSurface2.f23929a) {
                            deferrableSurface2.f23934f = aVar;
                        }
                        return "DeferrableSurface-close(" + deferrableSurface2 + ")";
                }
            }
        });
        if (C20140q0.d(3, "DeferrableSurface")) {
            f23928n.incrementAndGet();
            f23927m.get();
            e();
            d0A.N(new G3(this, Log.getStackTraceString(new Exception())), androidx.camera.core.impl.utils.executor.c.a());
        }
    }
}
