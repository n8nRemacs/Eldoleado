package androidx.camera.core;

import G.c;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.N0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20086c0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.j;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

/* compiled from: Preview.java */
@j.X
/* loaded from: classes.dex */
public final class v0 extends N0 {

    /* renamed from: t, reason: collision with root package name */
    @RestrictTo
    public static final b f24566t = new b();

    /* renamed from: u, reason: collision with root package name */
    public static final Executor f24567u = androidx.camera.core.impl.utils.executor.c.d();

    /* renamed from: n, reason: collision with root package name */
    @j.P
    public c f24568n;

    /* renamed from: o, reason: collision with root package name */
    @j.N
    public Executor f24569o;

    /* renamed from: p, reason: collision with root package name */
    public SessionConfig.b f24570p;

    /* renamed from: q, reason: collision with root package name */
    public DeferrableSurface f24571q;

    /* renamed from: r, reason: collision with root package name */
    @j.P
    public androidx.camera.core.processing.z f24572r;

    /* renamed from: s, reason: collision with root package name */
    @j.k0
    @j.P
    public K0 f24573s;

    /* compiled from: Preview.java */
    public static final class a implements L0.a<v0, androidx.camera.core.impl.u0, a>, InterfaceC20088d0.a<a>, j.a<a> {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.camera.core.impl.n0 f24574a;

