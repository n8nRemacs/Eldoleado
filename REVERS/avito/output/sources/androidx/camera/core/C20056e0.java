package androidx.camera.core;

import G.c;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Looper;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20056e0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.G0;
import androidx.camera.core.impl.InterfaceC20086c0;
import androidx.camera.core.impl.InterfaceC20088d0;
import androidx.camera.core.impl.InterfaceC20106m0;
import androidx.camera.core.impl.L0;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.f;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import ru.cyberity.cbr.core.domain.camera.CameraX;

/* compiled from: ImageCapture.java */
@j.X
/* renamed from: androidx.camera.core.e0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20056e0 extends N0 {

    /* renamed from: w, reason: collision with root package name */
    @RestrictTo
    public static final d f23725w = new d();

    /* renamed from: n, reason: collision with root package name */
    public final int f23726n;

    /* renamed from: o, reason: collision with root package name */
    @j.B
    public final AtomicReference<Integer> f23727o;

    /* renamed from: p, reason: collision with root package name */
    public final int f23728p;

    /* renamed from: q, reason: collision with root package name */
    @j.B
    public int f23729q;

    /* renamed from: r, reason: collision with root package name */
    public Rational f23730r;

    /* renamed from: s, reason: collision with root package name */
    public SessionConfig.b f23731s;

    /* renamed from: t, reason: collision with root package name */
    @j.P
    public androidx.camera.core.imagecapture.r f23732t;

    /* renamed from: u, reason: collision with root package name */
    @j.P
    public androidx.camera.core.imagecapture.N f23733u;

    /* renamed from: v, reason: collision with root package name */
    public final androidx.camera.core.imagecapture.q f23734v;

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$a */
    public class a implements androidx.camera.core.imagecapture.q {
        public a() {
        }

        @Override // androidx.camera.core.imagecapture.q
        @j.K
        public final void a() {
            C20056e0 c20056e0 = C20056e0.this;
            synchronized (c20056e0.f23727o) {
                try {
                    if (c20056e0.f23727o.get() != null) {
                        return;
                    }
                    c20056e0.f23727o.set(Integer.valueOf(c20056e0.G()));
                } finally {
                }
            }
        }

        @Override // androidx.camera.core.imagecapture.q
        @j.K
        public final void b() {
            C20056e0 c20056e0 = C20056e0.this;
            synchronized (c20056e0.f23727o) {
                try {
                    Integer andSet = c20056e0.f23727o.getAndSet(null);
                    if (andSet == null) {
                        return;
                    }
                    if (andSet.intValue() != c20056e0.G()) {
                        c20056e0.M();
                    }
                } finally {
                }
            }
        }

        @Override // androidx.camera.core.imagecapture.q
        @j.N
        @j.K
        public final com.google.common.util.concurrent.D0 c(@j.N ArrayList arrayList) {
            C20056e0 c20056e0 = C20056e0.this;
            c20056e0.getClass();
            androidx.camera.core.impl.utils.t.a();
            return androidx.camera.core.impl.utils.futures.f.l(c20056e0.d().d(arrayList, c20056e0.f23726n, c20056e0.f23728p), new C20054d0(), androidx.camera.core.impl.utils.executor.c.a());
        }
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$b */
    public static final class b implements L0.a<C20056e0, androidx.camera.core.impl.Z, b>, InterfaceC20088d0.a<b>, f.a<b>, InterfaceC20086c0.a<b> {

        /* renamed from: a, reason: collision with root package name */
        public final androidx.camera.core.impl.n0 f23736a;

        public b() {
            this(androidx.camera.core.impl.n0.R());
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        @Deprecated
        public final b a(@j.N Size size) {
            this.f23736a.K(InterfaceC20088d0.f24097k, size);
            return this;
        }

        @Override // androidx.camera.core.impl.L0.a
        @j.N
        @RestrictTo
        public final androidx.camera.core.impl.L0 b() {
            return new androidx.camera.core.impl.Z(androidx.camera.core.impl.s0.Q(this.f23736a));
        }

        @Override // androidx.camera.core.impl.InterfaceC20088d0.a
        @j.N
        public final b c(int i12) {
            this.f23736a.K(InterfaceC20088d0.f24094h, Integer.valueOf(i12));
            return this;
        }

        @Override // androidx.camera.core.L
        @j.N
        @RestrictTo
        public final InterfaceC20106m0 d() {
            return this.f23736a;
        }

        @j.N
        public final C20056e0 e() {
            Object objF;
            Integer num;
            Config.a<Integer> aVar = androidx.camera.core.impl.Z.f24073J;
            androidx.camera.core.impl.n0 n0Var = this.f23736a;
            n0Var.getClass();
            Object objF2 = null;
            try {
                objF = n0Var.f(aVar);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Integer num2 = (Integer) objF;
            if (num2 != null) {
                n0Var.K(InterfaceC20086c0.f24088e, num2);
            } else {
                n0Var.K(InterfaceC20086c0.f24088e, 256);
            }
            androidx.camera.core.impl.Z z12 = new androidx.camera.core.impl.Z(androidx.camera.core.impl.s0.Q(n0Var));
            InterfaceC20088d0.v(z12);
            C20056e0 c20056e0 = new C20056e0(z12);
            try {
                objF2 = n0Var.f(InterfaceC20088d0.f24097k);
            } catch (IllegalArgumentException unused2) {
            }
            Size size = (Size) objF2;
            if (size != null) {
                c20056e0.f23730r = new Rational(size.getWidth(), size.getHeight());
            }
            Config.a<Executor> aVar2 = androidx.camera.core.internal.f.f24350A;
            Object objC = androidx.camera.core.impl.utils.executor.c.c();
            try {
                objC = n0Var.f(aVar2);
            } catch (IllegalArgumentException unused3) {
            }
            androidx.core.util.z.f((Executor) objC, "The IO executor can't be null");
            Config.a<Integer> aVar3 = androidx.camera.core.impl.Z.f24071H;
            if (!n0Var.f24173F.containsKey(aVar3) || ((num = (Integer) n0Var.f(aVar3)) != null && (num.intValue() == 0 || num.intValue() == 1 || num.intValue() == 2))) {
                return c20056e0;
            }
            throw new IllegalArgumentException("The flash mode is not allowed to set: " + num);
        }

        public b(androidx.camera.core.impl.n0 n0Var) {
            Object objF;
            this.f23736a = n0Var;
            Object objF2 = null;
            try {
                objF = n0Var.f(androidx.camera.core.internal.i.f24362C);
            } catch (IllegalArgumentException unused) {
                objF = null;
            }
            Class cls = (Class) objF;
            if (cls != null && !cls.equals(C20056e0.class)) {
                throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
            }
            Config.a<Class<?>> aVar = androidx.camera.core.internal.i.f24362C;
            androidx.camera.core.impl.n0 n0Var2 = this.f23736a;
            n0Var2.K(aVar, C20056e0.class);
            try {
                objF2 = n0Var2.f(androidx.camera.core.internal.i.f24361B);
            } catch (IllegalArgumentException unused2) {
            }
            if (objF2 == null) {
                this.f23736a.K(androidx.camera.core.internal.i.f24361B, C20056e0.class.getCanonicalName() + "-" + UUID.randomUUID());
            }
        }
    }

    /* compiled from: ImageCapture.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.core.e0$c */
    public @interface c {
    }

    /* compiled from: ImageCapture.java */
    @RestrictTo
    /* renamed from: androidx.camera.core.e0$d */
    public static final class d implements androidx.camera.core.impl.J<androidx.camera.core.impl.Z> {

        /* renamed from: a, reason: collision with root package name */
        public static final androidx.camera.core.impl.Z f23737a;

        static {
            c.b bVar = new c.b();
            bVar.f6127a = G.a.f6123b;
            bVar.f6128b = G.d.f6129c;
            G.c cVarA = bVar.a();
            F f12 = F.f23562d;
            b bVar2 = new b();
            Config.a<Integer> aVar = androidx.camera.core.impl.L0.f24001u;
            androidx.camera.core.impl.n0 n0Var = bVar2.f23736a;
            n0Var.K(aVar, 4);
            n0Var.K(InterfaceC20088d0.f24093g, 0);
            n0Var.K(InterfaceC20088d0.f24101o, cVarA);
            n0Var.K(androidx.camera.core.impl.L0.f24006z, UseCaseConfigFactory.CaptureType.f24054b);
            if (!f12.equals(f12)) {
                throw new UnsupportedOperationException("ImageCapture currently only supports SDR");
            }
            n0Var.K(InterfaceC20086c0.f24089f, f12);
            f23737a = new androidx.camera.core.impl.Z(androidx.camera.core.impl.s0.Q(n0Var));
        }
    }

    /* compiled from: ImageCapture.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.core.e0$e */
    public @interface e {
    }

    /* compiled from: ImageCapture.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.core.e0$f */
    public @interface f {
    }

    /* compiled from: ImageCapture.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.core.e0$g */
    public @interface g {
    }

    /* compiled from: ImageCapture.java */
    @j.k0
    /* renamed from: androidx.camera.core.e0$h */
    public static class h {
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$i */
    public static final class i {
        @j.N
        public final String toString() {
            return "Metadata{mIsReversedHorizontal=false, mIsReversedVertical=false, mLocation=null}";
        }
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$k */
    public interface k {
        void onError(@j.N ImageCaptureException imageCaptureException);

        void onImageSaved(@j.N m mVar);
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$l */
    public static final class l {

        /* renamed from: a, reason: collision with root package name */
        @j.P
        public final File f23738a;

        /* renamed from: b, reason: collision with root package name */
        @j.N
        public final i f23739b = new i();

        /* compiled from: ImageCapture.java */
        /* renamed from: androidx.camera.core.e0$l$a */
        public static final class a {
        }

        public l(@j.P File file) {
            this.f23738a = file;
        }

        @j.N
        public final String toString() {
            return "OutputFileOptions{mFile=" + this.f23738a + ", mContentResolver=null, mSaveCollection=null, mContentValues=null, mOutputStream=null, mMetadata=" + this.f23739b + "}";
        }
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$m */
    public static class m {
    }

    static {
        new androidx.camera.core.internal.compat.workaround.b();
    }

    public C20056e0(@j.N androidx.camera.core.impl.Z z12) {
        super(z12);
        this.f23727o = new AtomicReference<>(null);
        this.f23729q = -1;
        this.f23730r = null;
        this.f23734v = new a();
        androidx.camera.core.impl.Z z13 = (androidx.camera.core.impl.Z) this.f23620f;
        Config.a<Integer> aVar = androidx.camera.core.impl.Z.f24070G;
        if (z13.c(aVar)) {
            this.f23726n = ((Integer) z13.f(aVar)).intValue();
        } else {
            this.f23726n = 1;
        }
        this.f23728p = ((Integer) z13.g(androidx.camera.core.impl.Z.f24076M, 0)).intValue();
    }

    public static boolean H(int i12, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((Integer) ((Pair) it.next()).first).equals(Integer.valueOf(i12))) {
                return true;
            }
        }
        return false;
    }

    @j.K
    public final void E(boolean z12) {
        androidx.camera.core.imagecapture.N n12;
        androidx.camera.core.impl.utils.t.a();
        androidx.camera.core.imagecapture.r rVar = this.f23732t;
        if (rVar != null) {
            rVar.a();
            this.f23732t = null;
        }
        if (z12 || (n12 = this.f23733u) == null) {
            return;
        }
        n12.a();
        this.f23733u = null;
    }

    @j.K
    @j.S
    public final SessionConfig.b F(@j.N String str, @j.N androidx.camera.core.impl.Z z12, @j.N androidx.camera.core.impl.G0 g02) {
        androidx.camera.core.impl.utils.t.a();
        String.format("createPipeline(cameraId: %s, streamSpec: %s)", str, g02);
        Size sizeE = g02.e();
        CameraInternal cameraInternalC = c();
        Objects.requireNonNull(cameraInternalC);
        boolean z13 = !cameraInternalC.j() || I();
        if (this.f23732t != null) {
            androidx.core.util.z.g(null, z13);
            this.f23732t.a();
        }
        this.f23732t = new androidx.camera.core.imagecapture.r(z12, sizeE, this.f23626l, z13);
        if (this.f23733u == null) {
            this.f23733u = new androidx.camera.core.imagecapture.N(this.f23734v);
        }
        this.f23733u.f(this.f23732t);
        SessionConfig.b bVarB = this.f23732t.b(g02.e());
        if (this.f23726n == 2) {
            d().l(bVarB);
        }
        if (g02.d() != null) {
            bVarB.e(g02.d());
        }
        bVarB.d(new P(this, str, z12, g02, 1));
        return bVarB;
    }

    public final int G() {
        int iIntValue;
        synchronized (this.f23727o) {
            iIntValue = this.f23729q;
            if (iIntValue == -1) {
                iIntValue = ((Integer) ((androidx.camera.core.impl.Z) this.f23620f).g(androidx.camera.core.impl.Z.f24071H, 2)).intValue();
            }
        }
        return iIntValue;
    }

    public final boolean I() {
        return (c() == null || c().h().w() == null) ? false : true;
    }

    public final void J(int i12) {
        if (i12 != 0 && i12 != 1 && i12 != 2) {
            throw new IllegalArgumentException(AK.c.g(i12, "Invalid flash mode: "));
        }
        synchronized (this.f23727o) {
            this.f23729q = i12;
            M();
        }
    }

    public final void K(@j.N final Executor executor, @j.N final j jVar) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            androidx.camera.core.impl.utils.executor.c.d().execute(new Runnable() { // from class: androidx.camera.core.b0
                @Override // java.lang.Runnable
                public final void run() {
                    C20056e0.d dVar = C20056e0.f23725w;
                    this.f23712b.K(executor, jVar);
                }
            });
        } else {
            L(executor, jVar, null, null);
        }
    }

    @j.K
    public final void L(@j.N Executor executor, @j.P j jVar, @j.P CameraX.d dVar, @j.P l lVar) {
        Rect rect;
        int iRound;
        int i12;
        int i13;
        int i14;
        int iIntValue;
        androidx.camera.core.impl.utils.t.a();
        CameraInternal cameraInternalC = c();
        Rect rect2 = null;
        if (cameraInternalC == null) {
            ImageCaptureException imageCaptureException = new ImageCaptureException("Not bound to a valid Camera [" + this + "]", null);
            if (jVar != null) {
                return;
            }
            if (dVar == null) {
                throw new IllegalArgumentException("Must have either in-memory or on-disk callback.");
            }
            dVar.onError(imageCaptureException);
            return;
        }
        androidx.camera.core.imagecapture.N n12 = this.f23733u;
        Objects.requireNonNull(n12);
        Rect rect3 = this.f23623i;
        Size sizeB = b();
        Objects.requireNonNull(sizeB);
        if (rect3 != null) {
            rect = rect3;
        } else {
            Rational rational = this.f23730r;
            if (rational == null || rational.floatValue() <= 0.0f || rational.isNaN()) {
                rect2 = new Rect(0, 0, sizeB.getWidth(), sizeB.getHeight());
            } else {
                CameraInternal cameraInternalC2 = c();
                Objects.requireNonNull(cameraInternalC2);
                int iH2 = h(cameraInternalC2, false);
                Rational rational2 = new Rational(this.f23730r.getDenominator(), this.f23730r.getNumerator());
                if (!androidx.camera.core.impl.utils.u.c(iH2)) {
                    rational2 = this.f23730r;
                }
                if (rational2 == null || rational2.floatValue() <= 0.0f || rational2.isNaN()) {
                    C20140q0.d(5, "ImageUtil");
                } else {
                    int width = sizeB.getWidth();
                    int height = sizeB.getHeight();
                    float f12 = width;
                    float f13 = height;
                    float f14 = f12 / f13;
                    int numerator = rational2.getNumerator();
                    int denominator = rational2.getDenominator();
                    if (rational2.floatValue() > f14) {
                        int iRound2 = Math.round((f12 / numerator) * denominator);
                        i14 = (height - iRound2) / 2;
                        i13 = iRound2;
                        iRound = width;
                        i12 = 0;
                    } else {
                        iRound = Math.round((f13 / denominator) * numerator);
                        i12 = (width - iRound) / 2;
                        i13 = height;
                        i14 = 0;
                    }
                    rect2 = new Rect(i12, i14, iRound + i12, i13 + i14);
                }
                Objects.requireNonNull(rect2);
            }
            rect = rect2;
        }
        Matrix matrix = this.f23624j;
        int iH3 = h(cameraInternalC, false);
        androidx.camera.core.impl.Z z12 = (androidx.camera.core.impl.Z) this.f23620f;
        Config.a<Integer> aVar = androidx.camera.core.impl.Z.f24077N;
        if (z12.c(aVar)) {
            iIntValue = ((Integer) z12.f(aVar)).intValue();
        } else {
            int i15 = this.f23726n;
            if (i15 == 0) {
                iIntValue = 100;
            } else {
                if (i15 != 1 && i15 != 2) {
                    throw new IllegalStateException(androidx.camera.camera2.internal.G.e(i15, "CaptureMode ", " is invalid"));
                }
                iIntValue = 95;
            }
        }
        androidx.camera.core.imagecapture.O oK2 = androidx.camera.core.imagecapture.O.k(executor, jVar, dVar, lVar, rect, matrix, iH3, iIntValue, this.f23726n, this.f23731s.n());
        androidx.camera.core.impl.utils.t.a();
        n12.f23801a.offer(oK2);
        n12.b();
    }

    public final void M() {
        synchronized (this.f23727o) {
            try {
                if (this.f23727o.get() != null) {
                    return;
                }
                d().k(G());
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    @j.P
    public final androidx.camera.core.impl.L0<?> f(boolean z12, @j.N UseCaseConfigFactory useCaseConfigFactory) {
        f23725w.getClass();
        androidx.camera.core.impl.Z z13 = d.f23737a;
        Config configA = useCaseConfigFactory.a(z13.z(), this.f23726n);
        if (z12) {
            configA = Config.P(configA, z13);
        }
        if (configA == null) {
            return null;
        }
        return new androidx.camera.core.impl.Z(androidx.camera.core.impl.s0.Q(((b) j(configA)).f23736a));
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final Set<Integer> i() {
        HashSet hashSet = new HashSet();
        hashSet.add(4);
        return hashSet;
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final L0.a<?, ?, ?> j(@j.N Config config) {
        return new b(androidx.camera.core.impl.n0.S(config));
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void r() {
        androidx.core.util.z.f(c(), "Attached camera cannot be null");
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void s() {
        M();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.L0<?> t(@j.N androidx.camera.core.impl.B b12, @j.N L0.a<?, ?, ?> aVar) {
        boolean z12;
        if (b12.g().a(androidx.camera.core.internal.compat.quirk.i.class)) {
            Boolean bool = Boolean.FALSE;
            InterfaceC20106m0 interfaceC20106m0D = aVar.d();
            Config.a<Boolean> aVar2 = androidx.camera.core.impl.Z.f24075L;
            Boolean bool2 = Boolean.TRUE;
            if (bool.equals(interfaceC20106m0D.g(aVar2, bool2))) {
                C20140q0.d(5, "ImageCapture");
            } else {
                C20140q0.d(4, "ImageCapture");
                aVar.d().K(aVar2, bool2);
            }
        }
        InterfaceC20106m0 interfaceC20106m0D2 = aVar.d();
        Boolean bool3 = Boolean.TRUE;
        Config.a<Boolean> aVar3 = androidx.camera.core.impl.Z.f24075L;
        Boolean bool4 = Boolean.FALSE;
        boolean z13 = true;
        if (bool3.equals(interfaceC20106m0D2.g(aVar3, bool4))) {
            if (I()) {
                C20140q0.d(5, "ImageCapture");
                z12 = false;
            } else {
                z12 = true;
            }
            Integer num = (Integer) interfaceC20106m0D2.g(androidx.camera.core.impl.Z.f24073J, null);
            if (num != null && num.intValue() != 256) {
                C20140q0.d(5, "ImageCapture");
                z12 = false;
            }
            if (!z12) {
                C20140q0.d(5, "ImageCapture");
                interfaceC20106m0D2.K(aVar3, bool4);
            }
        } else {
            z12 = false;
        }
        Integer num2 = (Integer) aVar.d().g(androidx.camera.core.impl.Z.f24073J, null);
        if (num2 != null) {
            if (I() && num2.intValue() != 256) {
                z13 = false;
            }
            androidx.core.util.z.a("Cannot set non-JPEG buffer format with Extensions enabled.", z13);
            aVar.d().K(InterfaceC20086c0.f24088e, Integer.valueOf(z12 ? 35 : num2.intValue()));
        } else if (z12) {
            aVar.d().K(InterfaceC20086c0.f24088e, 35);
        } else {
            List list = (List) aVar.d().g(InterfaceC20088d0.f24100n, null);
            if (list == null || H(256, list)) {
                aVar.d().K(InterfaceC20086c0.f24088e, 256);
            } else if (H(35, list)) {
                aVar.d().K(InterfaceC20086c0.f24088e, 35);
            }
        }
        return aVar.b();
    }

    @j.N
    public final String toString() {
        return "ImageCapture:".concat(g());
    }

    @Override // androidx.camera.core.N0
    @j.j0
    @RestrictTo
    public final void v() {
        androidx.camera.core.imagecapture.N n12 = this.f23733u;
        if (n12 != null) {
            n12.a();
        }
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 w(@j.N Config config) {
        this.f23731s.e(config);
        D(this.f23731s.k());
        G0.a aVarF = this.f23621g.f();
        aVarF.d(config);
        return aVarF.a();
    }

    @Override // androidx.camera.core.N0
    @j.N
    @RestrictTo
    public final androidx.camera.core.impl.G0 x(@j.N androidx.camera.core.impl.G0 g02) {
        SessionConfig.b bVarF = F(e(), (androidx.camera.core.impl.Z) this.f23620f, g02);
        this.f23731s = bVarF;
        D(bVarF.k());
        o();
        return g02;
    }

    @Override // androidx.camera.core.N0
    @RestrictTo
    public final void y() {
        androidx.camera.core.imagecapture.N n12 = this.f23733u;
        if (n12 != null) {
            n12.a();
        }
        E(false);
    }

    /* compiled from: ImageCapture.java */
    /* renamed from: androidx.camera.core.e0$j */
    public static abstract class j {
        public void a(@j.N InterfaceC20120j0 interfaceC20120j0) {
        }
    }
}
