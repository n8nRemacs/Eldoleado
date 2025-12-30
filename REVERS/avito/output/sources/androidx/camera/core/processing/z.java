package androidx.camera.core.processing;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.E0;
import androidx.camera.core.K0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.G0;
import androidx.concurrent.futures.b;
import com.google.common.util.concurrent.D0;
import j.K;
import j.N;
import j.P;
import j.X;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: SurfaceEdge.java */
@X
/* loaded from: classes.dex */
public class z {

    /* renamed from: a, reason: collision with root package name */
    public final int f24495a;

    /* renamed from: b, reason: collision with root package name */
    public final Matrix f24496b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f24497c;

    /* renamed from: d, reason: collision with root package name */
    public final Rect f24498d;

    /* renamed from: e, reason: collision with root package name */
    public final boolean f24499e;

    /* renamed from: f, reason: collision with root package name */
    public final int f24500f;

    /* renamed from: g, reason: collision with root package name */
    public final G0 f24501g;

    /* renamed from: h, reason: collision with root package name */
    public int f24502h;

    /* renamed from: i, reason: collision with root package name */
    public int f24503i;

    /* renamed from: j, reason: collision with root package name */
    @P
    public A f24504j;

    /* renamed from: l, reason: collision with root package name */
    @P
    public K0 f24506l;

    /* renamed from: m, reason: collision with root package name */
    @N
    public a f24507m;

    /* renamed from: k, reason: collision with root package name */
    public boolean f24505k = false;

    /* renamed from: n, reason: collision with root package name */
    @N
    public final HashSet f24508n = new HashSet();

    /* renamed from: o, reason: collision with root package name */
    public boolean f24509o = false;

    /* compiled from: SurfaceEdge.java */
    public static class a extends DeferrableSurface {

        /* renamed from: o, reason: collision with root package name */
        public final D0<Surface> f24510o;

        /* renamed from: p, reason: collision with root package name */
        public b.a<Surface> f24511p;

        /* renamed from: q, reason: collision with root package name */
        public DeferrableSurface f24512q;

        public a(@N Size size, int i12) {
            super(size, i12);
            this.f24510o = androidx.concurrent.futures.b.a(new y(this, 0));
        }

        @Override // androidx.camera.core.impl.DeferrableSurface
        @N
        public final D0<Surface> f() {
            return this.f24510o;
        }

        @K
        public final boolean g(@N DeferrableSurface deferrableSurface, @N Runnable runnable) {
            boolean z12;
            androidx.camera.core.impl.utils.t.a();
            deferrableSurface.getClass();
            DeferrableSurface deferrableSurface2 = this.f24512q;
            if (deferrableSurface2 == deferrableSurface) {
                return false;
            }
            androidx.core.util.z.g("A different provider has been set. To change the provider, call SurfaceEdge#invalidate before calling SurfaceEdge#setProvider", deferrableSurface2 == null);
            androidx.core.util.z.a("The provider's size must match the parent", this.f23936h.equals(deferrableSurface.f23936h));
            androidx.core.util.z.a("The provider's format must match the parent", this.f23937i == deferrableSurface.f23937i);
            synchronized (this.f23929a) {
                z12 = this.f23931c;
            }
            androidx.core.util.z.g("The parent is closed. Call SurfaceEdge#invalidate() before setting a new provider.", !z12);
            this.f24512q = deferrableSurface;
            androidx.camera.core.impl.utils.futures.f.i(this.f24511p, deferrableSurface.c());
            deferrableSurface.d();
            androidx.camera.core.impl.utils.futures.f.h(this.f23933e).N(new u(deferrableSurface, 1), androidx.camera.core.impl.utils.executor.c.a());
            androidx.camera.core.impl.utils.futures.f.h(deferrableSurface.f23935g).N(runnable, androidx.camera.core.impl.utils.executor.c.d());
            return true;
        }
    }