        public a() {
            this(androidx.camera.core.impl.n0.R());
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        @Deprecated
        public final a a(@j.N Size size) {
            this.f24574a.K(InterfaceC20088d0.f24097k, size);
            return this;
        }

        @Override // androidx.camera.core.impl.L0.a
        @j.N
        @RestrictTo
        public final androidx.camera.core.impl.L0 b() {
            return new androidx.camera.core.impl.u0(androidx.camera.core.impl.s0.Q(this.f24574a));
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        public final /* bridge */ /* synthetic */ a c(int i12) {
            f(i12);
            return this;
        }

        @Override // androidx.camera.core.L
        @j.N
        @RestrictTo
        public final InterfaceC20106m0 d() {
            return this.f24574a;
        }

        @j.N
        public final v0 e() {
            androidx.camera.core.impl.u0 u0Var = new androidx.camera.core.impl.u0(androidx.camera.core.impl.s0.Q(this.f24574a));
            InterfaceC20088d0.v(u0Var);
            v0 v0Var = new v0(u0Var);
            v0Var.f24569o = v0.f24567u;
            return v0Var;
        }

        @j.N
        public final void f(int i12) {
            Config.a<Integer> aVar = InterfaceC20088d0.f24094h;
            Integer numValueOf = Integer.valueOf(i12);
            androidx.camera.core.impl.n0 n0Var = this.f24574a;
            n0Var.K(aVar, numValueOf);
            n0Var.K(InterfaceC20088d0.f24095i, Integer.valueOf(i12));
        }

        public a(androidx.camera.core.impl.n0 n0Var) {
            Object objF;
            this.f24574a = n0Var;
            Object objF2 = null;
            try {
                objF = n0Var.f(androidx.camera.core.internal.i.f24362C);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Class cls = (Class) objF;
            if (cls != null && !cls.equals(v0.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
            androidx.camera.core.impl.n0 n0Var2 = this.f24574a;
            n0Var2.K(aVar, v0.class);
            try {
                objF2 = n0Var2.f(androidx.camera.core.internal.i.f24361B);
            } catch (IllegalArgumentException unused2) {
            }
            if (objF2 == null) {
                this.f24574a.K(androidx.camera.core.internal.i.f24361B, v0.class.getCanonicalName() + "-" + UUID.randomUUID());
            }
            n0Var.K(InterfaceC20088d0.f24096j, 2);
        }
    }

    /* compiled from: Preview.java */
    @RestrictTo
    public static final class b implements androidx.camera.core.impl.J<androidx.camera.core.impl.u0> {

        /* renamed from: a, reason: collision with root package name */
        public static final androidx.camera.core.impl.u0 f24575a;

        static {
            c.b bVar = new c.b();
            bVar.f6127a = G.a.f6123b;
            bVar.f6128b = G.d.f6129c;
            G.c cVarA = bVar.a();
            a aVar = new a();
            Config.a<Integer> aVar2 = androidx.camera.core.impl.L0.f24001u;
            androidx.camera.core.impl.n0 n0Var = aVar.f24574a;
            n0Var.K(aVar2, 2);
            n0Var.K(InterfaceC20088d0.f24093g, 0);
            n0Var.K(InterfaceC20088d0.f24101o, cVarA);
            n0Var.K(androidx.camera.core.impl.L0.f24006z, UseCaseConfigFactory.CaptureType.f24055c);
            f24575a = new androidx.camera.core.impl.u0(androidx.camera.core.impl.s0.Q(n0Var));
        }
    }

    /* compiled from: Preview.java */
    public interface c {
        void a(@j.N K0 k02);
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void B(@j.N Rect rect) {
        this.f23623i = rect;
        CameraInternal cameraInternalC = c();
        androidx.camera.core.processing.z zVar = this.f24572r;
        if (cameraInternalC == null || zVar == null) {
            return;
        }
        zVar.h(h(cameraInternalC, m(cameraInternalC)), ((InterfaceC20088d0) this.f23620f).s());
    }

    public final void E() {
        DeferrableSurface deferrableSurface = this.f24571q;
        if (deferrableSurface != null) {
            deferrableSurface.a();
            this.f24571q = null;
        }
        androidx.camera.core.processing.z zVar = this.f24572r;
        if (zVar != null) {
            androidx.camera.core.impl.utils.t.a();
            zVar.d();
            zVar.f24509o = true;
            this.f24572r = null;
        }
        this.f24573s = null;
    }

    @j.N
    @j.K
    public final SessionConfig.b F(@j.N String str, @j.N androidx.camera.core.impl.u0 u0Var, @j.N androidx.camera.core.impl.G0 g02) {
        Rect rect;
        androidx.camera.core.impl.utils.t.a();
        CameraInternal cameraInternalC = c();
        Objects.requireNonNull(cameraInternalC);
        E();
        androidx.core.util.z.g(null, this.f24572r == null);
        Matrix matrix = this.f23624j;
        boolean zJ2 = cameraInternalC.j();
        Size sizeE = g02.e();
        Rect rect2 = this.f23623i;
        if (rect2 != null) {
            rect = rect2;
        } else if (sizeE != null) {
            rect2 = new Rect(0, 0, sizeE.getWidth(), sizeE.getHeight());
            rect = rect2;
        } else {
            rect = null;
        }
        Objects.requireNonNull(rect);
        androidx.camera.core.processing.z zVar = new androidx.camera.core.processing.z(1, 34, g02, matrix, zJ2, rect, h(cameraInternalC, m(cameraInternalC)), ((InterfaceC20088d0) this.f23620f).s(), cameraInternalC.j() && m(cameraInternalC));
        this.f24572r = zVar;
        if (this.f23626l != null) {
            throw null;
        }
        zVar.a(new RunnableC20048a0(this, 1));
        K0 k0C = this.f24572r.c(cameraInternalC);
        this.f24573s = k0C;
        this.f24571q = k0C.f23599k;
        if (this.f24568n != null) {
            CameraInternal cameraInternalC2 = c();
            androidx.camera.core.processing.z zVar2 = this.f24572r;
            if (cameraInternalC2 != null && zVar2 != null) {
                zVar2.h(h(cameraInternalC2, m(cameraInternalC2)), ((InterfaceC20088d0) this.f23620f).s());
            }
            c cVar = this.f24568n;
            cVar.getClass();
            K0 k02 = this.f24573s;
            k02.getClass();
            this.f24569o.execute(new RunnableC20051c(3, cVar, k02));
        }
        SessionConfig.b bVarM = SessionConfig.b.m(u0Var, g02.e());
        bVarM.p(g02.c());
        if (g02.d() != null) {
            bVarM.e(g02.d());
        }
        if (this.f24568n != null) {
            bVarM.i(this.f24571q, g02.b());
        }
        bVarM.d(new P(this, str, u0Var, g02, 2));
        return bVarM;
    }

    @j.j0
    public final void G(@j.P c cVar) {
        Executor executor = f24567u;
        androidx.camera.core.impl.utils.t.a();
        if (cVar == null) {
            this.f24568n = null;
            this.f23617c = N0.c.f23630c;
            q();
            return;
        }
        this.f24568n = cVar;
        this.f24569o = executor;
        if (b() != null) {
            SessionConfig.b bVarF = F(e(), (androidx.camera.core.impl.u0) this.f23620f, this.f23621g);
            this.f24570p = bVarF;
            D(bVarF.k());
            p();
        }
        o();
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    @j.P
    public final androidx.camera.core.impl.L0<?> f(boolean z12, @j.N UseCaseConfigFactory useCaseConfigFactory) {
        f24566t.getClass();
        androidx.camera.core.impl.u0 u0Var = b.f24575a;
        Config configA = useCaseConfigFactory.a(u0Var.z(), 1);
        if (z12) {
            configA = Config.P(configA, u0Var);
        }
        if (configA == null) {
            return null;
        }
        return new androidx.camera.core.impl.u0(androidx.camera.core.impl.s0.Q(((a) j(configA)).f24574a));
    }

    @Override // androidx.camera.core.N0
    @j.F
    @RestrictTo
    public final int h(@j.N CameraInternal cameraInternal, boolean z12) {
        if (cameraInternal.j()) {
            return super.h(cameraInternal, z12);
        }
        return 0;
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final Set<Integer> i() {
        HashSet hashSet = new HashSet();
        hashSet.add(1);
        return hashSet;
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final L0.a<?, ?, ?> j(@j.N Config config) {
        return new a(androidx.camera.core.impl.n0.S(config));
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.L0<?> t(@j.N androidx.camera.core.impl.B b12, @j.N L0.a<?, ?, ?> aVar) {
        aVar.d().K(InterfaceC20086c0.f24088e, 34);
        return aVar.b();
    }

    @j.N
    public final String toString() {
        return "Preview:".concat(g());
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 w(@j.N Config config) {
        this.f24570p.e(config);
        D(this.f24570p.k());
        G0.a aVarF = this.f23621g.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 x(@j.N androidx.camera.core.impl.G0 g02) {
        SessionConfig.b bVarF = F(e(), (androidx.camera.core.impl.u0) this.f23620f, g02);
        this.f24570p = bVarF;
        D(bVarF.k());
        return g02;
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void y() {
        E();
    }
}
