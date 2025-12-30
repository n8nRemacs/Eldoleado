package androidx.camera.core;

import G.c;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.N0;
import androidx.camera.core.impl.C20094g0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20086c0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.j;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;
import java.util.concurrent.Executor;

/* compiled from: ImageAnalysis.java */
@j.X
/* loaded from: classes.dex */
public final class T extends N0 {

    /* renamed from: s, reason: collision with root package name */
    @RestrictTo
    public static final d f23653s = new d();

    /* renamed from: n, reason: collision with root package name */
    public final W f23654n;

    /* renamed from: o, reason: collision with root package name */
    public final Object f23655o;

    /* renamed from: p, reason: collision with root package name */
    @j.B
    public a f23656p;

    /* renamed from: q, reason: collision with root package name */
    public SessionConfig.b f23657q;

    /* renamed from: r, reason: collision with root package name */
    @j.P
    public C20094g0 f23658r;

    /* compiled from: ImageAnalysis.java */
    public interface a {
        void d(@j.N z0 z0Var);
    }

    /* compiled from: ImageAnalysis.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface b {
    }

    /* compiled from: ImageAnalysis.java */
    public static final class c implements InterfaceC20088d0.a<c>, j.a<c>, L0.a<T, androidx.camera.core.impl.Y, c>, InterfaceC20086c0.a<c> {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.camera.core.impl.n0 f23659a;