    public z(int i12, int i13, @N G0 g02, @N Matrix matrix, boolean z12, @N Rect rect, int i14, int i15, boolean z13) {
        this.f24500f = i12;
        this.f24495a = i13;
        this.f24501g = g02;
        this.f24496b = matrix;
        this.f24497c = z12;
        this.f24498d = rect;
        this.f24503i = i14;
        this.f24502h = i15;
        this.f24499e = z13;
        this.f24507m = new a(g02.e(), i13);
    }

    @K
    public final void a(@N Runnable runnable) {
        androidx.camera.core.impl.utils.t.a();
        b();
        this.f24508n.add(runnable);
    }

    public final void b() {
        androidx.core.util.z.g("Edge is already closed.", !this.f24509o);
    }

    @N
    @K
    public final K0 c(@N CameraInternal cameraInternal) {
        androidx.camera.core.impl.utils.t.a();
        b();
        G0 g02 = this.f24501g;
        K0 k02 = new K0(g02.e(), cameraInternal, g02.b(), g02.c(), new t(this, 0));
        try {
            DeferrableSurface deferrableSurface = k02.f23599k;
            if (this.f24507m.g(deferrableSurface, new t(this, 1))) {
                androidx.camera.core.impl.utils.futures.f.h(this.f24507m.f23933e).N(new u(deferrableSurface, 0), androidx.camera.core.impl.utils.executor.c.a());
            }
            this.f24506l = k02;
            f();
            return k02;
        } catch (DeferrableSurface.SurfaceClosedException e12) {
            throw new AssertionError("Surface is somehow already closed", e12);
        } catch (RuntimeException e13) {
            k02.c();
            throw e13;
        }
    }

    public final void d() {
        androidx.camera.core.impl.utils.t.a();
        this.f24507m.a();
        A a12 = this.f24504j;
        if (a12 != null) {
            a12.a();
            this.f24504j = null;
        }
    }

    @K
    public final void e() {
        boolean z12;
        androidx.camera.core.impl.utils.t.a();
        b();
        a aVar = this.f24507m;
        aVar.getClass();
        androidx.camera.core.impl.utils.t.a();
        if (aVar.f24512q == null) {
            synchronized (aVar.f23929a) {
                z12 = aVar.f23931c;
            }
            if (!z12) {
                return;
            }
        }
        d();
        this.f24505k = false;
        this.f24507m = new a(this.f24501g.e(), this.f24495a);
        Iterator it = this.f24508n.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
    }

    @K
    public final void f() {
        K0.e eVar;
        Executor executor;
        androidx.camera.core.impl.utils.t.a();
        K0 k02 = this.f24506l;
        if (k02 != null) {
            K0.d dVarG = K0.d.g(this.f24498d, this.f24503i, this.f24502h, this.f24497c, this.f24496b, this.f24499e);
            synchronized (k02.f23589a) {
                k02.f23600l = dVarG;
                eVar = k02.f23601m;
                executor = k02.f23602n;
            }
            if (eVar == null || executor == null) {
                return;
            }
            executor.execute(new E0(eVar, dVarG, 0));
        }
    }

    @K
    public final void g(@N DeferrableSurface deferrableSurface) {
        androidx.camera.core.impl.utils.t.a();
        b();
        this.f24507m.g(deferrableSurface, new t(this, 1));
    }

    public final void h(final int i12, final int i13) {
        Runnable runnable = new Runnable() { // from class: androidx.camera.core.processing.v
            @Override // java.lang.Runnable
            public final void run() {
                boolean z12;
                z zVar = this.f24480b;
                int i14 = zVar.f24503i;
                int i15 = i12;
                boolean z13 = true;
                if (i14 != i15) {
                    zVar.f24503i = i15;
                    z12 = true;
                } else {
                    z12 = false;
                }
                int i16 = zVar.f24502h;
                int i17 = i13;
                if (i16 != i17) {
                    zVar.f24502h = i17;
                } else {
                    z13 = z12;
                }
                if (z13) {
                    zVar.f();
                }
            }
        };
        if (androidx.camera.core.impl.utils.t.b()) {
            runnable.run();
        } else {
            androidx.core.util.z.g("Unable to post to main thread", new Handler(Looper.getMainLooper()).post(runnable));
        }
    }
}