        public c() {
            this(androidx.camera.core.impl.n0.R());
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        @Deprecated
        public final c a(@j.N Size size) {
            this.f23659a.K(InterfaceC20088d0.f24097k, size);
            return this;
        }

        @Override // androidx.camera.core.impl.L0.a
        @j.N
        @RestrictTo
        public final androidx.camera.core.impl.L0 b() {
            return new androidx.camera.core.impl.Y(androidx.camera.core.impl.s0.Q(this.f23659a));
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        public final c c(int i12) {
            this.f23659a.K(InterfaceC20088d0.f24094h, Integer.valueOf(i12));
            return this;
        }

        @Override // androidx.camera.core.L
        @j.N
        @RestrictTo
        public final InterfaceC20106m0 d() {
            return this.f23659a;
        }

        public c(androidx.camera.core.impl.n0 n0Var) {
            Object objF;
            this.f23659a = n0Var;
            Object objF2 = null;
            try {
                objF = n0Var.f(androidx.camera.core.internal.i.f24362C);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Class cls = (Class) objF;
            if (cls != null && !cls.equals(T.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
            androidx.camera.core.impl.n0 n0Var2 = this.f23659a;
            n0Var2.K(aVar, T.class);
            try {
                objF2 = n0Var2.f(androidx.camera.core.internal.i.f24361B);
            } catch (IllegalArgumentException unused2) {
            }
            if (objF2 == null) {
                n0Var2.K(androidx.camera.core.internal.i.f24361B, T.class.getCanonicalName() + "-" + UUID.randomUUID());
            }
        }
    }

    /* compiled from: ImageAnalysis.java */
    @RestrictTo
    public static final class d implements androidx.camera.core.impl.J<androidx.camera.core.impl.Y> {

        /* renamed from: a, reason: collision with root package name */
        public static final androidx.camera.core.impl.Y f23660a;

        static {
            Size size = new Size(640, 480);
            F f12 = F.f23562d;
            c.b bVar = new c.b();
            bVar.f6127a = G.a.f6123b;
            bVar.f6128b = new G.d(androidx.camera.core.internal.utils.c.f24372c);
            G.c cVarA = bVar.a();
            c cVar = new c();
            Config.a<Size> aVar = InterfaceC20088d0.f24098l;
            androidx.camera.core.impl.n0 n0Var = cVar.f23659a;
            n0Var.K(aVar, size);
            n0Var.K(androidx.camera.core.impl.L0.f24001u, 1);
            n0Var.K(InterfaceC20088d0.f24093g, 0);
            n0Var.K(InterfaceC20088d0.f24101o, cVarA);
            n0Var.K(androidx.camera.core.impl.L0.f24006z, UseCaseConfigFactory.CaptureType.f24056d);
            if (!f12.equals(f12)) {
                throw new UnsupportedOperationException("ImageAnalysis currently only supports SDR");
            }
            n0Var.K(InterfaceC20086c0.f24089f, f12);
            f23660a = new androidx.camera.core.impl.Y(androidx.camera.core.impl.s0.Q(n0Var));
        }
    }

    /* compiled from: ImageAnalysis.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface e {
    }

    public T(@j.N androidx.camera.core.impl.Y y12) {
        super(y12);
        this.f23655o = new Object();
        if (((Integer) ((androidx.camera.core.impl.Y) this.f23620f).g(androidx.camera.core.impl.Y.f24063G, 0)).intValue() == 1) {
            this.f23654n = new X();
        } else {
            this.f23654n = new Y((Executor) y12.g(androidx.camera.core.internal.j.f24363D, androidx.camera.core.impl.utils.executor.c.b()));
        }
        this.f23654n.f23678e = G();
        this.f23654n.f23679f = ((Boolean) ((androidx.camera.core.impl.Y) this.f23620f).g(androidx.camera.core.impl.Y.f24068L, Boolean.FALSE)).booleanValue();
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void B(@j.N Rect rect) {
        this.f23623i = rect;
        W w12 = this.f23654n;
        synchronized (w12.f23692s) {
            w12.f23684k = rect;
            w12.f23685l = new Rect(w12.f23684k);
        }
    }

    public final void E() {
        synchronized (this.f23655o) {
            W w12 = this.f23654n;
            w12.d();
            synchronized (w12.f23692s) {
                w12.f23675b = null;
                w12.f23681h = null;
            }
            if (this.f23656p != null) {
                this.f23617c = N0.c.f23630c;
                q();
            }
            this.f23656p = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final androidx.camera.core.impl.SessionConfig.b F(@j.N java.lang.String r16, @j.N androidx.camera.core.impl.Y r17, @j.N androidx.camera.core.impl.G0 r18) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.T.F(java.lang.String, androidx.camera.core.impl.Y, androidx.camera.core.impl.G0):androidx.camera.core.impl.SessionConfig$b");
    }

    public final int G() {
        return ((Integer) ((androidx.camera.core.impl.Y) this.f23620f).g(androidx.camera.core.impl.Y.f24066J, 1)).intValue();
    }

    public final void H(@j.N Executor executor, @j.N a aVar) {
        synchronized (this.f23655o) {
            try {
                W w12 = this.f23654n;
                S s5 = new S(aVar);
                synchronized (w12.f23692s) {
                    w12.f23675b = s5;
                    w12.f23681h = executor;
                }
                if (this.f23656p == null) {
                    o();
                }
                this.f23656p = aVar;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void I() {
        CameraInternal cameraInternalC = c();
        if (cameraInternalC != null) {
            this.f23654n.f23676c = h(cameraInternalC, false);
        }
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    @j.P
    public final androidx.camera.core.impl.L0<?> f(boolean z12, @j.N UseCaseConfigFactory useCaseConfigFactory) {
        f23653s.getClass();
        androidx.camera.core.impl.Y y12 = d.f23660a;
        Config configA = useCaseConfigFactory.a(y12.z(), 1);
        if (z12) {
            configA = Config.P(configA, y12);
        }
        if (configA == null) {
            return null;
        }
        return new androidx.camera.core.impl.Y(androidx.camera.core.impl.s0.Q(((c) j(configA)).f23659a));
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final L0.a<?, ?, ?> j(@j.N Config config) {
        return new c(androidx.camera.core.impl.n0.S(config));
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void r() {
        this.f23654n.f23693t = true;
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.L0<?> t(@j.N androidx.camera.core.impl.B b12, @j.N L0.a<?, ?, ?> aVar) {
        Boolean bool = (Boolean) ((androidx.camera.core.impl.Y) this.f23620f).g(androidx.camera.core.impl.Y.f24067K, null);
        boolean zA2 = b12.g().a(androidx.camera.core.internal.compat.quirk.h.class);
        W w12 = this.f23654n;
        if (bool != null) {
            zA2 = bool.booleanValue();
        }
        w12.f23680g = zA2;
        synchronized (this.f23655o) {
        }
        return aVar.b();
    }

    @j.N
    public final String toString() {
        return "ImageAnalysis:".concat(g());
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 w(@j.N Config config) {
        this.f23657q.e(config);
        D(this.f23657q.k());
        G0.a aVarF = this.f23621g.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 x(@j.N androidx.camera.core.impl.G0 g02) {
        SessionConfig.b bVarF = F(e(), (androidx.camera.core.impl.Y) this.f23620f, g02);
        this.f23657q = bVarF;
        D(bVarF.k());
        return g02;
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void y() {
        androidx.camera.core.impl.utils.t.a();
        C20094g0 c20094g0 = this.f23658r;
        if (c20094g0 != null) {
            c20094g0.a();
            this.f23658r = null;
        }
        W w12 = this.f23654n;
        w12.f23693t = false;
        w12.d();
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void z(@j.N Matrix matrix) {
        super.z(matrix);
        W w12 = this.f23654n;
        synchronized (w12.f23692s) {
            w12.f23686m = matrix;
            w12.f23687n = new Matrix(w12.f23686m);
        }
    }
}
